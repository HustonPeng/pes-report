package com.pes.reportrest.controller;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.pes.reportgen.model.SmallCardModel;
import com.pes.reportrest.dto.DataInputDto;
import com.pes.reportrest.dto.FullItemDto;
import com.pes.reportrest.service.ReceiptTabService;
import io.swagger.annotations.Api;
import java.net.URLEncoder;
import javax.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author huston.peng
 * @version 1.3.0
 * @date 3/7/21
 */
@Api
@RestController
@RequestMapping("/api/v1/dataquery")
public class DataQueryController {

  @Autowired
  ReceiptTabService receiptTabService;

  @GetMapping
  public IPage<DataInputDto> query(@RequestParam(required = false) String receiptNo,
                                   @RequestParam(required = false) String bookNo,
                                   @RequestParam(value = "approval", required = false) Boolean approval,
                                   @RequestParam Integer current, @RequestParam Integer pageSize) {
    return receiptTabService.query(receiptNo, bookNo, approval, current, pageSize);
  }

  @GetMapping("/itemList")
  public IPage<FullItemDto> queryItemList(@RequestParam(required = false) String receiptNo,
                                          @RequestParam(value = "approval", required = false) Boolean approval,
                                          @RequestParam(value = "printed", required = false) Boolean printed,

                                          @RequestParam Integer current, @RequestParam Integer pageSize) {
    return receiptTabService.queryItemList(receiptNo, approval, printed, current, pageSize);
  }

  @SneakyThrows
  @GetMapping("/itemListDownload")
  public void itemListDownload(@RequestParam(required = false) String receiptNo,
                               @RequestParam(value = "approval", required = false) Boolean approval,
                               @RequestParam(value = "printed", required = false) Boolean printed,
                               HttpServletResponse response) {
    var list = receiptTabService.queryItemList(receiptNo, approval, printed, 1, 10000);

    var fileName = "地藏法会排位名单";
    response.setContentType("application/vnd.ms-excel");
    response.setCharacterEncoding("utf-8");
    response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8") + ".xlsx");

    //dataMap
    list.getRecords().forEach(zw -> {
      if (zw.getItemType().equals(SmallCardModel.ItemType.婴灵)) {
        zw.setFamilyName(zw.getYlFamilyName());
      }
    });

    EasyExcel.write(response.getOutputStream(), FullItemDto.class).needHead(true)
        .sheet(fileName).doWrite(list.getRecords());
  }
}
