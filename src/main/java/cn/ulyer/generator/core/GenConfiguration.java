package cn.ulyer.generator.core;

import cn.ulyer.generator.core.datasource.DataSourceHelper;
import cn.ulyer.generator.core.datasource.MysqlHelper;
import cn.ulyer.generator.core.datasource.OracleHelper;
import cn.ulyer.generator.core.enums.DataBaseTypes;
import cn.ulyer.generator.core.enums.SupportDaoTypes;
import cn.ulyer.generator.core.gen.AbstractViewGenerator;
import cn.ulyer.generator.core.gen.GeneratorMvcHelper;
import cn.ulyer.generator.core.gen.MybatisPlusGeneratorMvc;
import cn.ulyer.generator.core.gen.Vue2ElementGenerator;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class GenConfiguration {

    private static Map<DataBaseTypes, DataSourceHelper> DATA_SOURCE_HELPER_MAP;

    private final static Map<String, AbstractViewGenerator> VIEW_GEN_MAP = new ConcurrentHashMap<>();

    private final static Map<String, GeneratorMvcHelper> JAVA_MVC_GEN_MAP = new ConcurrentHashMap<>();

    private final static Map<String,Class<?>> typeConvertMap = new ConcurrentHashMap<>(128);

    static {
        initDataSourceHelper();
        initViewGenHelper();
        initJavaMvcGenHelper();
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
        typeConvertMap.put("LONGTEXT",String.class);
        typeConvertMap.put("LONG",String.class);
        typeConvertMap.put("CHAR",String.class);
        typeConvertMap.put("TIMESTAMP", Date.class);
        typeConvertMap.put("DATE",Date.class);
        typeConvertMap.put("DATETIME",Date.class);
    }


    private static void initDataSourceHelper() {
        Map<DataBaseTypes, DataSourceHelper> values = new HashMap<>();
        values.put(DataBaseTypes.MYSQL, new MysqlHelper());
        values.put(DataBaseTypes.ORACLE, new OracleHelper());
        DATA_SOURCE_HELPER_MAP = Collections.unmodifiableMap(values);
    }

    private static void initViewGenHelper() {
        VIEW_GEN_MAP.put("vue2_element-ui",new Vue2ElementGenerator());
    }

    private static void initJavaMvcGenHelper() {
        JAVA_MVC_GEN_MAP.put(SupportDaoTypes.MYBATIS_PLUS.getComment(),new MybatisPlusGeneratorMvc());
    }

    public void registerMvcGenerator(String name, GeneratorMvcHelper generatorMvcHelper){
        JAVA_MVC_GEN_MAP.put(name, generatorMvcHelper);
    }

    public void registerViewGenerator(String name, AbstractViewGenerator viewGenerator){
        VIEW_GEN_MAP.put(name,viewGenerator);
    }

    public AbstractViewGenerator getViewGenerator(String name){
        AbstractViewGenerator abstractViewGenerator = VIEW_GEN_MAP.get(name);
        if(abstractViewGenerator==null){
            throw new NullPointerException("cached not get view generator helper for key :"+name);
        }
        return abstractViewGenerator;
    }


    public GeneratorMvcHelper getJavaGeneratorHelper(String name){
        GeneratorMvcHelper generatorMvcHelper = JAVA_MVC_GEN_MAP.get(name);
        if(generatorMvcHelper ==null){
            throw new NullPointerException("cached not get java generator helper for key :"+name);
        }
        return generatorMvcHelper;
    }

    public DataSourceHelper getDataSourceHelper(DataBaseTypes types){
        DataSourceHelper dataSourceHelper = DATA_SOURCE_HELPER_MAP.get(types);
        if(dataSourceHelper==null){
            throw new NullPointerException("retrieve datasourceHelper got null types:"+types.name());
        }
        return dataSourceHelper;
    }

    public void registerNewConvert(String name,Class<?> clazz){
        typeConvertMap.put(name,clazz);
    }

    public Class<?> convert(String name){
        return typeConvertMap.get(name.toUpperCase(Locale.ROOT));
    }



}
