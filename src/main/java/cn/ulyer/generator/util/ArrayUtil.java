package cn.ulyer.generator.util;

import java.util.*;

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

    public static <T> List<T> acceptNonNull(T ...elements){
        if(elements==null){
            return new ArrayList<>();
        }
        return Arrays.asList(elements);
    }

    public static <T> List<T> acceptNonNull(List<T> list){
        if(list==null){
            return Collections.EMPTY_LIST;
        }
        return list;
    }

}
