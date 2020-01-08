package cn.jarod.bluecat.core.utils;

import cn.jarod.bluecat.core.model.BaseQuery;
import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Jarod Jin E-mail:kira277@163.com
 * @version 创建时间：2020/1/7
 */
class BeanHelperUtilTest {

    @Test
    void getProperties() {
    }

    @Test
    void getPropertiesEmptyValue() {
        BaseQuery baseQuery = new BaseQuery();
        baseQuery.setPageNum(1);
        String[] nullStr = BeanHelperUtil.getEmptyValueProperties(baseQuery);
        assertFalse(Arrays.asList(nullStr).contains("pageNum"));
    }
}