package cn.ulyer.generator.core.datasource;

import cn.ulyer.generator.core.GenConfiguration;
import cn.ulyer.generator.core.property.OracleProperty;
import cn.ulyer.generator.model.DataSourceProperty;
import cn.ulyer.generator.model.GenColumn;
import cn.ulyer.generator.model.GenTable;
import cn.ulyer.generator.util.StringUtil;
import com.alibaba.druid.DbType;
import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.util.*;

/**
 * @author ulyer
 * @date 2021.12.13
 */
public class OracleHelper implements DataSourceHelper {

    final String TABLE_QUERY = "select t.table_name as \"tableName\",c.comments as \"comment\"  from user_tables t left join user_tab_comments c on t.table_name = c.table_name where 1=1 ";

    final String COLUMN_QUERY = "select c.table_name as \"tableName\" , c.column_name  as \"name\" ,c.data_type as \"jdbcType\",   decode(c.nullable,'Y',1,0) as \"nullable\" ,co.comments as \"comment\", decode(au.CONSTRAINT_type,'P',1,0) as \"uniqueId\" \n" +
            "from user_tab_columns c\n" +
            "left join user_col_comments co on c.table_name = co.table_name and c.column_name=co.column_name \n" +
            "left join  user_cons_columns  cu on   c.column_name=cu.column_name  and c.table_name=cu.table_name and cu.position=1\n" +
            "left join user_constraints au on cu.constraint_name=au.constraint_name and au.CONSTRAINT_type='P'\n" +
            "where\n" +
            " c.table_name = ?\n";

    @Override
    public DataSource create(DataSourceProperty dataSourceProperty) {
        String json = dataSourceProperty.getConnectionProperty();
        OracleProperty oracleProperty = StringUtil.fromJson(json, OracleProperty.class);
        String driver = oracleProperty.getDriver();
        DruidDataSource oracle = new DruidDataSource();
        oracle.setUrl(oracleProperty.getJdbcUrl());
        oracle.setDriverClassName(driver);
        oracle.setUsername(oracleProperty.getUsername());
        oracle.setPassword(oracleProperty.getPassword());
        oracle.setMaxActive(5);
        oracle.setDbType(DbType.oracle);
        return oracle;
    }

    /**
     * use like mysql template add alias
     * different sql
     * @param dataSource
     * @param params
     * @return
     */
    @Override
    public List<GenTable> getTables(DataSource dataSource, Map<String, String> params) {
        NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        String queryString = TABLE_QUERY;
        Map<String,Object> query = new HashMap<>();
        if(params!=null){
            String tableName = params.get("tableName");
            if(!StringUtil.isBlank(tableName)){
                queryString+=" and t.table_name in(:tableName)";
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
