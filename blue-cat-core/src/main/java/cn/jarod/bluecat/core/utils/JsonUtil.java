package cn.jarod.bluecat.core.utils;

import cn.jarod.bluecat.core.common.Constant;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

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
        // 通过该方法对mapper对象进行设置，所有序列化的对象都将按改规则进行系列化
        // Include.Include.ALWAYS 默认
        // Include.NON_DEFAULT 属性为默认值不序列化
        // Include.NON_EMPTY 属性为 空（""） 或者为 NULL 都不序列化，则返回的json是没有这个字段的。这样对移动端会更省流量
        // Include.NON_NULL 属性为NULL 不序列化,就是为null的字段不参加序列化
        // objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        // 字段保留，将null值转为""
        objectMapper.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() {
            @Override
            public void serialize(Object o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
                    throws IOException, JsonProcessingException {
                jsonGenerator.writeString("");
            }
        });
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(Constant.Common.DEFAULT_DATE_TIME_FORMAT)));
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern(Constant.Common.DEFAULT_DATE_FORMAT)));
        javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern(Constant.Common.DEFAULT_TIME_FORMAT)));
        objectMapper.registerModule(javaTimeModule);
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
