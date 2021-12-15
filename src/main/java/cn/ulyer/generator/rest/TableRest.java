package cn.ulyer.generator.rest;

import cn.ulyer.generator.model.GenColumn;
import cn.ulyer.generator.model.GenTable;
import cn.ulyer.generator.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author ulyer
 * @date 2021/12/14
 * 配置表 列相关
 */
@RestController
@RequestMapping("/tableRest")
public class TableRest {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 查询可生成表
     * @param queryModel
     * @return
     */
    @GetMapping("/listTableModels")
    public List<GenTable> genTables(GenTable queryModel){
        Query query = new Query();
        if(!StringUtil.isBlank(queryModel.getTableName())){
            query.addCriteria(Criteria.where("tableName").is(queryModel.getTableName()));
        }
        if(!StringUtil.isBlank(queryModel.getDataSourceId())){
            query.addCriteria(Criteria.where("dataSourceId").is(queryModel.getDataSourceId()));
        }
        return mongoTemplate.find(query, GenTable.class);
    }

    /**
     * 查询表下的列
     * @param queryModel
     * @return
     */
    @GetMapping("/listColumnModels")
    public List<GenColumn> genColumns(GenColumn queryModel){
        Query query = new Query();
        if(!StringUtil.isBlank(queryModel.getName())){
            query.addCriteria(Criteria.where("name").is(queryModel.getName()));
        }
        if(queryModel.getTableId()!=null){
            query.addCriteria(Criteria.where("tableId").is(queryModel.getTableId()));
        }
        return mongoTemplate.find(query, GenColumn.class);
    }


    /**
     * 获取单个表
     * @param tableId
     * @return
     */
    @GetMapping("/details/{tableId}")
    public GenTable getTableById(@PathVariable String tableId){
        return mongoTemplate.findOne(Query.query(Criteria.where("_id").is(tableId)), GenTable.class);
    }

    /**
     * 更新
     * @param tables
     * @return
     */
    @PostMapping("/update")
    public boolean updateTable(@RequestBody GenTable tables){
        Update update = new Update();
        update.set("comment",tables.getComment())
                        .set("className",tables.getClassName());
        return mongoTemplate.updateFirst(Query.query(Criteria.where("_id").is(tables.get_id())),update, GenTable.class).getModifiedCount()>0;
    }

    @PostMapping("/updateColumns")
    public String updateTable(@RequestBody Map<String,Object> data){
        return null;
    }

    @DeleteMapping("/remove/{tableId}")
    public boolean removeTable(@PathVariable String tableId){
        mongoTemplate.remove(Query.query(Criteria.where("tableId").is(tableId)), GenColumn.class);
        mongoTemplate.remove(Query.query(Criteria.where("_id").is(tableId)), GenTable.class);
        return true;
    }


    @PostMapping("/createColumn")
    public String createColumn (@RequestBody GenColumn columns){
        mongoTemplate.insert(columns);
        return columns.get_id();
    }

}
