package cn.ulyer.generator.rest;

import cn.ulyer.generator.model.GenColumn;
import cn.ulyer.generator.model.GenTable;
import cn.ulyer.generator.util.StringUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.bulk.BulkWriteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.util.Pair;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

    @Autowired
    private ObjectMapper objectMapper;

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
    public String updateColumn(@RequestBody Map<String,Object> data) throws JsonProcessingException {
        List<Map> list = (List<Map>) data.get("columns");
        BulkOperations mongoOperations = mongoTemplate.bulkOps(BulkOperations.BulkMode.UNORDERED,GenColumn.class);
        List<Pair<Query,Update>> pairs = new ArrayList<>();
        list.forEach(column->{
            Update update = new Update();
            column.forEach((k,v)->update.set((String) k,v));
            Pair<Query, Update> updatePair = Pair.of(Query.query(Criteria.where("_id").is(column.get("_id"))), update);
            pairs.add(updatePair);
        });
        mongoOperations.upsert(pairs);
        BulkWriteResult result = mongoOperations.execute();
        Integer modifyCount = result.getModifiedCount();
        return "data change:"+modifyCount+" not change:"+(list.size()-modifyCount);
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
