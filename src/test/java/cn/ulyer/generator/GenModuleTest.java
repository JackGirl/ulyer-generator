package cn.ulyer.generator;

import cn.ulyer.generator.model.GenModule;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class GenModuleTest extends UlyerGeneratorApplicationTests{

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void initData(){
        ClassPathResource classPathResource = new ClassPathResource("data/GenModules.json");
        try (InputStream stream =  classPathResource.getInputStream()){
            mongoTemplate.createCollection(GenModule.class);
            byte[] bytes = new byte[stream.available()];
            IOUtils.readFully(stream,bytes);
            List<GenModule> list = objectMapper.readValue(new String(bytes, StandardCharsets.UTF_8), new TypeReference<List<GenModule>>(){});
            mongoTemplate.insert(list, GenModule.class);
            IOUtils.closeQuietly(stream);
        } catch (IOException e) {
            System.out.println("error read:"+e.getMessage());
        }
    }


}
