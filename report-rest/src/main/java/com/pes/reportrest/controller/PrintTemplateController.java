package com.pes.reportrest.controller;

import com.pes.reportrest.dto.DataInputDto;
import com.pes.reportrest.service.ReceiptTabService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author huston.peng
 * @version 1.3.0
 * @date 3/7/21
 */
@RestController
@RequestMapping("/api/v1/printtemplate/")
public class PrintTemplateController {

  @Autowired
  ReceiptTabService receiptTabService;

  @GetMapping
  public void list(@RequestBody DataInputDto dataInputDto) {
    receiptTabService.input(dataInputDto);
  }

}
