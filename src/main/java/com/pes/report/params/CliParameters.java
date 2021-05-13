package com.pes.report.params;

import lombok.Data;
import lombok.ToString;

/**
 * @author huston.peng
 * @version 2.4.4
 * @date 3/5/21
 */
@Data
@ToString
public class CliParameters {
  private String excelPath;
  private String templatePath;
  private String outputDirectory;
}
