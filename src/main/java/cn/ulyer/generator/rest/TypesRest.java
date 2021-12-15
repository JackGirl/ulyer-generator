package cn.ulyer.generator.rest;


import cn.ulyer.generator.core.enums.DataBaseTypes;
import cn.ulyer.generator.core.enums.JavaTypes;
import cn.ulyer.generator.model.GenModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
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
     * 支持生成哪些模块
     * @return
     */
    @GetMapping("/modules")
    public List<GenModule> modules(){
        return mongoTemplate.findAll(GenModule.class);
    }

    /**
     * 支持的数据库类型
     * @return
     */
    @GetMapping("/dbTypes")
    public List<DataBaseTypes> dataBaseTypes(){
        return Arrays.asList(DataBaseTypes.values());
    }

    /**
     * 支持快速选择的javaType
     * @return
     */
    @GetMapping("/javaTypes")
    public List<String> javaTypes(){
        return Arrays.stream(JavaTypes.values()).map(d->d.getTypeName()).collect(Collectors.toList());
    }


    /**
     * 支持的js框架 ui等
     */
    @GetMapping("/jsFramework")
    public List<GenModule> jsFrameworks(){
        return mongoTemplate.findAll(GenModule.class);
    }




}
