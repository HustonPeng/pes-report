package com.pes.reportrest.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.event.SyncReadListener;
import com.google.common.collect.Lists;
import com.pes.reportgen.model.SmallCardModel;
import com.pes.reportrest.dto.DataInputDto;
import com.pes.reportrest.dto.DataInputItemDto;
import com.pes.reportrest.service.ReceiptItemTabService;
import com.pes.reportrest.service.ReceiptTabService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.SneakyThrows;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author huston.peng
 * @version 1.3.0
 * @date 3/7/21
 */
@Api
@RestController
@RequestMapping("/api/v1/dataop")
public class DataOpController {

  @Autowired
  ReceiptTabService receiptTabService;
  @Autowired
  ReceiptItemTabService receiptItemTabService;

  @PostMapping("approval")
  public void approval(@RequestParam(required = true) String receiptNo) {
    receiptTabService.approval(receiptNo);
  }

  @DeleteMapping("delete")
  public void delete(@RequestParam(required = true) String receiptNo) {
    receiptTabService.delete(receiptNo);
  }

  @PostMapping("printed")
  public void printed(@RequestParam(required = true) Long id, @RequestParam Boolean printed) {
    receiptItemTabService.printed(id, printed);
  }

  @ApiOperation("upload")
  @SneakyThrows
  @PostMapping("upload")
  public Object upload(@RequestParam(value = "file", required = true) MultipartFile file) {


    List<SmallCardModel> modelList = EasyExcel.read(file.getInputStream(), SmallCardModel.class,
        new SyncReadListener()).sheet(0).doReadSync();

    var groupByItemTypes
        = modelList.stream()
        .collect(Collectors.groupingBy(SmallCardModel::getReceiptNo));


    var list = new ArrayList<DataInputDto>();

    groupByItemTypes.forEach((receiptNo, items) -> {

      var firstValue = items.get(0);

      var dataInputDto = new DataInputDto();
      dataInputDto.setReceiptNo(receiptNo);
      dataInputDto.setBookNo(firstValue.getBookNo());
      dataInputDto.setPerson("N/A");
      dataInputDto.setPhone("N/A");
      dataInputDto.setPaymentType("N/A");
      dataInputDto.setPaymentNo("N/A");
      dataInputDto.setAmount(new BigDecimal("-1"));
      dataInputDto.setDate(LocalDate.of(2021, 1, 1));
      dataInputDto.setItems(Lists.newArrayList());

      items.forEach(item -> {

        var dataInputItemDto = new DataInputItemDto();

        dataInputItemDto.setItemType(item.getItemTypeEnum());
        dataInputItemDto.setLiveName(item.getName());
        if (SmallCardModel.ItemType.历代祖先.equals(item.getItemTypeEnum())) {
          dataInputItemDto.setFamilyName(item.getFamilyName());
        }
        if (SmallCardModel.ItemType.往生莲位.equals(item.getItemTypeEnum())) {
          dataInputItemDto.setRelation(item.getRelation());
          dataInputItemDto.setRelationName(item.getRelationName());
        }
        if (SmallCardModel.ItemType.婴灵.equals(item.getItemTypeEnum())) {
          dataInputItemDto.setYlFamilyName(item.getFamilyName());
          dataInputItemDto.setYlType(item.getDeadChildType());
        }
        dataInputItemDto.setPrinted(false);
        if (item.getPrinted() != null) {
          dataInputItemDto.setPrinted(item.getPrinted());
        }

        dataInputDto.getItems().add(dataInputItemDto);
      });

      list.add(dataInputDto);
    });

    receiptTabService.importData(list);

    return list;
  }


}
