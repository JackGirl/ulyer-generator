package cn.ulyer.generator.core.datasource;

import cn.ulyer.generator.model.DataSourceProperty;
import cn.ulyer.generator.model.GenColumns;
import cn.ulyer.generator.model.GenTables;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

public interface DataSourceHelper {

    DataSource create(DataSourceProperty dataSourceProperty);

    List<GenTables> getTables(DataSource dataSource, Map<String,String> params);

    List<GenColumns> getColumns(DataSource dataSource, Map<String,String> params);
}
