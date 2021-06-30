package com.pes.reportrest.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author huston.peng
 * @version 1.3.0
 * @date 3/7/21
 */
@Data
public class DataInputDto {

  @NotEmpty
  private String receiptNo;

  @NotEmpty
  private String bookNo;

  @NotEmpty
  private String person;

  @NotEmpty
  private String phone;

  @NotEmpty
  private String paymentType;

  private String paymentNo;

  @NotNull
  private BigDecimal amount;

  @NotNull
  private LocalDate date;

  @NotNull
  private List<DataInputItemDto> items;
}
