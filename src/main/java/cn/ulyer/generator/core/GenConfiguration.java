package cn.ulyer.generator.core;

import cn.ulyer.generator.core.datasource.DataSourceHelper;
import cn.ulyer.generator.core.datasource.MysqlHelper;
import cn.ulyer.generator.core.datasource.OracleHelper;
import cn.ulyer.generator.core.property.DbProperty;
import cn.ulyer.generator.core.property.MysqlProperty;
import cn.ulyer.generator.core.property.OracleProperty;
import cn.ulyer.generator.core.types.DataBaseTypes;
import cn.ulyer.generator.core.gen.DefaultTemplateJavaGenerator;
import cn.ulyer.generator.core.gen.DefaultTemplateUIGenerator;
import cn.ulyer.generator.core.gen.JavaGenerator;
import cn.ulyer.generator.core.gen.ViewUIGenerator;
import org.springframework.data.mongodb.core.MongoTemplate;

import javax.annotation.Resource;
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

    private final static Map<DataBaseTypes,DbProperty> DB_PROPERTY_TYPE = new HashMap<>();

    private final static Map<String,Class<?>> typeConvertMap = new ConcurrentHashMap<>(128);

    private JavaGenerator javaGenerator = new DefaultTemplateJavaGenerator();

    private ViewUIGenerator viewUIGenerator = new DefaultTemplateUIGenerator();

    @Resource
    private MongoTemplate mongoTemplate;

    static {
        initDataSourceHelper();
        registerConvert();
        registerDbProperty();
    }

    private static void registerDbProperty() {
        DB_PROPERTY_TYPE.put(DataBaseTypes.MYSQL,new MysqlProperty());
        DB_PROPERTY_TYPE.put(DataBaseTypes.ORACLE,new OracleProperty());
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

    public static DbProperty getDbProperty(DataBaseTypes types){
        return DB_PROPERTY_TYPE.get(types);
    }

    public void registerConvert(String name,Class<?> clazz){
        typeConvertMap.put(name,clazz);
    }

    public Class<?> convert(String name){
        return typeConvertMap.get(name.toUpperCase(Locale.ROOT));
    }

    public JavaGenerator getJavaGenerator() {
        return javaGenerator;
    }

    public void setJavaGenerator(JavaGenerator javaGenerator) {
        this.javaGenerator = javaGenerator;
    }

    public ViewUIGenerator getViewUIGenerator() {
        return viewUIGenerator;
    }

    public void setViewUIGenerator(ViewUIGenerator viewUIGenerator) {
        this.viewUIGenerator = viewUIGenerator;
    }
}
