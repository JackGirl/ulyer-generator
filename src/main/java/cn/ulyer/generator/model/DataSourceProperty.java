package cn.ulyer.generator.model;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author ulyer
 * @date 2021.12.13
 * 数据库配置
 */
@Data
@Document("datasource")
public class DataSourceProperty {

    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    private String _id;

    private String name;

    private String type;
    /**
     * json
     */
    private String connectionProperty;

}
