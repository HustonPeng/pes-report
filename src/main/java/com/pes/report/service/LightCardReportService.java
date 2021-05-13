package com.pes.report.service;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.event.SyncReadListener;
import com.alibaba.excel.util.DateUtils;
import com.pes.report.params.CliParameters;
import com.pes.report.model.LightCardModel;
import com.pes.report.util.PesPptTemplateUtil;
import java.io.FileInputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;
import lombok.SneakyThrows;

/**
 * @author huston.peng
 * @version 2.4.4
 * @date 3/5/21
 */
public class LightCardReportService extends BaseReportService<LightCardModel> {

  private final String pptPath;

  public LightCardReportService(CliParameters cliParameters) {
    super(cliParameters);
    this.pptPath = getGeneratePpt();
  }

  @SneakyThrows
  @Override
  public List<LightCardModel> load() {

    return EasyExcel.read(
        new FileInputStream(cliParameters.getExcelPath()), LightCardModel.class,
        new SyncReadListener())
        .doReadAllSync();
  }

  @Override
  public Map<String, String> loadMap() {
    return LightCardModel.toTextMap(load());
  }

  @SneakyThrows
  @Override
  public void fillTemplate(Map<String, String> data) {
    PesPptTemplateUtil.replaceTextAndWrite(cliParameters.getTemplatePath(), pptPath, data);
  }

  @SneakyThrows
  @Override
  public void print() {

    try {
//      Presentation presentation = new Presentation();
//      presentation.loadFromFile(pptPath);
//      presentation.saveToFile(getGeneratePdf(), FileFormat.PDF);
//      presentation.dispose();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  private String getGeneratePdf() {
    return cliParameters.getOutputDirectory() +
        "LightCard-" + DateUtils.format(new Date(), DateUtils.DATE_FORMAT_10) + ".pdf";
  }

  private String getGeneratePpt() {
    return cliParameters.getOutputDirectory() +
        "LightCard-" + DateUtils.format(new Date(), DateUtils.DATE_FORMAT_10) + ".pptx";
  }
}
