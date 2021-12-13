package cn.ulyer.generator.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("js_frameworks")
@Data
public class GenJSFramework {

    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    private Long _id;

    private String name;

    private List<String> uiFrameworks;
}
