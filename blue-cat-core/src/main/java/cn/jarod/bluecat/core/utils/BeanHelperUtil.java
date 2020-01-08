package cn.jarod.bluecat.core.utils;

import cn.jarod.bluecat.core.constant.Symbol;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.BeansException;
import org.springframework.util.ObjectUtils;

import java.beans.PropertyDescriptor;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author jarod.jin 2019/9/4
 */
@Slf4j
public class BeanHelperUtil {



    /***
     * 下划线命名转为驼峰命名
     * @param para
     * 下划线命名的字符串
     */
    public static String UnderlineToHump(String para){
        StringBuilder result=new StringBuilder();
        String[] a = para.split(Symbol.UNDERLINE);
        for(String s:a){
            if (!para.contains(Symbol.UNDERLINE)) {
                result.append(s);
                continue;
            }
            if(result.length()==0){
                result.append(s);
            }else{
                result.append(s.substring(0, 1).toUpperCase());
                result.append(s.substring(1).toLowerCase());
            }
        }
        return result.toString();
    }




    /**
     * 下划线的Map转换成Bean实体
     * @param map
     * @param clazz
     * @param <T>
     * @param <K>
     * @param <V>
     * @return
     */
    public static  <T,K,V> T getBeanFromUnderlineMap (Map<K,V> map, Class<T> clazz){
        Map<String, Object> newMap = Maps.newHashMap();
        map.forEach((k,v)->{
            String key = UnderlineToHump(String.valueOf(k).toLowerCase());
            newMap.put(key,v);
        });
        return MapBeanUtil.map2Bean(newMap, clazz);
    }




    /***
     * 驼峰命名转为下划线命名
     *
     * @param para
     * 驼峰命名的字符串
     */
    public static String HumpToUnderline(String para){
        StringBuilder sb=new StringBuilder(para);
        /*定位*/
        int temp=0;
        if (!para.contains(Symbol.UNDERLINE)) {
            for(int i=0;i<para.length();i++){
                if(Character.isUpperCase(para.charAt(i))){
                    sb.insert(i+temp, Symbol.UNDERLINE);
                    temp+=1;
                }
            }
        }
        return sb.toString().toUpperCase();
    }

    /**
     * 根据传入类型拷贝实体属性
     * @param source
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T createCopyBean(Object source, Class<T> clazz) {
        T t = null;
        try {
            t = clazz.newInstance();
            BeanUtils.copyProperties(source,t);
        } catch (InstantiationException |IllegalAccessException | BeansException e) {
            log.error(e.getMessage());
        }
        return t;
    }

    /**
     * 将数据源中为空的字段过滤，将数据源中不为空的字段复制到目标源中
     * @param source 数据源
     * @param target 提交的实体，目标源
     */
    public static void copyNotNullProperties(Object source, Object target) {
        BeanUtils.copyProperties(source, target, getNullValueProperties(source));
    }

    /**
     * 判定对象中为null的字段取出
     * @param object 判定对象
     * @return String[]
     */
    private static String[] getNullValueProperties(Object object) {
        BeanWrapper srcBean = new BeanWrapperImpl(object);
        PropertyDescriptor[] pds = srcBean.getPropertyDescriptors();
        return Arrays.stream(pds).filter(d ->
                srcBean.getPropertyValue(d.getName())==null
        ).map(PropertyDescriptor::getName).distinct().toArray(String[]::new);
    }

    /**
     * 将目标源中不为空的字段过滤，从数据源中复制目标源为空的字段值
     * @param source 用id从数据库中查出来的数据源
     * @param target 提交的实体，目标源
     */
    public static void copyNullProperties(Object source, Object target) {
        BeanUtils.copyProperties(source, target, getNotNullValueProperties(target));
    }


    /**
     * 将判定对象中不为null的字段取出
     * @param object 判定对象
     * @return String[]
     */
    private static String[] getNotNullValueProperties(Object object) {
        BeanWrapper srcBean = new BeanWrapperImpl(object);
        PropertyDescriptor[] pds = srcBean.getPropertyDescriptors();
        return Arrays.stream(pds).filter(d ->
                srcBean.getPropertyValue(d.getName())!=null
        ).map(PropertyDescriptor::getName).distinct().toArray(String[]::new);
    }

    /**
     * 将判定对象中为空的字段取出
     * @param object 判定对象
     * @return String[]
     */
    public static String[] getEmptyValueProperties(Object object) {
        BeanWrapper srcBean = new BeanWrapperImpl(object);
        PropertyDescriptor[] pds = srcBean.getPropertyDescriptors();
        return Arrays.stream(pds).filter(d ->
                ObjectUtils.isEmpty(srcBean.getPropertyValue(d.getName()))
        ).map(PropertyDescriptor::getName).distinct().toArray(String[]::new);
    }








}
