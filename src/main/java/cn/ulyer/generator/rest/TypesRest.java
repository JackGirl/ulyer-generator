package cn.ulyer.generator.rest;


import cn.ulyer.generator.core.types.DataBaseTypes;
import cn.ulyer.generator.core.types.FileTypes;
import cn.ulyer.generator.core.types.JavaTypes;
import cn.ulyer.generator.model.GenModule;
import cn.ulyer.generator.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * @author ulyer
 * @date 2021.12.14
 * 配置的数据 返回
 */
@RequestMapping("/types")
@RestController
public class TypesRest {

    @Autowired
    private MongoTemplate mongoTemplate;


    /**
     * 支持的数据库类型
     * @return
     */
    @GetMapping("/dbTypes")
    public List<Map<String,String>> dataBaseTypes(){
        return Arrays.stream(DataBaseTypes.values()).map(value->{
            Map<String,String> data = new HashMap<>();
            data.put("type",value.name());
            data.put("propertyJson", StringUtil.toJson(value.getDbProperty(),true));
            return data;
        }).collect(Collectors.toList());
    }

    /**
     * 支持快速选择的javaType
     * @return
     */
    @GetMapping("/javaTypes")
    public List<String> javaTypes(){
        return Arrays.stream(JavaTypes.values()).map(d->d.getTypeName()).collect(Collectors.toList());
    }

    @GetMapping("/fileTypes")
    public List<Map<String,String>> fileTypes(){
        return Arrays.stream(FileTypes.values()).map(value->{
            Map<String,String> data = new HashMap<>();
            data.put("type",value.name());
            data.put("mode", value.getMode());
            return data;
        }).collect(Collectors.toList());
    }




}
