package com.pes.report.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import lombok.var;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.sl.usermodel.TextParagraph;
import org.apache.poi.sl.usermodel.TextRun;
import org.apache.poi.sl.usermodel.TextShape;
import org.apache.poi.xslf.usermodel.XMLSlideShow;

/**
 * @author huston.peng
 * @version 2.4.4
 * @date 3/5/21
 */
public class PesPptTemplateUtil {

  /**
   * Replace Template with Map.
   *
   * @param templatePath The template path.
   * @param textMap      The data map.
   * @throws IOException
   */
  public static void replaceTextAndWrite(String templatePath, String outputPath,
                                         Map<String, String> textMap)
      throws IOException {

    try (var fileInput = new FileInputStream(templatePath);
         XMLSlideShow ppt = new XMLSlideShow(fileInput)) {
      for (var slide : ppt.getSlides()) {
        var shapes = slide.getShapes();
        for (var shape : shapes) {
          // Only deal text
          if (shape instanceof TextShape) {
            var list = ((TextShape) shape).getTextParagraphs();
            replaceData(list, textMap);
          }
        }
      }

      try (FileOutputStream out = new FileOutputStream(outputPath)) {
        ppt.write(out);
      }
    }
  }

  private static void replaceData(List<TextParagraph> list, Map<String, String> textMap) {

    if (CollectionUtils.isEmpty(list)) {
      return;
    }

    for (var textParagraph : list) {
      if (textParagraph == null) {
        continue;
      }

      List<TextRun> textRuns = textParagraph.getTextRuns();
      if (CollectionUtils.isEmpty(textRuns)) {
        continue;
      }

      for (int i = 0; i < textRuns.size(); i++) {
        TextRun textRun = textRuns.get(i);
        if (textRun == null) {
          continue;
        }

        String rawText = textRun.getRawText();
        if (StringUtils.isEmpty(rawText)) {
          continue;
        }

        String key = rawText.trim();

        if (textMap.containsKey(key)) {
          String value = textMap.get(key);
          textRun.setText(rawText.replace(key, value));
        }
      }
    }
  }
}
