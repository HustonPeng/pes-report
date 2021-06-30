package com.pes.reportgen.model;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import lombok.var;

/**
 * @author huston.peng
 * @version 1.3.0
 * @date 6/7/21
 *
 */
public class PrintedConverter implements Converter {
  @Override
  public Class supportJavaTypeKey() {
    return Boolean.class;
  }

  @Override
  public CellDataTypeEnum supportExcelTypeKey() {
    return CellDataTypeEnum.STRING;
  }

  @Override
  public Object convertToJavaData(CellData cellData, ExcelContentProperty contentProperty,
                                  GlobalConfiguration globalConfiguration) throws Exception {
    if (cellData != null && cellData.getStringValue() != null) {
      var data = cellData.getStringValue();
      return "是".equals(data) || "true".equalsIgnoreCase(data);
    }

    return null;
  }

  @Override
  public CellData convertToExcelData(Object value, ExcelContentProperty contentProperty,
                                     GlobalConfiguration globalConfiguration) throws Exception {
    return null;
  }
}
