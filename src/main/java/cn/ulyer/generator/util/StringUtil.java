package cn.ulyer.generator.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.expression.MapAccessor;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.Arrays;
import java.util.Map;

/**
 * @author ulyer
 * @date 2021.12.13
 * 字符串
 */
public class StringUtil {

    public static boolean isBlank(String value){
        return value==null||value.length()==0||value.replaceAll("\\s","").equals("");
    }


    /**
     * use hutool
     * @param name
     * @return
     */
    public static String toCamelCase(CharSequence name) {
        if (null == name) {
            return null;
        } else {
            String name2 = name.toString();
            if (contains(name2, '_')) {
                int length = name2.length();
                StringBuilder sb = new StringBuilder(length);
                boolean upperCase = false;

                for(int i = 0; i < length; ++i) {
                    char c = name2.charAt(i);
                    if (c == '_') {
                        upperCase = true;
                    } else if (upperCase) {
                        sb.append(Character.toUpperCase(c));
                        upperCase = false;
                    } else {
                        sb.append(Character.toLowerCase(c));
                    }
                }

                return sb.toString();
            } else {
                return name2;
            }
        }
    }

    public static boolean contains(String str,char c){
        return str != null && str.indexOf(c, 0) != -1;
    }

    private final static ObjectMapper objectMapper = new ObjectMapper();

    public static String toJson(Object obj,boolean pretty){
        try {
            if(pretty){
                return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
            }
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static  <T> T fromJson(String json,Class<T> clazz){
        try {
           return objectMapper.readValue(json,clazz);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e);
        }
    }
    /**
     * 解析el表达式
     * @param expr
     * @param data
     * @return
     */
    public static String processElExpression(String expr, Map<String,Object> data){
        ExpressionParser parser = new SpelExpressionParser();
        TemplateParserContext parserContext = new TemplateParserContext("#{","}");
        Expression expression =  parser.parseExpression(expr, parserContext);
        StandardEvaluationContext context = new StandardEvaluationContext();
        MapAccessor propertyAccessor = new MapAccessor();
        context.setVariables(data);
        context.setPropertyAccessors(Arrays.asList(propertyAccessor));
        return expression.getValue(context, data,String.class);
    }








}
