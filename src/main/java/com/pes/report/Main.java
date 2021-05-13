package com.pes.report;

import com.pes.report.params.CliParameters;
import com.pes.report.params.CliSpGroupParams;
import com.pes.report.service.LightCardReportService;
import com.pes.report.service.ReportService;
import com.pes.report.service.SpGroupService;
import java.io.IOException;
import lombok.var;

/**
 * @author huston.peng
 * @version 2.4.4
 * @date 2/5/21
 */
public class Main {

  public static void main(String[] args) throws IOException {

    System.out
        .println("format: ${reportType:light_card} ${excelPath} ${templatePath} ${outputDir}");
    System.out.println("format: ${reportType:sp_pdf} ${pdfDir} ${outExcelDir}");

    var type = args[0];

    if ("light_card".equals(type)) {
      com.pes.report.params.CliParameters cliParameters = new CliParameters();

      cliParameters.setExcelPath(args[1]);
      cliParameters.setTemplatePath(args[2]);
      cliParameters.setOutputDirectory(args[3]);

      System.out.println(cliParameters);

      ReportService<?> reportService = new LightCardReportService(cliParameters);
      reportService.run();
    } else if ("sp_pdf".equals(type)) {

      /*
      args = new String[3];
      args[0] = "sp_pdf";
      args[1] = "/Users/zhen.peng/Downloads/pdfs";
      args[2] = "/Users/zhen.peng/Downloads/";
      */
      var pdfDir = args[1];
      var outExcelDir = args[2];

      CliSpGroupParams cliSpGroupParams = new CliSpGroupParams(pdfDir, outExcelDir);

      new SpGroupService(cliSpGroupParams).run();
    }
  }
}
