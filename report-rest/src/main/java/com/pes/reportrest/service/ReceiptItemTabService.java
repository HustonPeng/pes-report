package com.pes.reportrest.service;

import com.pes.reportrest.dto.DataInputItemDto;
import com.pes.reportrest.entity.ReceiptItemTab;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author huston peng
 * @since 2021-07-03
 */
public interface ReceiptItemTabService extends IService<ReceiptItemTab> {

  /**
   * Input the item
   * @param receiptNo Receipt number.
   * @param items Item list.
   */
  void input(String receiptNo, List<DataInputItemDto> items);

  List<DataInputItemDto> getByReceiptNo(String receiptNo);

  void printed(Long id, Boolean printed);
}
