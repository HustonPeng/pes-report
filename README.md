# Pes-Report Tool

## Overview

### Features

* Light card print.
* Sp group pdf to excel.

### Env

* JDK8

### Release Notes

```shell
# v1.1.0
java -jar ${reportType:light_card} ${excelPath} ${templatePath} ${outputDir}
java -jar ${reportType:sp_pdf} ${pdfDir} ${outExcelDir}

# v1.0.0
java -jar ./pesReport.jar ${excelPath} ${templatePath} ${outputDir}

```

## Light card print

* excel: Only one sheet and two columns allowed, first row is header, 33 rows data included.
* print a page once a time.

How to run?

* edit the 01LightCard.bat
* replace ./NameToPrint.xlsx to the real path
* run.

## Sp group

How to run?

* edit the 02SpGroup.bat
* copy pdfs to ./pdfs directory
* run.