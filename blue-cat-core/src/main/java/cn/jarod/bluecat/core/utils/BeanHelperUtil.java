package cn.jarod.bluecat.core.utils;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.BeansException;

import java.beans.PropertyDescriptor;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @auther jarod.jin 2019/9/4
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
        String a[]=para.split("_");
        for(String s:a){
            if (!para.contains("_")) {
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
        Iterator entries = map.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry entry = (Map.Entry) entries.next();
            String key = UnderlineToHump(String.valueOf(entry.getKey()).toLowerCase());
            newMap.put(key,entry.getValue());
        }
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
        int temp=0;//定位
        if (!para.contains("_")) {
            for(int i=0;i<para.length();i++){
                if(Character.isUpperCase(para.charAt(i))){
                    sb.insert(i+temp, "_");
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
     * 将目标源中不为空的字段过滤，将数据库中查出的数据源复制到提交的目标源中
     *
     * @param source 用id从数据库中查出来的数据源
     * @param target 提交的实体，目标源
     */
    public static void copyNullProperties(Object source, Object target) {
        BeanUtils.copyProperties(source, target, getNoNullProperties(target));
    }


    /**
     * @param target 目标源数据
     * @return 将目标源中不为空的字段取出
     */
    private static String[] getNoNullProperties(Object target) {
        BeanWrapper srcBean = new BeanWrapperImpl(target);
        PropertyDescriptor[] pds = srcBean.getPropertyDescriptors();
        Set<String> noEmptyName = Sets.newHashSet();
        for (PropertyDescriptor p : pds) {
            Object value = srcBean.getPropertyValue(p.getName());
            if (value != null) noEmptyName.add(p.getName());
        }
        String[] result = new String[noEmptyName.size()];
        return noEmptyName.toArray(result);
    }







}
