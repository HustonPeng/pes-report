# Pes-Report Tool

## Overview

## Features

* Light card print.
* Sp group pdf to excel.
* Small card print.
* web site.

## Env

* Spring Boot 2.X + JDK 8
* Antd pro

## Release Notes

### v2.0.0

1. jar + application
2. templates, uploads, outputs
3. bat file.

### v1.2.0
```shell
java -jar ./pesReport-v1.2.0.jar
format: ${reportType:light_card} ${excelPath} ${templatePath} ${outputDir}
format: ${reportType:small_card} ${excelPath} ${templatePath} ${outputDir}
format: ${reportType:sp_pdf} ${pdfDir} ${outExcelDir}

```
### v.1.1.0

```shell
java -jar ${reportType:light_card} ${excelPath} ${templatePath} ${outputDir}
java -jar ${reportType:sp_pdf} ${pdfDir} ${outExcelDir}
```

### v.1.0。0

```shell
java -jar ./pesReport.jar ${excelPath} ${templatePath} ${outputDir}
```