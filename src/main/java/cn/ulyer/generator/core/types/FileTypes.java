package cn.ulyer.generator.core.types;

import lombok.Getter;

/**
 * @author ulyer
 * @date 2021、12、16
 * 默认支持处理的文件类型
 */
@Getter
public enum FileTypes {

    java("text/x-java"),
    xml("xml"),
    vue("vue"),
    js("javascript"),
    txt("textile"),
    css("css"),
    yml("yaml"),
    yaml("yaml"),
    html("htmlmixed");

     FileTypes(String codeMode){
        this.mode = codeMode;
    }

    private String mode;

}
