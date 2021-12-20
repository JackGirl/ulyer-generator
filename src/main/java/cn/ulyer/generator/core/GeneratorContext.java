package cn.ulyer.generator.core;

import cn.ulyer.generator.core.wrapper.ModuleWrapper;
import cn.ulyer.generator.core.wrapper.TableWrapper;
import cn.ulyer.generator.util.ArrayUtil;
import lombok.Getter;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author ulyer
 * @date 2021.12.13
 * 生成时上下文参数
 */
@Getter
public class GeneratorContext {

    private List<ModuleWrapper> modules;

    private List<TableWrapper> tables;

    private Map<String,Object> variables;

    private String author;

    private String backPackage;


    private GeneratorContext(Map<String,Object> variables){
        this.variables = variables==null? Collections.EMPTY_MAP:variables;
    }

    private void setTemplates(List<ModuleWrapper> modules) {
        this.modules = modules;
    }

    private void setTables(List<TableWrapper> tables) {
        this.tables = tables;
    }

    private void setVariables(Map<String, Object> variables) {
        this.variables = variables;
    }

    private void setBackPackage(String backPackage) {
        this.backPackage = backPackage;
    }

    private void setAuthor(String author) {
        this.author = author;
    }

    public static class ContextBuilder{

        private List<ModuleWrapper> modules;

        private List<TableWrapper> tables;

        private Map<String,Object> variables;

        private String backPackage;

        private String author;

        public static ContextBuilder builder(){
            return builder(null);
        }

        public static ContextBuilder builder(Map<String,Object> variables){
            ContextBuilder contextBuilder =  new ContextBuilder();
            contextBuilder.variables = variables;
            return contextBuilder;
        }

        public ContextBuilder tables(List<TableWrapper> tables){
            this.tables = tables;
            return this;
        }

        public ContextBuilder modules(List<ModuleWrapper> modules){
            this.modules = modules;
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
            generatorContext.setTemplates(this.modules);
            generatorContext.setBackPackage(this.backPackage);
            generatorContext.setAuthor(this.author);
            generatorContext.setTables(this.tables);
            return generatorContext;
        }




    }
}
