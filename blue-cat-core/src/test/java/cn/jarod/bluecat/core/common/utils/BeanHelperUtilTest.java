package cn.jarod.bluecat.core.common.utils;

import cn.jarod.bluecat.core.api.pojo.PageRequestDTO;
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
        PageRequestDTO queryDO = new PageRequestDTO();
        queryDO.setPageNum(1);
        String[] nullStr = BeanHelperUtil.getEmptyValueProperties(queryDO);
        assertFalse(Arrays.asList(nullStr).contains("pageNum"));
    }
}