package cn.ulyer.generator.core;

import cn.ulyer.generator.model.GenColumns;
import cn.ulyer.generator.model.GenTables;
import lombok.Data;

/**
 * @author ulyer
 * @date 2021.12.13
 * 生成时上下文参数
 */
@Data
public class GeneratorContext {

    private GenTables genTable;

    private GenColumns genColumns;

    private GeneratorParams generatorParams;

}
