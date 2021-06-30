package com.pes.reportrest.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pes.reportrest.dto.DataInputItemDto;
import com.pes.reportrest.entity.ReceiptItemTab;
import com.pes.reportrest.mapper.ReceiptItemTabMapper;
import com.pes.reportrest.service.ReceiptItemTabService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.var;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author huston peng
 * @since 2021-07-03
 */
@Service
public class ReceiptItemTabServiceImpl extends ServiceImpl<ReceiptItemTabMapper, ReceiptItemTab>
    implements ReceiptItemTabService {

  @Override
  public void input(String receiptNo, List<DataInputItemDto> items) {

    getBaseMapper().deleteByReceiptNo(receiptNo);

    var list = items.stream().map(dataInputItemDto -> {
      ReceiptItemTab receiptItemTab = new ReceiptItemTab();
      receiptItemTab.setReceiptNo(receiptNo);
      receiptItemTab.setItemType(dataInputItemDto.getItemType());
      receiptItemTab.setLiveName(dataInputItemDto.getLiveName());
      receiptItemTab.setFamilyName(dataInputItemDto.getFamilyName());
      receiptItemTab.setRelation(dataInputItemDto.getRelation());
      receiptItemTab.setRelationName(dataInputItemDto.getRelationName());
      receiptItemTab.setYlFamilyName(dataInputItemDto.getYlFamilyName());
      receiptItemTab.setYlType(dataInputItemDto.getYlType());
      return receiptItemTab;
    }).collect(Collectors.toList());

    this.saveBatch(list);
  }

  @Override
  public List<DataInputItemDto> getByReceiptNo(String receiptNo) {

    var list = lambdaQuery().eq(ReceiptItemTab::getReceiptNo, receiptNo)
        .orderBy(true, true, ReceiptItemTab::getItemType)
        .list();

    return list.stream().map(zw -> {
      DataInputItemDto dataInputItemDto = new DataInputItemDto();
      BeanUtils.copyProperties(zw, dataInputItemDto);
      dataInputItemDto.setPrinted(zw.getPrinted());
      return dataInputItemDto;
    }).collect(Collectors.toList());

  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void printed(Long id, Boolean printed) {
    getBaseMapper().printed(id, printed);
  }
}
