package com.pes.report.service;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.event.SyncReadListener;
import com.alibaba.excel.util.DateUtils;
import com.pes.report.model.SmallCardModel;
import com.pes.report.params.CliParameters;
import com.pes.report.util.PesPptTemplateUtil;
import java.io.FileInputStream;
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


  public void runBatch() {

    var modelList = load();

    var groupByItemTypes
        = modelList.stream()
        .collect(Collectors.groupingBy(SmallCardModel::getItemTypeEnum));

    groupByItemTypes.forEach(this::runByItemType);
  }

  private void runByItemType(SmallCardModel.ItemType itemType, List<SmallCardModel> modelList) {

    int pageNumber = 1;
    int start = 0;
    int end = PAGE_SIZE;
    boolean stop = false;

    do {

      if (end >= modelList.size()) {
        stop = true;
        end = modelList.size();
      }

      var subList = modelList.subList(start, end);
      var map = SmallCardModel.toTextMap(itemType, subList, PAGE_SIZE);

      fillTemplate(itemType, map, pageNumber++);

      start += PAGE_SIZE;
      end += PAGE_SIZE;

    } while (!stop);
  }

  @SneakyThrows
  private List<SmallCardModel> load() {
    return EasyExcel.read(
        new FileInputStream(cliParameters.getExcelPath()), SmallCardModel.class,
        new SyncReadListener())
        .doReadAllSync();
  }

  @SneakyThrows
  private void fillTemplate(SmallCardModel.ItemType itemType, Map<String, String> data, int pageNumber) {
    PesPptTemplateUtil.replaceTextAndWrite(cliParameters.getTemplatePath() + "/" + itemType.getTemplateName(),
        getGeneratePpt(itemType, pageNumber), data);
  }

  private String getGeneratePpt(SmallCardModel.ItemType itemType, Integer number) {

    return cliParameters.getOutputDirectory() +
        "SmallCard-" + itemType.name() + "-" + DateUtils.format(new Date(), DateUtils.DATE_FORMAT_10) + "-" + number +
        ".pptx";
  }

}
