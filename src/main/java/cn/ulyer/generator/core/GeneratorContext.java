package cn.ulyer.generator.core;

import cn.ulyer.generator.model.GenTable;
import cn.ulyer.generator.model.GenTemplate;
import cn.ulyer.generator.util.ArrayUtil;
import lombok.Getter;

import java.util.List;
import java.util.Map;

/**
 * @author ulyer
 * @date 2021.12.13
 * 生成时上下文参数
 */
@Getter
public class GeneratorContext {

    private List<GenTemplate> templates;

    private List<GenTable> tables;

    private Map<String,?> variables;

    private String author;

    private String backPackage;


    private GeneratorContext(Map<String,?> variables){
        this.variables = variables;
    }

    private void setTemplates(List<GenTemplate> templates) {
        this.templates = templates;
    }

    private void setTables(List<GenTable> tables) {
        this.tables = tables;
    }

    private void setVariables(Map<String, ?> variables) {
        this.variables = variables;
    }

    private void setBackPackage(String backPackage) {
        this.backPackage = backPackage;
    }

    private void setAuthor(String author) {
        this.author = author;
    }

    public static class ContextBuilder{

        private List<GenTemplate> templates;

        private List<GenTable> tables;

        private Map<String,?> variables;

        private String backPackage;

        private String author;

        public static ContextBuilder builder(){
            return builder(null);
        }

        public static ContextBuilder builder(Map<String,?> variables){
            ContextBuilder contextBuilder =  new ContextBuilder();
            contextBuilder.variables = variables;
            return contextBuilder;
        }

        public ContextBuilder templates(GenTemplate...templates){
            this.templates = ArrayUtil.acceptNonNull(templates);
            return this;
        }

        public ContextBuilder tables(GenTable...tables){
            this.tables = ArrayUtil.acceptNonNull(tables);
            return this;
        }

        public ContextBuilder basePackage(String basePackage){
            this.backPackage = basePackage;
            return this;
        }

        public ContextBuilder author(String author){
            this.author = author;
            return this;
        }

        public GeneratorContext build(){
            GeneratorContext generatorContext  = new GeneratorContext(this.variables);
            generatorContext.setTemplates(this.templates);
            generatorContext.setBackPackage(this.backPackage);
            generatorContext.setAuthor(this.author);
            generatorContext.setTables(this.tables);
            return generatorContext;
        }




    }
}
