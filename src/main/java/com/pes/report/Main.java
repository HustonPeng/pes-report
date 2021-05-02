package com.pes.report;

import com.pes.report.service.LightCardReportService;
import com.pes.report.service.ReportService;
import java.io.IOException;

/**
 * @author huston.peng
 * @version 2.4.4
 * @date 2/5/21
 */
public class Main {

  public static void main(String[] args) throws IOException {

    System.out.println("format: ${excelPath} ${templatePath} ${outputDir}");

    CliParameters cliParameters = new CliParameters();

    cliParameters.setExcelPath(args[0]);
    cliParameters.setTemplatePath(args[1]);
    cliParameters.setOutputDirectory(args[2]);

    System.out.println(cliParameters);

    ReportService<?> reportService = new LightCardReportService(cliParameters);
    reportService.run();
  }
}
