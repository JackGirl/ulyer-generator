package cn.ulyer.generator.config;

import cn.ulyer.generator.core.GenConfiguration;
import freemarker.template.TemplateModelException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;

@Configuration
public class GenAutoConfiguration {

    @Autowired
    private freemarker.template.Configuration configuration;

    @Autowired
    private Environment environment;

    @PostConstruct
    public void  setSharedVariable() throws TemplateModelException {
        configuration.setSharedVariable("contextPath",environment.getProperty("server.servlet.context-path"));
    }

    @Bean
    public GenConfiguration genConfiguration(){
        return new GenConfiguration();
    }


}
