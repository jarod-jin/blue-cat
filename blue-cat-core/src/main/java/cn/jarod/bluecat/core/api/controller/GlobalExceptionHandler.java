package cn.jarod.bluecat.core.api.controller;

import cn.jarod.bluecat.core.api.pojo.ResponseDTO;
import cn.jarod.bluecat.core.common.enums.Constant;
import cn.jarod.bluecat.core.api.enums.ReturnCode;
import cn.jarod.bluecat.core.api.exception.BaseException;
import cn.jarod.bluecat.core.common.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @author jarod.jin 2019/9/3
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private final String logMsgPrefix = "调用地址路径为： "+ Constant.Symbol.BRACE +", 异常信息为："+ Constant.Symbol.BRACE +  " 位置为："+ Constant.Symbol.BRACE;

    @ResponseBody
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseDTO validErrorHandler(HttpServletRequest req, MethodArgumentNotValidException e) {
        log.warn("数据校验不通过: " + logMsgPrefix, req.getRequestURI(), e.getMessage(), JsonUtil.toJson(traceExceptionLastLocation(e)));
        return new ResponseDTO(ReturnCode.UNPROCESSABLE_ENTITY.getCode(), ReturnCode.UNPROCESSABLE_ENTITY.getMsg(), e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(value = BaseException.class)
    public ResponseDTO defaultErrorHandler(HttpServletRequest req, BaseException e) {
        log.warn("参数异常："+ logMsgPrefix, req.getRequestURI(), e.getMsg(), JsonUtil.toJson(traceExceptionLastLocation(e)));
        return new ResponseDTO(ReturnCode.UNPROCESSABLE_ENTITY.getCode(), e.getMsg(), traceExceptionLastLocation(e));
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseDTO errorHandler(HttpServletRequest req, Exception e) {
        log.error("接口异常："+ logMsgPrefix , req.getRequestURI(), e.getMessage(), JsonUtil.toJson(traceExceptionLastLocation(e)));
        return new ResponseDTO(ReturnCode.SERVER_ERROR.getCode(), ReturnCode.SERVER_ERROR.getMsg(), e.getMessage());
    }

    /**
     * 追踪最后报错位置
     * @param e 异常
     * @return
     */
    private StackTraceElement traceExceptionLastLocation(Exception e){
        return Arrays.stream(e.getStackTrace())
                .filter(element->!element.getClassName().contains(Constant.Common.API_RESULT_UTIL))
                .findFirst()
                .orElseGet(null);
    }

}
