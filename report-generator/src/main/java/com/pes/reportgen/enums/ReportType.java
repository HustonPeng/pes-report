package com.pes.reportgen.enums;

import lombok.Getter;

/**
 * @author huston.peng
 * @version 1.2.0
 * @date 12/6/21
 */
public enum ReportType {
  LIGHT_CARD("light_card"),
  SMALL_CARD("small_card"),
  SP_PDF("sp_pdf");

  @Getter
  private final String typeName;

  ReportType(String typeName) {
    this.typeName = typeName;
  }
}
