package cn.jarod.bluecat.core.api.exception;

import cn.jarod.bluecat.core.api.enums.ReturnCode;

/**
 * @author Jarod Jin E-mail:kira277@163.com
 * @version 创建时间：2020/1/9
 */
public class NoDataFoundException extends BaseException{

    private static final long serialVersionUID = - 4105339668832686970L;

    public NoDataFoundException() {
        super(ReturnCode.NOT_FOUND);
    }
}
