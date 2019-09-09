package cn.jarod.bluecat.core.enums;

import lombok.Getter;

/**
 * @auther jarod.jin 2019/9/4
 */
public enum CommonPattern {

    ONLY_LETTER("[a-zA-Z]+","全为英文"),

    ONLY_DIGIT("[0-9]+","全为数字"),

    LETTER_DIGIT("[a-zA-Z0-9]+","只含有英文数字"),

    CHINESE_LETTER("[\\u4e00-\\u9fa5]+","中文字"),

    EMAIL("^[A-Za-z0-9\\u4e00-\\u9fa5]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$","邮箱"),

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
