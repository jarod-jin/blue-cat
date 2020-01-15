package cn.jarod.bluecat.core.exception;

import cn.jarod.bluecat.core.common.ReturnCode;

/**
 * @author Jarod Jin E-mail:kira277@163.com
 * @version 创建时间：2020/1/9
 */
public class NoDataFoundException extends BaseException{

    public NoDataFoundException() {
        super(ReturnCode.NOT_FOUND);
    }
}
