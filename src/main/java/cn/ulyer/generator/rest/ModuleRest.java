package cn.ulyer.generator.rest;

import cn.ulyer.generator.model.GenModule;
import cn.ulyer.generator.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ulyer
 * @date 2021/12/15
 * 模块配置
 */
@RestController
@RequestMapping("/moduleRest")
public class ModuleRest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @GetMapping("/list")
    public List<GenModule> list(String moduleName,String group){
        Query query = new Query();
        if(!StringUtil.isBlank(moduleName)){
            query.addCriteria(Criteria.where("moduleName").is(moduleName));
        }
        if(!StringUtil.isBlank(group)){
            query.addCriteria(Criteria.where("group").is(group));
        }
        return mongoTemplate.find(query,GenModule.class);
    }


    @DeleteMapping("/remove")
    public boolean remove(@RequestParam String moduleName){
        mongoTemplate.remove(Query.query(Criteria.where("moduleName").is(moduleName)),GenModule.class);
        return true;
    }

    @PostMapping("saveOrUpdate")
    public boolean saveOrUpdate(@RequestBody GenModule genModule){
        mongoTemplate.save(genModule);
        return true;
    }
}
