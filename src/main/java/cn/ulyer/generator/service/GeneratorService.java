package cn.ulyer.generator.service;

import cn.ulyer.generator.core.GenConfiguration;
import cn.ulyer.generator.core.GeneratorParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.io.OutputStream;

/**
 * @author ulyer
 * @date 2021.12.13
 * 生成服务类
 */
@Service
public class GeneratorService {

    @Autowired
    private GenConfiguration genConfiguration;

    @Autowired
    private MongoTemplate mongoTemplate;

    public void generator(GeneratorParams generatorParams, OutputStream outputStream){

    }


}
