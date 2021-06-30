package com.pes.reportrest.controller;

import com.pes.reportrest.dto.DataInputDto;
import com.pes.reportrest.service.ReceiptTabService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author huston.peng
 * @version 1.3.0
 * @date 3/7/21
 */
@Api
@RestController
@RequestMapping("/api/v1/datainput")
public class DataInputController {

  @Autowired
  ReceiptTabService receiptTabService;

  @PostMapping
  public void input(@RequestBody DataInputDto dataInputDto) {
    receiptTabService.input(dataInputDto);
  }

}
