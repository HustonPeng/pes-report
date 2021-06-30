package com.pes.reportrest.dto;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author huston.peng
 * @version 1.3.0
 * @date 4/7/21
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class FullItemDto extends DataInputItemDto {

  @ExcelProperty("收据号码")
  private String receiptNo;

  @ExcelProperty("书号")
  private String bookNo;

  @ExcelProperty("联系人")
  private String person;

  @ExcelProperty("电话")
  private String phone;

  @ExcelIgnore
  private String paymentType;
  @ExcelIgnore
  private String paymentNo;
  @ExcelIgnore
  private BigDecimal amount;

  @ExcelIgnore
  private LocalDate date;

}
