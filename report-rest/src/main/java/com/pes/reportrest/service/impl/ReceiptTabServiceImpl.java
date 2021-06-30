package com.pes.reportrest.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pes.reportrest.dto.DataInputDto;
import com.pes.reportrest.dto.FullItemDto;
import com.pes.reportrest.entity.ReceiptTab;
import com.pes.reportrest.exception.AlreadyApprovalException;
import com.pes.reportrest.mapper.ReceiptTabMapper;
import com.pes.reportrest.service.ReceiptItemTabService;
import com.pes.reportrest.service.ReceiptTabService;
import java.util.HashMap;
import java.util.List;
import lombok.var;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ReceiptTabServiceImpl extends ServiceImpl<ReceiptTabMapper, ReceiptTab> implements ReceiptTabService {

  @Autowired
  ReceiptItemTabService receiptItemTabService;

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void input(DataInputDto dataInputDto) {

    notAllowedAfterApproval(dataInputDto.getReceiptNo());

    saveOrUpdateReceiptTab(dataInputDto);
  }

  @Override
  public IPage<DataInputDto> query(String receiptNo, String bookNo, Boolean approval, Integer pageIndex, Integer pageSize) {

    var list = getBaseMapper()
        .getAllByReceiptNoAndBookNoAndPhone(new Page<ReceiptTab>(pageIndex, pageSize), receiptNo, bookNo, approval);

    return list.convert(zw -> {
      DataInputDto dataInputDto = new DataInputDto();
      BeanUtils.copyProperties(zw, dataInputDto);
      dataInputDto.setDate(zw.getInputDate());
      dataInputDto.setItems(receiptItemTabService.getByReceiptNo(zw.getReceiptNo()));
      return dataInputDto;
    });

  }

  private ReceiptTab notAllowedAfterApproval(String receiptNo) {
    var dbEntity = lambdaQuery()
        .eq(ReceiptTab::getReceiptNo, receiptNo)
        .oneOpt();
    if (dbEntity.isPresent()) {
      if (dbEntity.get().getApproval()) {
        throw new AlreadyApprovalException(receiptNo + "已经审核，不允许操作.");
      }
    }

    return dbEntity.orElse(null);
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void approval(String receiptNo) {

    var dbEntity = notAllowedAfterApproval(receiptNo);
    if (dbEntity == null) {
      throw new RuntimeException("Not exist");
    }

    dbEntity.setApproval(true);

    this.updateById(dbEntity);
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void delete(String receiptNo) {

    var dbEntity = notAllowedAfterApproval(receiptNo);
    if (dbEntity != null) {
      removeById(dbEntity);

      var condition = new HashMap<String, Object>(2);
      condition.put("receipt_no", receiptNo);

      receiptItemTabService.removeByMap(condition);
    }
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void importData(List<DataInputDto> list) {
    list.forEach(this::importSingle);
  }

  private void importSingle(DataInputDto dataInputDto) {

    var dbEntity = lambdaQuery()
        .eq(ReceiptTab::getReceiptNo, dataInputDto.getReceiptNo())
        .oneOpt();

    //Skip Approval
    if (dbEntity.isPresent() && dbEntity.get().getApproval()) {
      return;
    }

    saveOrUpdateReceiptTab(dataInputDto);
  }

  private void saveOrUpdateReceiptTab(DataInputDto dataInputDto) {
    ReceiptTab receiptTab = new ReceiptTab();
    receiptTab.setReceiptNo(dataInputDto.getReceiptNo());
    receiptTab.setBookNo(dataInputDto.getBookNo());
    receiptTab.setPerson(dataInputDto.getPerson());
    receiptTab.setPhone(dataInputDto.getPhone());
    receiptTab.setPaymentType(dataInputDto.getPaymentType());
    receiptTab.setPaymentNo(dataInputDto.getPaymentNo());
    receiptTab.setAmount(dataInputDto.getAmount());
    receiptTab.setInputDate(dataInputDto.getDate());

    UpdateWrapper<ReceiptTab> wrapper = new UpdateWrapper<>();
    wrapper.eq("receipt_no", dataInputDto.getReceiptNo());

    this.saveOrUpdate(receiptTab, wrapper);

    receiptItemTabService.input(dataInputDto.getReceiptNo(), dataInputDto.getItems());
  }

  @Override
  public IPage<FullItemDto> queryItemList(String receiptNo, Boolean approval, Boolean printed,
                                          Integer current, Integer pageSize) {

    return getBaseMapper()
        .getFullItemList(new Page<ReceiptTab>(current, pageSize), receiptNo, approval, printed);

  }
}
