package cn.ulyer.generator.core.enums;

import lombok.Getter;

/**
 * @author ulyer
 * @date 2021.12.13
 */
@Getter
public enum SupportDaoTypes {
    /**
     * mybatis plus
     */
    MYBATIS_PLUS("mybatis plus");

    private String comment;

    private SupportDaoTypes(String comment){
        this.comment = comment;
    }

}
