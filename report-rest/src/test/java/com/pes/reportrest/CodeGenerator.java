package com.pes.reportrest;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.FileType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CodeGenerator {

  public static void main(String[] args) {

    AutoGenerator mpg = new AutoGenerator();

    GlobalConfig gc = new GlobalConfig();
    String projectPath = System.getProperty("user.dir");
    gc.setOutputDir(projectPath + "/report-rest" + "/src/main/java");
    gc.setAuthor("huston peng");
    gc.setOpen(false);
    gc.setSwagger2(true);
    gc.setBaseResultMap(true);
    gc.setServiceName("%sService");
    gc.setBaseColumnList(true);

    mpg.setGlobalConfig(gc);

    // 数据源配置
    DataSourceConfig dsc = new DataSourceConfig();
    dsc.setUrl(
        "jdbc:mysql://localhost:3306/pes_db?useSSL=false&setUnicode=true&characterEncoding=utf8&allowPublicKeyRetrieval=true");
    dsc.setDbType(DbType.MYSQL);
    dsc.setDriverName("com.mysql.jdbc.Driver");
    dsc.setUsername("root");
    dsc.setPassword("Pes_123456");
    mpg.setDataSource(dsc);

    PackageConfig pc = new PackageConfig();
    pc.setModuleName("");
    pc.setParent("com.pes.reportrest");
    mpg.setPackageInfo(pc);

    InjectionConfig cfg =
        new InjectionConfig() {
          @Override
          public void initMap() {
            // to do nothing
          }
        };
    cfg.setFileCreate(
        new IFileCreate() {
          @Override
          public boolean isCreate(ConfigBuilder configBuilder, FileType fileType, String filePath) {
            // 如果是Entity则直接返回true表示写文件
            if (fileType == FileType.ENTITY) {
              return true;
            }

            // 否则先判断文件是否存在
            File file = new File(filePath);
            boolean exist = file.exists();
            if (!exist) {
              file.getParentFile().mkdirs();
            }
            // 文件不存在或者全局配置的fileOverride为true才写文件
            return !exist || configBuilder.getGlobalConfig().isFileOverride();
          }
        });

    List<FileOutConfig> focList = new ArrayList<FileOutConfig>();
    focList.add(new FileOutConfig("/templates/mapper.xml.vm") {
      @Override
      public String outputFile(TableInfo tableInfo) {
        return "/Users/zhen.peng/git/pes-report/report-rest/src/main/resources/mapper/" + tableInfo.getEntityName() + "Mapper.xml";
      }
    });
    cfg.setFileOutConfigList(focList);
    mpg.setCfg(cfg);

    // 配置模板
    TemplateConfig templateConfig = new TemplateConfig();
    mpg.setTemplate(templateConfig);

    // 策略配置
    StrategyConfig strategy = new StrategyConfig();
    strategy.setNaming(NamingStrategy.underline_to_camel);
    strategy.setColumnNaming(NamingStrategy.underline_to_camel);
    strategy.setEntityLombokModel(true);
    strategy.setRestControllerStyle(true);
    strategy.setSuperEntityColumns("id");
    strategy.setInclude("receipt_tab,receipt_item_tab".split(","));
    strategy.setControllerMappingHyphenStyle(true);
    strategy.setTablePrefix(pc.getModuleName() + "_");
    mpg.setStrategy(strategy);
    mpg.setTemplateEngine(new VelocityTemplateEngine());
    mpg.execute();
  }
}
