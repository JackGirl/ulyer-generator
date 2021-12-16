package cn.ulyer.generator;

import cn.ulyer.generator.model.GenModule;
import cn.ulyer.generator.model.GenTemplate;
import com.fasterxml.jackson.core.JsonProcessingException;
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

public class GenModuleTest extends UlyerGeneratorApplicationTests {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void initModule() throws JsonProcessingException {
        String modules = readFile("data/GenModules.json");
        List<GenModule> list = objectMapper.readValue(modules, new TypeReference<List<GenModule>>() {
        });
        mongoTemplate.insert(list, GenModule.class);
    }

    @Test
    public void initTemplates() throws JsonProcessingException {
        String modules = readFile("data/GenTemplate.json");
        List<GenTemplate> list = objectMapper.readValue(modules, new TypeReference<List<GenTemplate>>() {
        });
        mongoTemplate.insert(list, GenTemplate.class);
    }

    private String readFile(String fileName) {
        ClassPathResource classPathResource = new ClassPathResource(fileName);
        try (InputStream stream = classPathResource.getInputStream()) {
            byte[] bytes = new byte[stream.available()];
            IOUtils.readFully(stream, bytes);
            return new String(bytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            System.out.println("error read:" + e.getMessage());
            return null;
        }
    }


}
