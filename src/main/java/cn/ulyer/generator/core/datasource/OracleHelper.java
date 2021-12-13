package cn.ulyer.generator.core.datasource;

import cn.ulyer.generator.model.DataSourceProperty;
import cn.ulyer.generator.model.GenColumns;
import cn.ulyer.generator.model.GenTables;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

public class OracleHelper implements DataSourceHelper{
    @Override
    public DataSource create(DataSourceProperty dataSourceProperty) {
        return null;
    }

    @Override
    public List<GenTables> getTables(DataSource dataSource, Map<String, String> params) {
        return null;
    }

    @Override
    public List<GenColumns> getColumns(DataSource dataSource, Map<String, String> params) {
        return null;
    }

}
