package cn.jarod.bluecat.auth.enums;

import cn.jarod.bluecat.core.enums.ReturnCode;
import cn.jarod.bluecat.core.exception.BaseException;

import java.util.Arrays;

/**
 * @author jarod.jin 2019/9/9
 */
public enum SignType {
    /**电话*/
    tel(0,"tel"),

    /**email*/
    email(1,"email");

    private int number;

    private String code;

    SignType(int number, String code){
        this.code = code;
        this.number = number;
    }

    public int getNumber(){
        return number;
    }

    public String getCode(){
        return code;
    }

    public static Integer findNumber(String code){
        return Arrays.stream(SignType.values())
                .filter(e->code.equals(e.code))
                .findFirst()
                .orElseThrow(()->new BaseException(ReturnCode.NOT_ACCEPTABLE))
                .number;
    }

}
