package cn.ulyer.generator.rest;

import cn.ulyer.generator.model.GenTemplate;
import cn.ulyer.generator.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author ulyer
 * @date 2021、12、15
 * 模板配置相关
 */
@RestController
@RequestMapping("/templateRest")
public class TemplateRest {


    @Autowired
    private MongoTemplate mongoTemplate;

    @GetMapping("/list")
    public List<GenTemplate> list(String name){
        Query query = new Query();
        if(!StringUtil.isBlank(name)){
            query.addCriteria(Criteria.where("name").regex(".*"+name+".*"));
        }
        return mongoTemplate.find(query,GenTemplate.class);
    }


    @DeleteMapping("/removeTemplate")
    public boolean remove(@RequestParam String name){
        mongoTemplate.remove(Query.query(Criteria.where("name").is(name)),GenTemplate.class);
        return true;
    }

    @PostMapping("/saveOrUpdate")
    public boolean saveOrUpdate(@RequestBody GenTemplate template){
        mongoTemplate.save(template);
        return true;
    }



}
