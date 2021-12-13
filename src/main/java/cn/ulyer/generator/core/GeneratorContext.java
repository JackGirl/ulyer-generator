package cn.ulyer.generator.core;

import cn.ulyer.generator.model.GenColumns;
import cn.ulyer.generator.model.GenTables;
import lombok.Data;

@Data
public class GeneratorContext {

    private GenTables genTable;

    private GenColumns genColumns;

    private GeneratorParams generatorParams;

}
