package cn.ulyer.generator.core.gen;

import cn.ulyer.generator.model.GenTemplate;
import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;

/**
 * @author ulyer
 * @date 2021.12.13
 * 抽象mvc层生成
 */
public  class FreeMakerGenerator extends BaseTemplateGenerator {



    @Override
    protected String process(GenTemplate template, Map<String, Object> variables) throws Exception {
        Template freeMakerTemplate = new Template("freemaker",template.getTemplate(),null);
        StringWriter writer = new StringWriter();
        freeMakerTemplate.process(variables,writer);
        return writer.toString();
    }
}
