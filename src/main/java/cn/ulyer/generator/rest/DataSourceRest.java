package cn.ulyer.generator.rest;


import cn.ulyer.generator.core.GenConfiguration;
import cn.ulyer.generator.core.datasource.DataSourceHelper;
import cn.ulyer.generator.core.enums.DataBaseTypes;
import cn.ulyer.generator.model.DataSourceProperty;
import cn.ulyer.generator.model.GenTables;
import cn.ulyer.generator.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author ulyer
 * @date 2021.12.13
 * 数据源rest
 */
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
        if(!StringUtil.isBlank(queryModel.getName())){
            query.addCriteria(Criteria.where("name").is(queryModel.getName()));
        }
        if(!StringUtil.isBlank(queryModel.getType())){
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
    public List<String> getDataSourceTables(@PathVariable String dataBaseId) throws Exception {
        DataSourceProperty property =  mongoTemplate.findOne(new Query(Criteria.where("_id").is(dataBaseId)),DataSourceProperty.class);
        if(property==null){
            return Collections.EMPTY_LIST;
        }
        DataSourceHelper dataSourceHelper = genConfiguration.getDataSourceHelper(DataBaseTypes.valueOf(property.getType()));
        List<GenTables> tables = dataSourceHelper.getTables(dataSourceHelper.create(property),null);
        return tables.stream().map(GenTables::getTableName).collect(Collectors.toList());
    }






}
