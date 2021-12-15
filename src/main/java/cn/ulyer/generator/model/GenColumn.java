package cn.ulyer.generator.model;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author ulyer
 * @date 2021.12.13
 * 生成字段配置
 */
@Document("gen_column")
@Data
public class GenColumn {

    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    private String _id;

    private String tableId;

    private boolean uniqueId;

    private String name;

    private String propertyName;

    private String comment;

    private String javaType;

    private String jdbcType;

    private boolean nullable;



}
