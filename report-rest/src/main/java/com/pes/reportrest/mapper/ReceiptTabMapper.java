package com.pes.reportrest.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pes.reportrest.dto.FullItemDto;
import com.pes.reportrest.entity.ReceiptTab;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author huston peng
 * @since 2021-07-03
 */
@Mapper
public interface ReceiptTabMapper extends BaseMapper<ReceiptTab> {
  IPage<ReceiptTab> getAllByReceiptNoAndBookNoAndPhone(Page page, @Param("receiptNo") String receiptNo,
                                                       String bookNo, @Param("approval") Boolean approval
  );

  IPage<FullItemDto> getFullItemList(Page page, @Param("receiptNo") String receiptNo,
                                     @Param("approval") Boolean approval, @Param("printed") Boolean printed);
}
