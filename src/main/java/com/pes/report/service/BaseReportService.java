package com.pes.report.service;

import com.pes.report.params.CliParameters;
import lombok.var;

/**
 * @author huston.peng
 * @version 2.4.4
 * @date 3/5/21
 */
public abstract class BaseReportService<T> implements ReportService<T> {

  protected final CliParameters cliParameters;

  protected BaseReportService(CliParameters cliParameters) {
    this.cliParameters = cliParameters;
  }

  @Override
  public void run() {
    var map = loadMap();
    fillTemplate(map);
    print();
  }
}
