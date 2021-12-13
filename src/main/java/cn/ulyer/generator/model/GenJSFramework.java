package cn.ulyer.generator.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * @author ulyer
 * @date 2021.12.13
 * 可选生成ui js框架配置
 */
@Document("js_frameworks")
@Data
public class GenJSFramework {

    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    private String _id;

    private String name;

    private List<String> uiFrameworks;
}
