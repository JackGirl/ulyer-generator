package cn.ulyer.generator.core.gen;

import cn.ulyer.generator.core.GeneratorContext;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipInputStream;

/**
 * @author ulyer
 * @date 2021.12.13
 * java层代码生成辅助类
 */
public interface TemplateGenerator {

    /**
     *
     * @param generatorContext
     * @param folderPath 指定在某个文件夹路径下生成
     * @return
     */
    void generator(GeneratorContext generatorContext, String folderPath);


}
