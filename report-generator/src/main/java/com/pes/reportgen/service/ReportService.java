package com.pes.reportgen.service;

import java.util.List;
import java.util.Map;

/**
 * @author huston.peng
 * @version 2.4.4
 * @date 3/5/21
 */
public interface ReportService<T> extends Runnable {

  List<T> load();

  Map<String, String> loadMap();

  void fillTemplate(Map<String, String> data);

  void print();
}
