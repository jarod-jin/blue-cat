package cn.jarod.bluecat.core.exception;

import cn.jarod.bluecat.core.common.ReturnCode;

import java.util.Arrays;

/**
 * @author Jarod Jin E-mail:kira277@163.com
 * @version 创建时间：2020/1/9
 */
public class BadParameterException extends BaseException{

    public BadParameterException(ReturnCode returnEnum, String errorMessage) {
        super(returnEnum.getCode(), errorMessage);
    }

    public BadParameterException(ReturnCode returnEnum) {
        super(returnEnum);
    }
}
