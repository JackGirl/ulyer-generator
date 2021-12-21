package cn.ulyer.generator.core.gen;

import cn.ulyer.generator.core.GeneratorContext;
import cn.ulyer.generator.core.wrapper.ModuleWrapper;
import cn.ulyer.generator.core.wrapper.TableWrapper;
import cn.ulyer.generator.model.GenTemplate;
import cn.ulyer.generator.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.util.CollectionUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author ulyer
 * @date 2021/12/20
 * 基础模板类 定义生成逻辑
 */
@Slf4j
public abstract class BaseTemplateGenerator implements TemplateGenerator {


    @Override
    public void generator(GeneratorContext generatorContext, String folderPath) {
        log.debug("========================================开始生成文件 time:{}", new Date());
        File file = new File(folderPath);
        if (!file.exists() && !file.isDirectory()) {
            log.error("========================================生成失败:path need be a directory");
            throw new IllegalStateException("path need be a directory");
        }
        log.debug("========================================生成文件夹路径:{}", file.getAbsolutePath());
        List<ModuleWrapper> modules = generatorContext.getModules();
        if (CollectionUtils.isEmpty(modules)) {
            return;
        }
        log.debug("======================================== 生成模块 :{}", modules);
        List<TableWrapper> tables = generatorContext.getTables();
        for (ModuleWrapper module : modules) {
            List<GenTemplate> templates = module.getTemplates();
            if (CollectionUtils.isEmpty(templates)) {
                continue;
            }
            log.debug("======================================== 生成模板 :{}", templates.stream().map(GenTemplate::getName).collect(Collectors.toList()));

            //每个 table 循环一次 template 如果没有table  则用  variable 执行一次template
            List<TableWrapper> container = new LinkedList<>();
            if (CollectionUtils.isEmpty(tables)) {
                container.add(null);
            } else {
                container.addAll(tables);
            }
            for (TableWrapper wrapper : container) {
                for (GenTemplate template : templates) {
                    Map<String, Object> variables = processVariables(generatorContext, module, template, wrapper);
                    try {
                        String result = process(template, variables);
                        createFile(folderPath, result, template, variables);
                    } catch (Exception e) {
                        log.error("生成模板出错 template: {} error:{}", template.getName(), e);
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        log.debug("======================================== 生成结束  SUCCESS");
    }

    private String processPathEnding(String filePath) {
        if (!filePath.endsWith("/") && !filePath.endsWith(File.separator)) {
            filePath = filePath + "/";
        }
        return filePath;
    }

    /**
     * 生成文件 根据 el表达式解析路径和变量
     *
     * @param baseFolder
     * @param content
     * @param template
     * @param variables
     */
    private void createFile(String baseFolder, String content, GenTemplate template, Map<String, Object> variables) {
        String fileType = template.getType();
        String filePathName = StringUtil.processElExpression(template.getNameExpression(), variables);
        String[] namePath = filePathName.split("/");
        StringBuilder fileFolder = new StringBuilder(((String) variables.get("basePackage")).replaceAll("\\.","/"));
        if (namePath.length > 1) {
            IntStream.range(0, namePath.length - 1).forEach(action -> {
                fileFolder.append("/").append(namePath[action]);
            });
        }
        File folder = new File(processPathEnding(baseFolder) + fileFolder);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        String fileName = namePath[namePath.length - 1] + "." + fileType;
        String filePath = folder.getAbsolutePath();
        filePath = processPathEnding(filePath);
        try {
            OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(filePath + fileName));
            IOUtils.write(content, outputStream, StandardCharsets.UTF_8);
            IOUtils.close(outputStream);
        } catch (IOException e) {
            log.debug("======================================== 生成失败:{}", e);
            return;
        }
        log.debug("======================================== 生成文件 :{} 路径:{}", fileName, filePath);
    }

    /**
     * 处理变量
     *
     * @param context
     * @param module
     * @param template
     * @param table
     * @return
     */
    private Map<String, Object> processVariables(GeneratorContext context, ModuleWrapper module, GenTemplate template, TableWrapper table) {
        Map<String, Object> processVariables = context.getVariables();
        processVariables.putAll(context.getVariables());
        processVariables.put("table", table == null ? null : table.getGenTable());
        processVariables.put("module", module.getModule());
        GenTemplate nullableTemplate = new GenTemplate();
        nullableTemplate.setName(template.getName());
        nullableTemplate.setNameExpression(template.getNameExpression());
        nullableTemplate.setTemplate(template.getType());
        processVariables.put("template", nullableTemplate);
        processVariables.put("columns", table == null ? null : table.getColumns());
        processVariables.put("idType", table == null ? null : table.getIdType());
        processVariables.put("idProp",table==null? null: table.getIdProp());
        processVariables.put("author", context.getAuthor());
        processVariables.put("basePackage", context.getBackPackage());
        return processVariables;
    }

    protected abstract String process(GenTemplate template, Map<String, Object> variables) throws Exception;


}
