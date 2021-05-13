package com.pes.report.model;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author huston.peng
 * @version 2.4.4
 * @date 13/5/21
 */
@Data
@EqualsAndHashCode
public class SpBillModel {

  @ExcelIgnore
  private static String ACCOUNT_NO = "Account No.";

  @ExcelProperty("Bill Date")
  private String billDate;

  @ExcelProperty("Sp Account No.")
  private String accountNo;

  @ExcelProperty("Total Amount")
  private String totalAmount;

  @ExcelProperty("Tax Invoice")
  private String taxInvoice;

  public void gain(String line, String nextLine) {
    if (line.startsWith(ACCOUNT_NO)) {
      this.setAccountNo(line.replace(ACCOUNT_NO, ""));
    }

    if (line.startsWith("Total Amount Payable")) {
      this.setTotalAmount(nextLine);
    }

    if (line.endsWith("Bill")) {
      this.setBillDate(line);
    }

    if (line.startsWith("This is your tax invoice for")) {
      this.setTaxInvoice(nextLine);
    }
  }
}
