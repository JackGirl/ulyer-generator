package cn.ulyer.generator.core;

import cn.ulyer.generator.core.enums.GenModules;
import lombok.Data;

import java.util.List;

/**
 * @author ulyer
 * @date 2021.12.13
 * 生成参数
 */
@Data
public class GeneratorParams {

    private String jsFramework;

    private String ui;

    private String dao;

    private boolean useSwagger;

    private List<GenModules> genModules;

    private String basePackage;

    private String author = "ulyer-generator";

    private List<String> genTableIds;

}
