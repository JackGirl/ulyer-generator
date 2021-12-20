package cn.ulyer.generator.rest;

import cn.ulyer.generator.core.GenConfiguration;
import cn.ulyer.generator.core.GeneratorContext;
import cn.ulyer.generator.core.GeneratorParams;
import cn.ulyer.generator.core.datasource.DataSourceHelper;
import cn.ulyer.generator.core.types.DataBaseTypes;
import cn.ulyer.generator.core.wrapper.ModuleWrapper;
import cn.ulyer.generator.core.wrapper.TableWrapper;
import cn.ulyer.generator.model.*;
import cn.ulyer.generator.util.ArrayUtil;
import cn.ulyer.generator.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author ulyer
 * @date 2021.12.13
 */
@RestController(GenRest.NAME)
@RequestMapping("/genRest")
@Slf4j
public class GenRest {

    public final static String NAME = "generatorRest";

    @Autowired
    private GenConfiguration genConfiguration;

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 导入查询出database的表
     *
     * @param tableNames
     * @param id
     * @return
     */
    @PostMapping("/import")
    public String importTable(@RequestParam List<String> tableNames, @RequestParam String id) throws Exception {
        DataSourceProperty property = mongoTemplate.findOne(new Query(Criteria.where("_id").is(id)), DataSourceProperty.class);
        if (property == null) {
            return "success: 0";
        }
        DataSourceHelper dataSourceHelper = genConfiguration.getDataSourceHelper(DataBaseTypes.valueOf(property.getType()));
        DataSource dataSource = dataSourceHelper.create(property);
        Map<String, String> params = new HashMap<>();
        params.put("tableName", ArrayUtil.iteratorToString(tableNames.iterator(), ","));
        List<GenTable> genTables = dataSourceHelper.getTables(dataSource, params);
        int successCount = 0;
        for (GenTable genTable : genTables) {
            final long count = mongoTemplate.count(Query.query(Criteria.where("dataSourceId").is(id).and("tableName").is(genTable.getTableName())), GenTable.class);
            if (count == 0) {
                genTable.setClassName(StringUtil.toCamelCase(genTable.getTableName()));
                genTable.setDataSourceId(property.get_id());
                genTable.setDataSourceName(property.getName());
                mongoTemplate.insert(genTable);
                Map<String, String> columnParams = new HashMap<>();
                columnParams.put("tableName", genTable.getTableName());
                List<GenColumn> genColumns = dataSourceHelper.getColumns(dataSource, genConfiguration, columnParams);
                genColumns.forEach(g -> {
                    g.setTableId(genTable.get_id());
                    g.setJavaType(genConfiguration.convert(g.getJdbcType()).getName());
                    g.setPropertyName(StringUtil.toCamelCase(g.getName()));
                });
                mongoTemplate.insert(genColumns, GenColumn.class);
                successCount++;
            }
        }
        return "success:" + successCount + " fail：" + (genTables.size() - successCount);
    }

    /**
     * 代码生成
     * result keys:  success(bool) path(filePath) error (string)
     *
     * @param generatorParams
     * @return
     */
    @PostMapping("/start")
    public Map<String, Object> start(@Validated @RequestBody GeneratorParams generatorParams) {
        Map<String, Object> result = new HashMap<>();
        result.put("success", false);
        // 构造 generatorContext
        List<GenModule> modules = mongoTemplate.find(Query.query(Criteria.where("moduleName").in(generatorParams.getGenModules())), GenModule.class);
        if(CollectionUtils.isEmpty(modules)){
            result.put("error","模块必选");
            return result;
        }
        List<GenTable> tables = mongoTemplate.find(Query.query(Criteria.where("_id").in(generatorParams.getGenTables())), GenTable.class);
        // 。。。额 就是不想判断 和引入新的依赖来做了
        tables = ArrayUtil.acceptNonNull(tables);
        List<ModuleWrapper> moduleWrappers = new ArrayList<>();
        modules.forEach(module->{
            moduleWrappers.add(ModuleWrapper.of(module,mongoTemplate.find(Query.query(Criteria.where("name").in(module.getTemplates())),GenTemplate.class)));
        });
        List<TableWrapper> tableWrappers = new ArrayList<>();
        tables.forEach(table->{
            tableWrappers.add(TableWrapper.of(table,mongoTemplate.find(Query.query(Criteria.where("tableId").is(table.get_id())),GenColumn.class)));
        });
        GeneratorContext generatorContext = GeneratorContext.ContextBuilder
                .builder(generatorParams.getExtendsVariables())
                .basePackage(generatorParams.getBasePackage())
                .author(generatorParams.getAuthor())
                .tables(tableWrappers)
                .modules(moduleWrappers)
                .build();
        Path directory;
        try {
            directory = Files.createTempDirectory(UUID.randomUUID().toString());
        } catch (IOException e) {
            log.error("create tempDir error:{}", e);
            result.put("error", e.getMessage());
            return result;
        }
        try {
            genConfiguration.getTemplateGenerator().generator(generatorContext, directory.toFile().getPath());
        } catch (Exception e) {
            result.put("error",e.getMessage());
            return result;
        }
        result.put("success",true);
        result.put("path",directory.toFile().getPath());
        return result;
    }
}
