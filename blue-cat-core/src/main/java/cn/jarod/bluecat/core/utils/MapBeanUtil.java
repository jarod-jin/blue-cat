package cn.jarod.bluecat.core.utils;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * 由于使用的apache的beanutils所以默认情况下会将
 * Ineger、Boolean、Long等基本类型包装类为null时的值复制后转换成0或者false
 * @auther jarod.jin 2019/9/4
 */
@Slf4j
public class MapBeanUtil {

    /**
     *
     * Map转换层Bean，使用泛型免去了类型转换的麻烦。
     * @param <T>
     * @param map
     * @param clazz
     * @return
     */
    public static <T> T map2Bean(Map<String, Object> map, Class<T> clazz) {
        T bean = null;
        try {
            bean = clazz.newInstance();
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
    public static Map bean2Map(Object obj) {
        Map map = Maps.newHashMap();
        try {
            map = BeanUtils.describe(obj);
        } catch (NoSuchMethodException | IllegalAccessException| InvocationTargetException e) {
            log.error(e.getMessage());
        }
        return map;
    }

}
