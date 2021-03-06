package cn.jarod.bluecat.core.common.utils;

import cn.jarod.bluecat.core.common.enums.Constant;
import cn.jarod.bluecat.core.converter.LocalDateConverter;
import cn.jarod.bluecat.core.converter.LocalDateTimeConverter;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.BigDecimalConverter;
import org.apache.commons.beanutils.converters.BigIntegerConverter;
import org.apache.commons.beanutils.converters.DateConverter;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

/**
 * 由于使用的apache的beanutils所以默认情况下会将
 * Ineger、Boolean、Long等基本类型包装类为null时的值复制后转换成0或者false
 * @author jarod.jin 2019/9/4
 */
@Slf4j
public class MapBeanUtil {

    static{
        //注册时间格式
        DateConverter dateConverter = new DateConverter();
        dateConverter.setPatterns(new String[]{ Constant.Common.DEFAULT_DATE_FORMAT,
                Constant.Common.DEFAULT_DATE_TIME_FORMAT});
        ConvertUtils.register(dateConverter, Date.class);
        ConvertUtils.register(new LocalDateConverter(), LocalDate.class);
        ConvertUtils.register(new LocalDateTimeConverter(), LocalDateTime.class);
        //注册数字格式
        ConvertUtils.register(new BigIntegerConverter(null), BigInteger.class);
        ConvertUtils.register(new BigDecimalConverter(null), BigDecimal.class);
    }


    /**
     *
     * Map转换层Bean，使用泛型免去了类型转换的麻烦, 同时使用指定的时间日期使用类
     * @param <T,D>
     * @param map
     * @param tClass
     * @return
     */
    public static <T> T map2Bean(Map<String, Object> map, Class<T> tClass) {
        T bean = null;
        try {
            bean = tClass.newInstance();
            BeanUtils.populate(bean, map);
        } catch (InstantiationException | IllegalAccessException| InvocationTargetException e) {
            log.error(e.getMessage());
        }
        return bean;
    }



    /**
     * Bean 转换成 Map。
     * @param obj
     * @return
     */
    public static Map<String,String> bean2Map(Object obj) {
        Map<String,String> map = Maps.newHashMap();
        try {
            map = BeanUtils.describe(obj);
        } catch (NoSuchMethodException | IllegalAccessException| InvocationTargetException e) {
            log.error(e.getMessage());
        }
        return map;
    }

}
