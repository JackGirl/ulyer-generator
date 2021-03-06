package cn.ulyer.generator.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * @author ulyer
 * @date 2021.12.13
 * 可选生成ui js框架配置
 */
@Document("gen_module")
@Data
public class GenModule {

    @Id
    private String moduleName;

    private List<String> templates;

    private String group;

}

