package cn.ulyer.generator.core;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * @author ulyer
 * @date 2021.12.13
 * 生成参数
 */
@Data
public class GeneratorParams {

    @NotNull(message = "生成模块必选")
    private List<String> genModules;

    private String basePackage = "cn.ulyer";

    private String author;

    private List<String> genTables;

    private Map<String,Object> extendsVariables;

}
