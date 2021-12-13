package cn.ulyer.generator.util;

import java.util.Iterator;

/**
 * @author ulyer
 * @date 2021.12.13
 * 数组工具
 */
public class ArrayUtil {

    public static String iteratorToString(Iterator<?> iterator,String join){
        StringBuilder builder = new StringBuilder();
        while (iterator.hasNext()){
            Object next = iterator.next();
            builder.append(next).append(join);
        }
        return builder.substring(0,builder.length()-1);
    }

}
