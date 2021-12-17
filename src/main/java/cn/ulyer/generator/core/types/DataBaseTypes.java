package cn.ulyer.generator.core.types;

import cn.ulyer.generator.core.property.DbProperty;
import cn.ulyer.generator.core.property.MysqlProperty;
import cn.ulyer.generator.core.property.OracleProperty;
import lombok.Getter;

/**
 * @author ulyer
 * @date 2021.12.13
 */
@Getter
public enum DataBaseTypes {


    MYSQL(new MysqlProperty()),
    ORACLE(new OracleProperty());

     DataBaseTypes(DbProperty dbProperty){
        this.dbProperty = dbProperty;
    }
    private DbProperty dbProperty ;
}
