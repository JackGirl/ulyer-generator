package cn.ulyer.generator.core.datasource;

import cn.ulyer.generator.core.GenConfiguration;
import cn.ulyer.generator.core.property.MysqlProperty;
import cn.ulyer.generator.model.DataSourceProperty;
import cn.ulyer.generator.model.GenColumn;
import cn.ulyer.generator.model.GenTable;
import cn.ulyer.generator.util.StringUtil;
import com.alibaba.druid.DbType;
import com.alibaba.druid.pool.DruidDataSource;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.util.*;

/**
 * @author ulyer
 * @date 2021.12.13
 */
public class MysqlHelper implements DataSourceHelper {


    final String TABLE_QUERY = "SELECT table_name tableName, table_comment comment FROM information_schema.`TABLES`  where table_schema = (select database())";

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
        MysqlProperty mysqlProperty =StringUtil.fromJson(json,MysqlProperty.class);
        String driver = StringUtil.isBlank(mysqlProperty.getDriver()) ? DRIVER : mysqlProperty.getDriver();
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(mysqlProperty.getJdbcUrl());
        dataSource.setDriverClassName(driver);
        dataSource.setUsername(mysqlProperty.getUsername());
        dataSource.setPassword(mysqlProperty.getPassword());
        dataSource.setMaxActive(5);
        dataSource.setDbType(DbType.mysql);
        return dataSource;
    }


    @Override
    public List<GenTable> getTables(DataSource dataSource, Map<String, String> params) {
        NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        String queryString = TABLE_QUERY;
        Map<String,Object> query = new HashMap<>();
        if(params!=null){
            String tableName = params.get("tableName");
            if(!StringUtil.isBlank(tableName)){
                queryString+=" and table_name in(:tableName)";
                query.put("tableName", Arrays.asList(tableName.split(",")));
            }
        }
        List<GenTable> tables =   jdbcTemplate.query(queryString,query,new BeanPropertyRowMapper<>(GenTable.class));
        return tables==null? Collections.EMPTY_LIST:tables;
    }

    @Override
    public List<GenColumn> getColumns(DataSource dataSource, GenConfiguration configuration, Map<String, String> params) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<GenColumn> columns = jdbcTemplate.query(COLUMN_QUERY,new BeanPropertyRowMapper<>(GenColumn.class),params.get("tableName") );
        return columns==null? Collections.EMPTY_LIST:columns;
    }

}
