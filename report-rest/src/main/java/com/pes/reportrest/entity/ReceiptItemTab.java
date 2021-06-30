package com.pes.reportrest.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.pes.reportgen.model.SmallCardModel;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value="ReceiptItemTab对象", description="")
public class ReceiptItemTab implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(type = IdType.AUTO)
    private Long id;

    private String receiptNo;

    private SmallCardModel.ItemType itemType;

    private String liveName;

    private String familyName;

    private String relation;

    private String relationName;

    private String ylFamilyName;

    private String ylType;

    private Boolean printed;
}
