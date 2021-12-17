package cn.ulyer.generator.core.types;

import lombok.Getter;

/**
 * @author ulyer
 * @date 2021.12.14
 * 默认支持快速选择的下拉框
 */
@Getter
public enum JavaTypes {

    STRING("java.lang.String"),
    DATE("java.util.Date"),
    INTEGER("java.lang.Integer"),
    LONG("java.lang.Long"),
    BIG_DECIMAL("java.math.BigDecimal"),
    BOOL("java.lang.Boolean");




    JavaTypes(String typeName){
        this.typeName = typeName;
    }


    private String typeName;

}
