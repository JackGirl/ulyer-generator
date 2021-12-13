package cn.ulyer.generator.core.datasource;

import cn.ulyer.generator.core.GenConfiguration;
import cn.ulyer.generator.model.DataSourceProperty;
import cn.ulyer.generator.model.GenColumns;
import cn.ulyer.generator.model.GenTables;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

/**
 * @author ulyer
 * @date 2021.12.13
 * 数据库辅助类
 */
public interface DataSourceHelper {

    DataSource create(DataSourceProperty dataSourceProperty) throws Exception;

    List<GenTables> getTables(DataSource dataSource, Map<String,String> params);

    List<GenColumns> getColumns(DataSource dataSource, GenConfiguration configuration, Map<String, String> params);
}
