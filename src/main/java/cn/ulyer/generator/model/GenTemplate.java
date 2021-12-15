package cn.ulyer.generator.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("gen_template")
@Data
public class GenTemplate {

    @Id
    private String name;

    private String template;
}
