package com.pes.reportgen.service;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.event.SyncReadListener;
import com.alibaba.excel.util.DateUtils;
import com.pes.reportgen.dto.GenerateDto;
import com.pes.reportgen.model.SmallCardModel;
import com.pes.reportgen.params.CliParameters;
import com.pes.reportgen.util.PesPptTemplateUtil;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.SneakyThrows;
import lombok.var;

/**
 * @author huston.peng
 * @version 1.2.0
 * @date 12/6/21
 */
public class SmallCardReportService {

  private static final int PAGE_SIZE = 18;

  private final CliParameters cliParameters;

  public SmallCardReportService(CliParameters cliParameters) {
    this.cliParameters = cliParameters;
  }


  public List<GenerateDto> runBatch(List<String> reportTypes) {

    var toPrintTypes = reportTypes.stream().map(zw -> zw.substring("small_card_".length()))
        .collect(Collectors.toList());
    List<GenerateDto> generateDtos = new ArrayList<>();

    var modelList = load();

    var groupByItemTypes
        = modelList.stream()
        .filter(zw -> toPrintTypes.contains(zw.getItemType()))
        .filter(zw -> zw.getPrinted() == null || zw.getPrinted() != null && !zw.getPrinted())
        .collect(Collectors.groupingBy(SmallCardModel::getItemTypeEnum));

    groupByItemTypes.forEach((key, value) -> generateDtos.add(new GenerateDto(key.name(), runByItemType(key, value))));

    return generateDtos;

  }

  public List<GenerateDto> runBatch() {
    return runBatch(new ArrayList<>());
  }

  private List<String> runByItemType(SmallCardModel.ItemType itemType, List<SmallCardModel> modelList) {

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
      var map = SmallCardModel.toTextMap(itemType, subList, PAGE_SIZE);

      files.add(fillTemplate(itemType, map, pageNumber++));

      start += PAGE_SIZE;
      end += PAGE_SIZE;

    } while (!stop);

    return files;
  }

  @SneakyThrows
  private List<SmallCardModel> load() {
    return EasyExcel.read(
        new FileInputStream(cliParameters.getExcelPath()), SmallCardModel.class,
        new SyncReadListener()).sheet(0).doReadSync();
  }

  @SneakyThrows
  private String fillTemplate(SmallCardModel.ItemType itemType, Map<String, String> data, int pageNumber) {

    var pptPath = "SmallCard-" + itemType.name() + "-" + DateUtils.format(new Date(), DateUtils.DATE_FORMAT_10) + "-" +
        pageNumber +
        ".pptx";

    PesPptTemplateUtil.replaceTextAndWrite(cliParameters.getTemplatePath() + itemType.getTemplateName(),
        cliParameters.getOutputDirectory() + pptPath, data);

    return pptPath;
  }


}
