package com.pes.report.model;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import lombok.Data;
import lombok.Getter;

/**
 * @author huston.peng
 * @version 1.2.0
 * @date 12/6/21
 */
@Data
public class SmallCardModel {
  /**
   * 收据号
   */
  private String receiptNo;

  /**
   * 书本号
   */
  private String date;

  /**
   * 阳上
   */
  private String name;

  /**
   * 项目
   */
  private String itemType;

  /**
   * 姓氏
   */
  private String familyName;

  /**
   * 关系
   */
  private String relation;

  /**
   * 关系人名字
   */
  private String relationName;

  /**
   * 婴灵类表
   */
  private String deadChildType;

  private String bookNo;

  public static Map<String, String> toTextMap(ItemType itemType, List<SmallCardModel> smallCardModelList, int size) {

    Map<String, String> textMap = new LinkedHashMap<>(smallCardModelList.size());

    for (int i = 0; i < size; i++) {

      boolean hasValue = i < smallCardModelList.size();

      textMap.put("$recNo" + (i + 1), hasValue ? smallCardModelList.get(i).getReceiptNo() : "");
      textMap.put("$name" + (i + 1), hasValue ? smallCardModelList.get(i).getName() : "");

      switch (itemType) {
        case 历代祖先:
          textMap.put("$fn" + (i + 1), hasValue ? smallCardModelList.get(i).getFamilyName() : "");
          break;
        case 往生莲位:
          textMap.put("$relation" + (i + 1), hasValue ? smallCardModelList.get(i).getRelation() : "");
          textMap.put("$relationName" + (i + 1), hasValue ? smallCardModelList.get(i).getRelationName() : "");
          break;
        case 十方无主孤魂:
          break;
        case 冤亲债主:
          break;
        case 婴灵:
          // text
          textMap.put("$fn" + (i + 1), hasValue ? smallCardModelList.get(i).getFamilyName() : "");
          textMap.put("$childname" + (i + 1), hasValue ? smallCardModelList.get(i).getDeadChildType() : "");
          break;
        default:
          break;
      }
    }

    return textMap;
  }

  public ItemType getItemTypeEnum() {

    if (itemType == null || "".equals(itemType)) {
      return ItemType.婴灵;
    }

    return ItemType.getByChineseCode(itemType);
  }

  public enum ItemType {
    /**
     * 历代祖先 小牌位
     */
    历代祖先(1, "SmallCard_Lidaizuxian.pptx", "历代祖先"),
    /**
     * 往生莲位 小牌位
     */
    往生莲位(2, "SmallCard_Wangshenglianwei.pptx", "往生莲位"),

    /**
     * 冤亲债主 小牌位
     */
    冤亲债主(3, "SmallCard_Yuanqinzhaizhuguhun.pptx", "冤亲债主"),

    /**
     * 婴灵 小牌位
     */
    婴灵(4, "SmallCard_Yingling.pptx", "婴灵"),

    /**
     * 十方无主孤魂 小牌位
     */
    十方无主孤魂(5, "SmallCard_Shifangwuzhuguhun.pptx", "十方无主孤魂");

    private final Integer code;

    @Getter
    private final String templateName;
    private final String chineseCode;

    ItemType(Integer code, String templateName, String chineseCode) {
      this.code = code;
      this.templateName = templateName;
      this.chineseCode = chineseCode;
    }

    public static ItemType getByCode(Integer code) {
      switch (code) {
        case 1:
          return 历代祖先;
        case 2:
          return 往生莲位;
        case 3:
          return 冤亲债主;
        case 4:
          return 婴灵;
        case 5:
          return 十方无主孤魂;
        default:
          throw new IllegalStateException("Not support code: " + code);
      }
    }

    public static ItemType getByChineseCode(String chineseCode) {
      switch (chineseCode) {
        case "历代祖先":
          return 历代祖先;
        case "往生莲位":
          return 往生莲位;
        case "冤亲债主":
          return 冤亲债主;
        case "婴灵":
          return 婴灵;
        case "十方无主孤魂":
          return 十方无主孤魂;
        default:
          throw new IllegalStateException("Not support code: " + chineseCode);
      }
    }
  }
}
