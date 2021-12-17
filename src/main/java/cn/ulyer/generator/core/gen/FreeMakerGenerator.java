package cn.ulyer.generator.core.gen;

import cn.ulyer.generator.core.GeneratorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

/**
 * @author ulyer
 * @date 2021.12.13
 * 抽象mvc层生成
 */
@Component
public  class FreeMakerGenerator implements TemplateGenerator {

    private final String GEN_FOLDER_NAME = "FreeMakerGenerator_";

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void generator(GeneratorContext generatorContext, String folderPath) {

    }
}
