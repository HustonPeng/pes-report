package com.pes.reportrest.mapper;

import com.pes.reportrest.entity.ReceiptItemTab;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author huston peng
 * @since 2021-07-03
 */
@Mapper
public interface ReceiptItemTabMapper extends BaseMapper<ReceiptItemTab> {

  int deleteByReceiptNo(@Param("receiptNo") String receiptNo);

  int printed(@Param("id") Long id, @Param("printed") Boolean printed);
}
