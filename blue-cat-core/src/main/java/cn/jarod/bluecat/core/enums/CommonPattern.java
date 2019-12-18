package cn.jarod.bluecat.core.enums;

import lombok.Getter;

/**
 * @author jarod.jin 2019/9/4
 */

/**
 * 一般正则枚举
 */
public enum CommonPattern {

    /**全英文*/
    ONLY_LETTER("[a-zA-Z]+","全为英文"),

    /**全为数字*/
    ONLY_DIGIT("[0-9]+","全为数字"),

    /**只含有英文数字*/
    LETTER_DIGIT("[a-zA-Z0-9]+","只含有英文数字"),

    /**中文字*/
    CHINESE_LETTER("[\\u4e00-\\u9fa5]+","中文字"),

    /**邮箱*/
    EMAIL("^[A-Za-z0-9\\u4e00-\\u9fa5]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$","邮箱"),

    /**国内手机*/
    CN_PHONE_NUMBER("^(1[3-9])\\d{9}$","国内手机");



    @Getter
    String pattern;

    @Getter
    String memo;

    CommonPattern(String pattern,String memo){
        this.pattern = pattern;
        this.memo = memo;
    }

}
