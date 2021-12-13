package cn.ulyer.generator.model;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("gen_tables")
@Data
public class GenTables {

    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    private Long _id;

    private String tableName;

    private String comment;

    private String className;

    private String dataBaseId;

    private String dataBaseName;


}
