package cn.ulyer.generator.core.datasource;

import cn.ulyer.generator.core.GenConfiguration;
import cn.ulyer.generator.model.DataSourceProperty;
import cn.ulyer.generator.model.GenColumns;
import cn.ulyer.generator.model.GenTables;
import cn.ulyer.generator.util.StringUtil;
import com.alibaba.druid.DbType;
import com.alibaba.druid.pool.DruidDataSource;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class MysqlHelper implements DataSourceHelper{

    private final ObjectMapper objectMapper = new ObjectMapper();

    final String TABLE_QUERY = "SELECT table_name tableName, table_comment comment FROM information_schema.`TABLES` where table_schema = (select database())";

    final static String DRIVER = "com.mysql.cj.jdbc.Driver";

    final String COLUMN_QUERY = "SELECT \n" +
            "            table_name AS tableName, \n" +
            "            column_name AS NAME, \n" +
            "            data_type AS jdbcType, \n" +
            "            case column_key when 'PRI' THEN TRUE ELSE FALSE END AS uniqueId, \n" +
            "            column_comment AS COMMENT , \n" +
            "            CASE IS_NULLABLE WHEN 'YES' THEN  TRUE ELSE FALSE END  AS NULLABLE \n" +
            "            FROM \n" +
            "            information_schema.COLUMNS  \n" +
            "            WHERE \n" +
            "            table_name = ?  \n" +
            "            AND table_schema = ( SELECT DATABASE ( ) )";

    @Override
    public DataSource create(DataSourceProperty dataSourceProperty) throws JsonProcessingException {
        String json = dataSourceProperty.getConnectionProperty();
        JsonNode node = objectMapper.readTree(json);
        String url = node.get("jdbcUrl").asText();
        JsonNode driverNode = node.get("driver");
        String driver = driverNode == null ? DRIVER : driverNode.asText(DRIVER);
        String username = node.get("username").asText();
        String password = node.get("password").asText();
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(url);
        dataSource.setDriverClassName(driver);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setMaxActive(5);
        dataSource.setDbType(DbType.mysql);
        return dataSource;
    }

    @Override
    public List<GenTables> getTables(DataSource dataSource, Map<String, String> params) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        String queryString = TABLE_QUERY;
        List query = new ArrayList();
        if(params!=null){
            String tableName = params.get("tableName");
            if(!StringUtil.isBlank(tableName)){
                queryString+=" and instr(?,table_name)";
                query.add(tableName);
            }
        }
        List<GenTables> tables =   jdbcTemplate.query(queryString,  new BeanPropertyRowMapper<>(GenTables.class),query.toArray());
        return tables==null? Collections.EMPTY_LIST:tables;
    }

    @Override
    public List<GenColumns> getColumns(DataSource dataSource, GenConfiguration configuration,Map<String, String> params) {
        String tableName = params.get("tableName");
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<GenColumns> columns = jdbcTemplate.query(COLUMN_QUERY,new BeanPropertyRowMapper<>(GenColumns.class),tableName );
        return columns==null?Collections.EMPTY_LIST:columns;
    }

}
