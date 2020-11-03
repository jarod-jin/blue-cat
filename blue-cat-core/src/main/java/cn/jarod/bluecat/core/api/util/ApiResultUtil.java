package cn.jarod.bluecat.core.api.util;

import cn.jarod.bluecat.core.api.enums.ReturnCode;
import cn.jarod.bluecat.core.api.exception.BadParameterException;
import cn.jarod.bluecat.core.api.exception.BaseException;
import cn.jarod.bluecat.core.api.exception.NoDataFoundException;

/**
 * @author Jarod Jin E-mail:kira277@163.com
 * @version 创建时间：2020/1/8
 */
public class ApiResultUtil {

    /**
     * 验证不通过
     * @param msg 错误信息
     * @return BaseException
     */
    public static BaseException fail4Unauthorized(String msg){
        return new BaseException(ReturnCode.UNAUTHORIZED.getCode(),msg);
    }

    /**
     * 未找到数据
     * @return BaseException
     */
    public static BaseException fail4NotFound(){
        return new NoDataFoundException();
    }

    /**
     * 数据重复
     * @return new BaseException(ReturnCode.UNAUTHORIZED.
     */
    public static BaseException fail4Existed(){
        return new BaseException(ReturnCode.ENTITY_EXISTED);
    }

    /**
     * 参数传入错误
     * @param returnEnum 错误类型
     * @return BaseException
     */
    public static BaseException fail4BadParameter(ReturnCode returnEnum){
        return new BadParameterException(returnEnum);
    }

    /**
     * 参数传入错误
     * @param returnEnum 错误类型
     * @param msg 错误信息
     * @return BaseException
     */
    public static BaseException fail4BadParameter(ReturnCode returnEnum,String msg){
        return new BadParameterException(returnEnum, msg);
    }



}
