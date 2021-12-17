package cn.ulyer.generator.core;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;

/**
 * @author ulyer
 * @date 2021.12.13
 * 生成参数
 */
@Data
public class GeneratorParams {

    @NotBlank(message = "生成模块必填")
    private List<String> genModules;

    private String basePackage = "";

    private String author;

    private List<String> genTables;

    private Map<String,?> extendsVariables;

}
