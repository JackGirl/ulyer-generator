package cn.ulyer.generator.rest;

import cn.ulyer.generator.core.GenConfiguration;
import cn.ulyer.generator.core.datasource.DataSourceHelper;
import cn.ulyer.generator.core.enums.DataBaseTypes;
import cn.ulyer.generator.model.DataSourceProperty;
import cn.ulyer.generator.model.GenColumn;
import cn.ulyer.generator.model.GenTable;
import cn.ulyer.generator.util.ArrayUtil;
import cn.ulyer.generator.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ulyer
 * @date 2021.12.13
 */
@RestController(GenRest.NAME)
@RequestMapping("/genRest")
public class GenRest {

    public final static String NAME = "generatorRest";

    @Autowired
    private GenConfiguration genConfiguration;

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 导入查询出database的表
     * @param tableNames
     * @param id
     * @return
     */
    @PostMapping("/import")
    public String importTable(@RequestParam List<String> tableNames, @RequestParam String id) throws Exception {
        DataSourceProperty property =  mongoTemplate.findOne(new Query(Criteria.where("_id").is(id)),DataSourceProperty.class);
        if(property==null){
            return "success: 0";
        }
        DataSourceHelper dataSourceHelper = genConfiguration.getDataSourceHelper(DataBaseTypes.valueOf(property.getType()));
        DataSource dataSource = dataSourceHelper.create(property);
        Map<String,String> params = new HashMap<>();
        params.put("tableName", ArrayUtil.iteratorToString(tableNames.iterator(),","));
        List<GenTable> genTables = dataSourceHelper.getTables(dataSource,params);
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
        return "success:"+successCount+" fail："+(genTables.size()-successCount);
    }

}
