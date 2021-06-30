package com.pes.reportrest.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pes.reportrest.dto.DataInputDto;
import com.pes.reportrest.dto.FullItemDto;
import com.pes.reportrest.entity.ReceiptTab;
import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author huston peng
 * @since 2021-07-03
 */
public interface ReceiptTabService extends IService<ReceiptTab> {

  /**
   * Input Data.
   *
   * @param dataInputDto The data dto.
   */
  void input(DataInputDto dataInputDto);

  IPage<DataInputDto> query(String receiptNo, String bookNo, Boolean approval, Integer pageIndex,
                            Integer pageSize);

  void approval(String receiptNo);

  /**
   * Delete the receipt number.
   *
   * @param receiptNo Receipt number.
   */
  void delete(String receiptNo);

  /**
   * Import the data.
   *
   * @param list The list.
   */
  void importData(List<DataInputDto> list);

  IPage<FullItemDto> queryItemList(String receiptNo, Boolean approval, Boolean printed, Integer current,
                                   Integer pageSize);
}
