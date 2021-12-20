package cn.ulyer.generator;

import cn.ulyer.generator.model.GenTable;
import cn.ulyer.generator.util.StringUtil;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class SpringElExpressionTest {

    @Test
    public void elTest(){
            GenTable genTable = new GenTable();
            genTable.setTableName("aaaa");
            Map<String, Object> map = new HashMap<>();
            map.put("table",genTable);
            String result = StringUtil.processElExpression("aaa#{table.tableName}", map);
            System.out.println("result:" + result);
    }

}
