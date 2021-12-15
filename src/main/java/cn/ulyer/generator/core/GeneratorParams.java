package cn.ulyer.generator.core;

import cn.ulyer.generator.model.GenModule;
import lombok.Data;

import java.util.List;

/**
 * @author ulyer
 * @date 2021.12.13
 * 生成参数
 */
@Data
public class GeneratorParams {

    private List<GenModule> genModules;

    private String basePackage;

    private String author = "ulyer-generator";

    private List<String> genTableIds;

}
