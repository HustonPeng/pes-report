package com.pes.reportgen.service;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.event.SyncReadListener;
import com.alibaba.excel.util.DateUtils;
import com.pes.reportgen.dto.GenerateDto;
import com.pes.reportgen.model.LightCardModel;
import com.pes.reportgen.params.CliParameters;
import com.pes.reportgen.util.PesPptTemplateUtil;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import lombok.SneakyThrows;
import lombok.var;

/**
 * @author huston.peng
 * @version 2.4.4
 * @date 3/5/21
 */
public class LightCardReportService {

  private static final int PAGE_SIZE = 33;

  private final CliParameters cliParameters;

  public LightCardReportService(CliParameters cliParameters) {
    this.cliParameters = cliParameters;
  }

  @SneakyThrows
  public List<LightCardModel> load() {

    return EasyExcel.read(
        new FileInputStream(cliParameters.getExcelPath()), LightCardModel.class,
        new SyncReadListener()).sheet(0).doReadSync();
  }

  @SneakyThrows
  public String fillTemplate(Map<String, String> data, int pageNumber) {

    var pptPath = "LightCard-" + DateUtils.format(new Date(), DateUtils.DATE_FORMAT_10) + "_" + pageNumber + ".pptx";

    PesPptTemplateUtil
        .replaceTextAndWrite(cliParameters.getTemplatePath() + "LightCard.pptx",
            cliParameters.getOutputDirectory() + pptPath, data);

    return pptPath;
  }

  public List<GenerateDto> runBatch() {

    var modelList = load();

    int pageNumber = 1;
    int start = 0;
    int end = PAGE_SIZE;
    boolean stop = false;

    List<String> files = new ArrayList<>();

    do {

      if (end >= modelList.size()) {
        stop = true;
        end = modelList.size();
      }

      var subList = modelList.subList(start, end);
      var map = LightCardModel.toTextMap(subList, PAGE_SIZE);

      files.add(fillTemplate(map, pageNumber++));

      start += PAGE_SIZE;
      end += PAGE_SIZE;

    } while (!stop);

    return Arrays.asList(new GenerateDto("供灯", files));
  }

}
