package cn.jarod.bluecat.core.common.utils;

import cn.jarod.bluecat.core.common.pojo.DateBean4Test;
import com.google.common.collect.ImmutableMap;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Month;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Jarod Jin E-mail:kira277@163.com
 * @version 创建时间：2020/5/9
 */
class MapBeanUtilTest {

    @Test
    void map2Bean() {
    }

    @Test
    void testMap2Bean() {
        Map<String, Object> map = ImmutableMap.<String, Object>builder()
                .put("date","2019-05-20 23:34:50")
                .put("localDate","2019-03-20")
                .put("localDateTime","2019-03-20 12:20:40")
                .put("decimal","23")
                .build();
        DateBean4Test testBean = MapBeanUtil.map2Bean(map,DateBean4Test.class);
        assertAll("MapToBean Test Assert!",()->{
            assertEquals(new BigDecimal(23),testBean.getDecimal());
            assertNotNull(testBean.getDate());
            assertEquals(Month.MARCH,testBean.getLocalDate().getMonth());
            assertEquals(20, testBean.getLocalDateTime().getDayOfMonth());
        });

    }

}