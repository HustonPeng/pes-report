package com.pes.reportgen.model;

import com.alibaba.excel.annotation.ExcelProperty;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Data;

/**
 * @author huston.peng
 * @version 2.4.4
 * @date 3/5/21
 */
@Data
public class LightCardModel {

  /**
   * The Name
   */
  @ExcelProperty(index = 0)
  private String name;

  /**
   * Receipt No
   */
  @ExcelProperty(index = 1)
  private String receiptNo;

  public static Map<String, String> toTextMap(List<LightCardModel> lightCardModelList, int pageSize) {

    Map<String, String> textMap = new HashMap<>(lightCardModelList.size());

    for (int i = 0; i < pageSize; i++) {

      boolean hasValue = i < lightCardModelList.size();

      textMap.put("$name" + (i + 1), hasValue ? lightCardModelList.get(i).getName() : "");
      textMap.put("$recNo" + (i + 1), hasValue ? lightCardModelList.get(i).getReceiptNo() : "");
    }

    return textMap;
  }
}
