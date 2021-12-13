package cn.ulyer.generator;

import cn.ulyer.generator.util.ArrayUtil;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

public class ArrayUtilTest {

    @Test
    public void testJoin(){
        List<String> array = new LinkedList<>();
        array.add("11");
        array.add("22");
        array.add("33");
        array.add("44");
        System.out.println(ArrayUtil.iteratorToString(array.iterator(),","));
    }

}
