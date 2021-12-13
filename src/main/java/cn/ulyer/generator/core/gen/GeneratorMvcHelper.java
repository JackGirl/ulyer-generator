package cn.ulyer.generator.core.gen;

import cn.ulyer.generator.core.GeneratorContext;

import java.io.InputStream;
import java.io.OutputStream;

public interface GeneratorMvcHelper {

    InputStream genJava(GeneratorContext generatorContext, OutputStream outputStream);


}
