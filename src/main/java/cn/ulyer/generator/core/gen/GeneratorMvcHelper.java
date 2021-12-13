package cn.ulyer.generator.core.gen;

import cn.ulyer.generator.core.GeneratorContext;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author ulyer
 * @date 2021.12.13
 * java层代码生成辅助类
 */
public interface GeneratorMvcHelper {

    InputStream genJava(GeneratorContext generatorContext, OutputStream outputStream);


}
