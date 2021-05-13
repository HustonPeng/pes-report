package com.pes.report.service;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.pes.report.model.SpBillModel;
import com.pes.report.params.CliSpGroupParams;
import java.io.File;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.SneakyThrows;
import lombok.var;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

/**
 * @author huston.peng
 * @version 2.4.4
 * @date 13/5/21
 */

public class SpGroupService implements Runnable {

  private final CliSpGroupParams cliSpGroupParams;

  public SpGroupService(CliSpGroupParams cliSpGroupParams) {
    this.cliSpGroupParams = cliSpGroupParams;
  }

  @SneakyThrows
  private static SpBillModel readSpBillModel(File pdf) {

    try (var document = PDDocument.load(pdf)) {

      if (!document.isEncrypted() && pdf.isFile() && pdf.canRead()) {

        var tStripper = new PDFTextStripper();
        tStripper.setStartPage(1);
        tStripper.setEndPage(1);
        tStripper.setSortByPosition(true);

        String pdfFileInText = tStripper.getText(document);
        System.out.println(pdfFileInText);
        SpBillModel spBillModel = new SpBillModel();

        // split by whitespace
        String[] lines = pdfFileInText.split("\\r?\\n");
        for (int i = 0; i < lines.length; i++) {
          spBillModel.gain(lines[i], i < lines.length - 1 ? lines[i + 1] : lines[i]);
        }

        return spBillModel;
      }
    }

    return null;
  }


  @Override
  public void run() {
    File dir = new File(cliSpGroupParams.getPdfDirectory());

    var list =
        Arrays.stream(Objects.requireNonNull(dir.listFiles())).map(SpGroupService::readSpBillModel)
            .filter(Objects::nonNull)
            .collect(Collectors.toList());

    EasyExcel.write(cliSpGroupParams.getOutExcelDirectory() + "sp-result.xlsx", SpBillModel.class)
        .sheet("SP GROUP")
        .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
        .doWrite(list);
  }
}
