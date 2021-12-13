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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController(GenRest.NAME)
@RequestMapping("/genRest")
public class GenRest {

    public final static String NAME = "generatorRest";

    @Autowired
    private GenConfiguration genConfiguration;

    @Autowired
    private MongoTemplate mongoTemplate;

    @GetMapping("/listTableModels")
    public List<GenTables> genTables(GenTables queryModel){
        Query query = new Query();
        if(!StringUtil.isBlank(queryModel.getTableName())){
            query.addCriteria(Criteria.where("tableName").is(queryModel.getTableName()));
        }
        if(StringUtil.isBlank(queryModel.getDataBaseId())){
            query.addCriteria(Criteria.where("dataBaseId").is(queryModel.getDataBaseId()));
        }
        return mongoTemplate.find(query,GenTables.class);
    }

    @GetMapping("/listColumnModels")
    public List<GenColumns> genColumns(GenColumns queryModel){
        Query query = new Query();
        if(!StringUtil.isBlank(queryModel.getName())){
            query.addCriteria(Criteria.where("name").is(queryModel.getName()));
        }
        if(queryModel.getTableId()!=null){
            query.addCriteria(Criteria.where("tableId").is(queryModel.getTableId()));
        }
        return mongoTemplate.find(query,GenColumns.class);
    }


    /**
     * 导入查询出的表
     * @param tableNames
     * @param id
     * @return
     */
    @PostMapping("/import")
    public boolean importTable(@RequestParam List<String> tableNames, @RequestParam String id) throws Exception {
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
                List<GenColumns> genColumns = dataSourceHelper.getColumns(dataSource,genConfiguration,columnParams);
                genColumns.forEach(g->{
                    g.setTableId(genTable.get_id());
                    g.setJavaType(genConfiguration.convert(g.getJdbcType()).getName());
                    g.setPropertyName(g.getName());
                });
                mongoTemplate.insert(genColumns);
            }
        });
        return true;
    }

}
