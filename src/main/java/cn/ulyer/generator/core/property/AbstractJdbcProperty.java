package cn.ulyer.generator.core.property;

import lombok.Getter;
import lombok.Setter;

/**
 * 通用jdbc
 * @author ulyer
 * @date 2021.12.16
 */
@Getter
@Setter
public abstract class AbstractJdbcProperty implements DbProperty {

    protected String jdbcUrl;

    protected String driver;

    protected String username;

    protected String password;

}
