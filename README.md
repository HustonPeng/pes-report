# Pes-Report Tool

## Overview

### Features

* Light card print.
* Sp group pdf to excel.
* Small card print. 

### Env

* JDK8

### Release Notes

```shell
# v1.2.0
java -jar ./pesReport-v1.2.0.jar
format: ${reportType:light_card} ${excelPath} ${templatePath} ${outputDir}
format: ${reportType:small_card} ${excelPath} ${templatePath} ${outputDir}
format: ${reportType:sp_pdf} ${pdfDir} ${outExcelDir}

# v1.1.0
java -jar ${reportType:light_card} ${excelPath} ${templatePath} ${outputDir}
java -jar ${reportType:sp_pdf} ${pdfDir} ${outExcelDir}

# v1.0.0
java -jar ./pesReport.jar ${excelPath} ${templatePath} ${outputDir}

```
### Template Name

* light card: LightCard.pptx
* Small Card: 
 * SmallCard_Lidaizuxian.pptx
 * SmallCard_Shifangwuzhuguhun.pptx
 * SmallCard_Wangshenglianwei.pptx
 * SmallCard_Yingling.pptx
 * SmallCard_Yuanqinzhaizhuguhun.pptx