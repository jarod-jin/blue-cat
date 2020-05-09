package cn.jarod.bluecat.core.base.exception;

import cn.jarod.bluecat.core.common.ReturnCode;

/**
 * @author Jarod Jin E-mail:kira277@163.com
 * @version 创建时间：2020/1/9
 */
public class BadParameterException extends BaseException{

    private static final long serialVersionUID = - 1138478368418970818L;

    public BadParameterException(ReturnCode returnEnum, String errorMessage) {
        super(returnEnum.getCode(), errorMessage);
    }

    public BadParameterException(ReturnCode returnEnum) {
        super(returnEnum);
    }
}
