package cn.ulyer.generator.rest;


import cn.ulyer.generator.core.GenConfiguration;
import cn.ulyer.generator.core.datasource.DataSourceHelper;
import cn.ulyer.generator.core.enums.DataBaseTypes;
import cn.ulyer.generator.model.DataSourceProperty;
import cn.ulyer.generator.model.GenColumns;
import cn.ulyer.generator.model.GenTables;
import cn.ulyer.generator.util.ArrayUtil;
import cn.ulyer.generator.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequestMapping("/dataSourceRest")
@RestController(DataSourceRest.NAME)
public class DataSourceRest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private GenConfiguration genConfiguration;

    public final static String NAME = "DataSourceRest";

    /**
     * 数据源配置
     * @return
     */
    @GetMapping("/listDataSource")
    public List<DataSourceProperty> list(DataSourceProperty queryModel){
        Query query = new Query();
        if(StringUtil.isBlank(queryModel.getName())){
            query.addCriteria(Criteria.where("name").is(queryModel.getName()));
        }
        if(StringUtil.isBlank(queryModel.getType())){
            query.addCriteria(Criteria.where("type").is(queryModel.getType()));
        }
        return mongoTemplate.find(query, DataSourceProperty.class);
    }

    /**
     * 添加一个数据源配置
     * @param dataSourceProperty
     * @return
     */
    @PostMapping("/addDataSourceConfig")
    public boolean add(@RequestBody DataSourceProperty dataSourceProperty){
        mongoTemplate.save(dataSourceProperty);
        return true;
    }

    /**
     * 删除存储的数据源配置
     * @param id
     * @return
     */
    @DeleteMapping("/remove/{id}")
    public boolean remove(@PathVariable String id){
        mongoTemplate.remove(Query.query(Criteria.where("_id").is(id)));
        return true;
    }

    /**
     * 查询选择的数据库获取表信息返回
     * @return
     */
    @GetMapping("/datasourceTables/{dataBaseId}")
    public List<String> getDataSourceTables(@PathVariable String dataBaseId){
        DataSourceProperty property =  mongoTemplate.findOne(new Query(Criteria.where("_id").is(dataBaseId)),DataSourceProperty.class);
        if(property==null){
            return Collections.EMPTY_LIST;
        }
        DataSourceHelper dataSourceHelper = genConfiguration.getDataSourceHelper(DataBaseTypes.valueOf(property.getType()));
        List<GenTables> tables = dataSourceHelper.getTables(dataSourceHelper.create(property),null);
        return tables.stream().map(GenTables::getTableName).collect(Collectors.toList());
    }

    @PostMapping("/updateColumn")
    public boolean updateColumn(@RequestBody GenColumns columns){
        mongoTemplate.save(columns);
        return true;
    }

    @PostMapping("/updateTable")
    public boolean updateTable(@RequestBody GenTables table){
        mongoTemplate.save(table);
        return true;
    }

    /**
     * 导入查询出的表
     * @param tableNames
     * @param id
     * @return
     */
    @GetMapping("/import")
    public boolean importTable(@RequestParam List<String> tableNames,@RequestParam Long id){
       DataSourceProperty property =  mongoTemplate.findOne(new Query(Criteria.where("_id").is(id)),DataSourceProperty.class);
       if(property==null){
           return false;
       }
       DataSourceHelper dataSourceHelper = genConfiguration.getDataSourceHelper(DataBaseTypes.valueOf(property.getType()));
       DataSource dataSource = dataSourceHelper.create(property);
       Map<String,String> params = new HashMap<>();
       params.put("tableNames", ArrayUtil.iteratorToString(tableNames.iterator(),","));
       List<GenTables> genTables = dataSourceHelper.getTables(dataSource,params);
       genTables.forEach(genTable -> {
           final long count = mongoTemplate.count(Query.query(Criteria.where("dataBaseId").is(id).and("tableName").is(genTable.getTableName())),GenTables.class);
           if(count==0){
               mongoTemplate.insert(genTable);
               Map<String,String> columnParams = new HashMap<>();
                columnParams.put("tableName",genTable.getTableName());
               List<GenColumns> genColumns = dataSourceHelper.getColumns(dataSource,columnParams);
               mongoTemplate.insert(genColumns);
           }
       });
       return true;
    }


}