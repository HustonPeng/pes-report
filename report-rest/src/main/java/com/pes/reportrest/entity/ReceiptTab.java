package com.pes.reportrest.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author huston peng
 * @since 2021-07-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="ReceiptTab对象", description="")
public class ReceiptTab implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(type = IdType.AUTO)
    private Long id;

    private String receiptNo;

    private Boolean approval;

    private String bookNo;

    private String person;

    private String phone;

    private String paymentType;

    private String paymentNo;

    private BigDecimal amount;

    private LocalDate inputDate;


}
