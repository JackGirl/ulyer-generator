package cn.ulyer.generator.core.enums;

import lombok.Getter;

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
