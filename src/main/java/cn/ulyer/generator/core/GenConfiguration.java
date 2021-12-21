package cn.ulyer.generator.core;

import cn.ulyer.generator.core.datasource.DataSourceHelper;
import cn.ulyer.generator.core.datasource.MysqlHelper;
import cn.ulyer.generator.core.datasource.OracleHelper;
import cn.ulyer.generator.core.gen.FreeMakerGenerator;
import cn.ulyer.generator.core.gen.TemplateGenerator;
import cn.ulyer.generator.core.types.DataBaseTypes;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ulyer
 * @date 2021.12.13
 * 生成全局配置
 */
public class GenConfiguration {

    private static Map<DataBaseTypes, DataSourceHelper> DATA_SOURCE_HELPER_MAP;

    private final static Map<String,Class<?>> typeConvertMap = new ConcurrentHashMap<>(128);


    private TemplateGenerator templateGenerator = new FreeMakerGenerator();

    static {
        initDataSourceHelper();
        registerConvert();
    }



    private static void registerConvert() {
        typeConvertMap.put("TINYINT",Integer.class );
        typeConvertMap.put("BIGINT",Long.class);
        typeConvertMap.put("INT",Integer.class);
        typeConvertMap.put("DECIMAL", BigDecimal.class);
        typeConvertMap.put("TEXT",String.class);
        typeConvertMap.put("FLOAT",Float.class);
        typeConvertMap.put("VARCHAR",String.class);
        typeConvertMap.put("VARCHAR2",String.class);
        typeConvertMap.put("NVARCHAR",String.class);
        typeConvertMap.put("NVARCHAR2",String.class);
        typeConvertMap.put("NUMBER",Long.class);
        typeConvertMap.put("LONGTEXT",String.class);
        typeConvertMap.put("LONG",String.class);
        typeConvertMap.put("CHAR",String.class);
        typeConvertMap.put("TIMESTAMP", Date.class);
        typeConvertMap.put("TIMESTAMP(6)", Date.class);
        typeConvertMap.put("DATE",Date.class);
        typeConvertMap.put("CLOB",String.class);
        typeConvertMap.put("DATETIME",Date.class);
    }


    private static void initDataSourceHelper() {
        Map<DataBaseTypes, DataSourceHelper> values = new HashMap<>();
        values.put(DataBaseTypes.MYSQL, new MysqlHelper());
        values.put(DataBaseTypes.ORACLE, new OracleHelper());
        DATA_SOURCE_HELPER_MAP = Collections.unmodifiableMap(values);
    }

    public static String getUITemplate(String name) {
        return "";
    }

    private static String getJavaTemplate() {
        return "";
    }


    public DataSourceHelper getDataSourceHelper(DataBaseTypes types){
        DataSourceHelper dataSourceHelper = DATA_SOURCE_HELPER_MAP.get(types);
        if(dataSourceHelper==null){
            throw new NullPointerException("retrieve datasourceHelper got null types:"+types.name());
        }
        return dataSourceHelper;
    }

    public void registerConvert(String name,Class<?> clazz){
        typeConvertMap.put(name,clazz);
    }

    public Class<?> convert(String name){
        return typeConvertMap.get(name.toUpperCase(Locale.ROOT));
    }

    public TemplateGenerator getTemplateGenerator() {
        return templateGenerator;
    }

    public void setJavaGenerator(TemplateGenerator templateGenerator) {
        this.templateGenerator = templateGenerator;
    }


}
