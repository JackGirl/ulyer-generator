package cn.ulyer.generator.util;

public class StringUtil {

    public static boolean isBlank(String value){
        return value==null||value.length()==0||value.replaceAll("\\s","").equals("");
    }

}
