package com.pes.reportrest.controller;

import com.pes.reportgen.dto.GenerateDto;
import com.pes.reportgen.enums.ReportType;
import com.pes.reportgen.params.CliParameters;
import com.pes.reportgen.service.LightCardReportService;
import com.pes.reportgen.service.SmallCardReportService;
import com.pes.reportrest.dto.FileDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import lombok.var;
import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author huston.peng
 * @version 1.3.0
 * @date 1/7/21
 */
@Api("Print API")
@RequestMapping("/api/v1/print")
@RestController
public class PrintController {

  @Value("${upload.dir:}")
  private String uploadDir;

  @Value("${template.dir:}")
  private String templateDir;

  @Value("${output.dir:}")
  private String outputDir;

  @ApiOperation("upload")
  @SneakyThrows
  @PostMapping("upload")
  public List<FileDto> upload(@RequestParam("reportTypes") List<String> reportTypes,
                              @RequestParam(value = "file", required = true) MultipartFile file) {

    File dir = new File(uploadDir);
    if (!dir.exists()) {
      dir.mkdir();
    }

    var fileName = executeUpload(uploadDir, file);

    var cliParameters = new CliParameters();
    cliParameters.setExcelPath(fileName);
    cliParameters.setTemplatePath(templateDir);
    cliParameters.setOutputDirectory(outputDir);

    List<GenerateDto> generateFiles = null;
    if (reportTypes.contains(ReportType.LIGHT_CARD.getTypeName())) {
      LightCardReportService reportService = new LightCardReportService(cliParameters);
      generateFiles = reportService.runBatch();
    } else {
      SmallCardReportService reportService = new SmallCardReportService(cliParameters);
      generateFiles = reportService.runBatch(reportTypes);
    }

    var result = new ArrayList<FileDto>();
    generateFiles.forEach(zw -> {
      zw.getFiles().forEach(zw2 -> {
        result.add(new FileDto(zw.getType(), zw2));
      });
    });

    return result;
  }

  private String executeUpload(String uploadDir, MultipartFile file) throws Exception {

    //文件后缀名
    String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
    //上传文件名
    String filename = UUID.randomUUID() + suffix;

    //服务器端保存的文件对象
    File serverFile = new File(uploadDir + filename);

    if (!serverFile.exists()) {
      //先得到文件的上级目录，并创建上级目录，在创建文件
      serverFile.getParentFile().mkdir();
      try {
        //创建文件
        serverFile.createNewFile();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    file.transferTo(serverFile);

    return serverFile.getAbsolutePath();
  }

  @ApiOperation("download")
  @GetMapping("download")
  @SneakyThrows
  public void download(String fileName, HttpServletResponse response) {

    response.setContentType("application/vnd.ms-excel");
    response.setCharacterEncoding("utf-8");
    response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));

    FileInputStream fileInputStream = new FileInputStream(outputDir + fileName);

    IOUtils.copy(fileInputStream, response.getOutputStream());
    response.flushBuffer();
  }
}
