package com.venble.boot;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.TemplateType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Dingwq
 * @since 7/3/2023
 */
public class JpaGenerator {

    /**
     * 数据源配置
     */
    public static final DataSourceConfig.Builder DATA_SOURCE_CONFIG =
            new DataSourceConfig.Builder("jdbc:mysql://127.0.0.1:3306/venable-boot?useUnicode=true&characterEncoding=utf8&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC&createDatabaseIfNotExist=true",
                    "root", "123456");

    protected static List<String> getTables(String tables) {
        return "all".equals(tables) ? Collections.emptyList() : Arrays.asList(tables.split(","));
    }

    public static void main(String[] args) {
        String projectPath = System.getProperty("user.dir");
        FastAutoGenerator.create(DATA_SOURCE_CONFIG)
                // 全局配置
                .globalConfig((scanner, builder) -> builder.author(scanner.apply("请输入作者名称？"))
                        .disableOpenDir()
                        .outputDir(projectPath + "/src/main/java/"))
                // 包配置
                .packageConfig((scanner, builder) -> builder.parent(scanner.apply("请输入包名？"))
                        .moduleName(scanner.apply("请输入模块名？"))
                        .mapper("repository")
                        .serviceImpl("service")
                        .entity("domain")
                )
                .templateConfig((scanner, builder) -> builder
                        .disable(TemplateType.XML, TemplateType.SERVICE)
                        .entity("templates/generator/entity.java")
                        .controller("templates/generator/controller.java")
                        .serviceImpl("templates/generator/service.java")
                        .mapper("templates/generator/repository.java")
                        .build())
                // 策略配置
                .strategyConfig((scanner, builder) -> builder.addInclude(getTables(scanner.apply("请输入表名，多个英文逗号分隔？所有输入 all")))
                        .addTablePrefix("warn_")
                        .entityBuilder().enableLombok()
                        .mapperBuilder().formatMapperFileName("%sRepository")
                        .controllerBuilder().enableRestStyle().enableHyphenStyle()
                        .serviceBuilder().formatServiceImplFileName("%sService")
                        .build())
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }
}
