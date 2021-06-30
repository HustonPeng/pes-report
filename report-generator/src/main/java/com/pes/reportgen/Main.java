package com.pes.reportgen;

import com.pes.reportgen.enums.ReportType;
import com.pes.reportgen.params.CliParameters;
import com.pes.reportgen.params.CliSpGroupParams;
import com.pes.reportgen.service.LightCardReportService;
import com.pes.reportgen.service.SmallCardReportService;
import com.pes.reportgen.service.SpGroupService;
import java.io.IOException;
import lombok.var;

/**
 * @author huston.peng
 * @version 2.4.4
 * @date 2/5/21
 */
public class Main {

  public static void main(String[] args) throws IOException {

    System.out.println("format: ${reportType:light_card} ${excelPath} ${templateDir} ${outputDir}");
    System.out.println("format: ${reportType:small_card} ${excelPath} ${templateDir} ${outputDir}");

    System.out.println("format: ${reportType:sp_pdf} ${pdfDir} ${outExcelDir}");

    ReportType type = ReportType.valueOf(args[0].toUpperCase());

    if (ReportType.LIGHT_CARD.equals(type)) {
      com.pes.reportgen.params.CliParameters cliParameters = new CliParameters();

      cliParameters.setExcelPath(args[1]);
      cliParameters.setTemplatePath(args[2]);
      cliParameters.setOutputDirectory(args[3]);

      System.out.println(cliParameters);

      LightCardReportService reportService = new LightCardReportService(cliParameters);
      reportService.runBatch();
    } else if (ReportType.SP_PDF.equals(type)) {

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
    } else if (ReportType.SMALL_CARD.equals(type)) {

      com.pes.reportgen.params.CliParameters cliParameters = new CliParameters();

      cliParameters.setExcelPath(args[1]);
      cliParameters.setTemplatePath(args[2]);
      cliParameters.setOutputDirectory(args[3]);

      System.out.println(cliParameters);

      SmallCardReportService reportService = new SmallCardReportService(cliParameters);
      reportService.runBatch();
    }
  }
}
