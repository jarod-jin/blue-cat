package cn.jarod.bluecat.core.utils;

import cn.jarod.bluecat.core.enums.ReturnCode;
import cn.jarod.bluecat.core.exception.BaseException;
import cn.jarod.bluecat.core.model.ResultDTO;

/**
 * @author Jarod Jin E-mail:kira277@163.com
 * @version 创建时间：2020/1/8
 */
public class ApiResultUtil {

    /**
     * 查询成功
     * @param obj 返回数据
     * @return ResultDTO
     */
    public static ResultDTO getSuccess(Object obj){
        return new ResultDTO(ReturnCode.GET_SUCCESS.getCode(),ReturnCode.GET_SUCCESS.getMsg(),obj);
    }

    /**
     * 修改成功
     * @param obj 返回数据
     * @return ResultDTO
     */
    public static ResultDTO saveSuccess(Object obj){
        return new ResultDTO(ReturnCode.SAVE_SUCCESS.getCode(),ReturnCode.SAVE_SUCCESS.getMsg(),obj);
    }

    /**
     * 删除成功
     * @return ResultDTO
     */
    public static ResultDTO delSuccess(){
        return new ResultDTO(ReturnCode.DEL_SUCCESS.getCode(),ReturnCode.DEL_SUCCESS.getMsg(),"");
    }

    /**
     * 验证不通过
     * @param msg 错误信息
     * @return BaseException
     */
    public static BaseException fail4Unauthorized(String msg){
        return new BaseException(ReturnCode.UNAUTHORIZED.getCode(),msg);
    }


}
