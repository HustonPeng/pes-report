package com.pes.reportrest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author huston.peng
 * @version 1.3.0
 * @date 3/7/21
 */
@Controller
public class IndexController {

  @RequestMapping(value = {"/"})
  public String index() {
    return "index";
  }
}
