package cn.ulyer.generator.model;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("gen_columns")
@Data
public class GenColumns {

    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    private Long _id;

    private String tableId;

    private String name;

    private String comment;

    private String javaType;

    private String jdbcType;



}
