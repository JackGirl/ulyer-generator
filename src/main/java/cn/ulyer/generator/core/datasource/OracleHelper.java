package cn.ulyer.generator.core.datasource;

import cn.ulyer.generator.core.GenConfiguration;
import cn.ulyer.generator.model.DataSourceProperty;
import cn.ulyer.generator.model.GenColumn;
import cn.ulyer.generator.model.GenTable;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

/**
 * @author ulyer
 * @date 2021.12.13
 */
public class OracleHelper implements DataSourceHelper{
    @Override
    public DataSource create(DataSourceProperty dataSourceProperty) {
        return null;
    }

    @Override
    public List<GenTable> getTables(DataSource dataSource, Map<String, String> params) {
        return null;
    }

    @Override
    public List<GenColumn> getColumns(DataSource dataSource, GenConfiguration configuration, Map<String, String> params) {
        return null;
    }

}
