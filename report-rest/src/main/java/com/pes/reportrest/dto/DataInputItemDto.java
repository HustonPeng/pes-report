package com.pes.reportrest.dto;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.pes.reportgen.model.SmallCardModel;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author huston.peng
 * @version 1.3.0
 * @date 3/7/21
 *
 */
@Data
public class DataInputItemDto {

  @ExcelIgnore
  private Long id;

  @NotNull
  @ExcelProperty(value = "项目", converter = ItemTypeConverter.class)
  private SmallCardModel.ItemType itemType;

  @NotEmpty
  @ExcelProperty("阳上姓名")
  private String liveName;

  @ExcelProperty("家门姓氏 (历代祖先和婴灵only)")
  private String familyName;

  @ExcelProperty("关系(往生莲位only)")
  private String relation;

  @ExcelProperty("先/故人名字")
  private String relationName;

  @ExcelProperty("婴灵家门姓氏")
  @ExcelIgnore
  private String ylFamilyName;

  @ExcelProperty("婴灵类别")
  private String ylType;

  @ExcelProperty("是否已打印")
  private Boolean printed;

  public static class ItemTypeConverter implements Converter<SmallCardModel.ItemType> {

    @Override
    public Class supportJavaTypeKey() {
      return SmallCardModel.ItemType.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
      return CellDataTypeEnum.STRING;
    }

    @Override
    public SmallCardModel.ItemType convertToJavaData(CellData cellData, ExcelContentProperty contentProperty,
                                                     GlobalConfiguration globalConfiguration) throws Exception {
      return null;
    }

    @Override
    public CellData convertToExcelData(SmallCardModel.ItemType value, ExcelContentProperty contentProperty,
                                       GlobalConfiguration globalConfiguration) throws Exception {
      if(value !=null ){
        return new CellData(value.name());
      }
      return new CellData("");
    }
  }
}
