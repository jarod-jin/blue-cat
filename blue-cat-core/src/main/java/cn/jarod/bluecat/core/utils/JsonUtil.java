package cn.jarod.bluecat.core.utils;

import cn.jarod.bluecat.core.common.Constant;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * 基于Jackson制作的json工具类
 * @author Jarod Jin E-mail:kira277@163.com
 * @version 创建时间：2020/1/16
 */
@Slf4j
public class JsonUtil {

    private static ObjectMapper objectMapper = new ObjectMapper();

    static {
        /*全部字段序列化*/
        /*对象的所有字段全部列入*/
        objectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        /*取消默认转换timestamps形式*/
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,Boolean.FALSE);
        /*所有的日期格式都统一为以下的样式，即yyyy-MM-dd HH:mm:ss*/
        objectMapper.setDateFormat(new SimpleDateFormat(Constant.Common.DEFAULT_DATE_TIME_FORMAT));
        /*忽略空Bean转json的错误*/
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,Boolean.FALSE);
        /*忽略 在json字符串中存在，但是在java对象中不存在对应属性的情况。防止错误*/
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,Boolean.FALSE);
    }

    /**
     *
     * @param obj
     * @return
     */
    public static <T> String toJson(T obj){
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (IOException e) {
            log.error("JacksonUtil.toJsonString {} : {}",e.getMessage(), e.getCause());
        }
        return null;
    }


    /**
     * 字符串转对象
     * @param json
     * @param clazz
     * @return
     */
    public static <T> T parsePojo(String json, Class<T> clazz){
        if(StringUtils.isEmpty(json) || clazz == null){
            return null;
        }
        try {
            return objectMapper.readValue(json, clazz);
        } catch (IOException e) {
            log.error("JacksonUtil.parseObject {} : {}",e.getMessage(), e.getCause());
        }
        return null;
    }

    /**
     * 字段符转List之类的集合
     * @param json
     * @param typeReference
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T parseObject(String json, TypeReference<T> typeReference){
        if(StringUtils.isEmpty(json) || typeReference == null){
            return null;
        }
        try {
            return (T)(typeReference.getType().equals(String.class)? json : objectMapper.readValue(json,typeReference));
        } catch (IOException e) {
            log.error("JacksonUtil.parseObject {} : {}",e.getMessage(), e.getCause());
        }
        return null;
    }

    /**
     * 差不多同上
     * @param str
     * @param collectionClass
     * @param elementClasses
     * @return
     */
    public static <T> T parseObject(String str, Class<?> collectionClass,Class<?>... elementClasses){
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(collectionClass,elementClasses);
        try {
            return objectMapper.readValue(str,javaType);
        } catch (IOException e) {
            log.error("JacksonUtil.parseObject {} : {}",e.getMessage(), e.getCause());
        }
        return null;
    }


}
