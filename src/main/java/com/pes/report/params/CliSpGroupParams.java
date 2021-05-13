package com.pes.report.params;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

/**
 * @author huston.peng
 * @version 2.4.4
 * @date 3/5/21
 */
@Data
@ToString
@AllArgsConstructor
public class CliSpGroupParams {
  private String pdfDirectory;
  private String outExcelDirectory;
}
