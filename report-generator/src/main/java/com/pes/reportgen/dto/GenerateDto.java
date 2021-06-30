package com.pes.reportgen.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author huston.peng
 * @version 1.3.0
 * @date 3/7/21
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenerateDto {

  private String type;

  private List<String> files;
}
