package com.pes.reportrest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author huston.peng
 * @version 1.3.0
 * @date 1/7/21
 */
@Data
@AllArgsConstructor
public class FileDto {

  private String type;

  private String fileName;

}
