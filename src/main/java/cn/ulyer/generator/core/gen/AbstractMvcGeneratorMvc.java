package cn.ulyer.generator.core.gen;

import cn.ulyer.generator.core.GeneratorContext;

import java.io.InputStream;
import java.io.OutputStream;

public abstract class AbstractMvcGeneratorMvc implements GeneratorMvcHelper {

    protected  byte[] generatorRest(GeneratorContext generatorContext){
        return null;
    }

    protected abstract byte[] generatorService(GeneratorContext generatorContext);

    protected abstract byte[] generatorDao(GeneratorContext generatorContext);

    @Override
    public InputStream genJava(GeneratorContext generatorContext, OutputStream outputStream) {
        return null;
    }
}
