package cn.jarod.bluecat.core.utils;

import cn.jarod.bluecat.core.common.CommonPattern;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author jarod.jin 2019/9/4
 */
class EncryptUtilTest {

    @Test
    void stringEncodeSHA256() {
        String test = "123456";
        String sha256 = "8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92";
        assertEquals(sha256, EncryptUtil.stringEncodeSHA256(test));
    }

    @Test
    void stringEncodeMD5() {
        String test = "123456789";
        String md5 = "25f9e794323b453885f5181f1b624d0b";
        assertEquals(md5, EncryptUtil.stringEncodeMD5(test));
    }

    @Test
    void getRandomCode_only_digit() {
        String result = EncryptUtil.getRandomCode(6);
        assertTrue(result.matches(CommonPattern.ONLY_DIGIT.getPattern()));
    }

    @Test
    void getRandomCode_has_letter() {
        String result = EncryptUtil.getRandomCode(6,true);
        assertTrue(result.matches(CommonPattern.LETTER_DIGIT.getPattern()));
    }
}