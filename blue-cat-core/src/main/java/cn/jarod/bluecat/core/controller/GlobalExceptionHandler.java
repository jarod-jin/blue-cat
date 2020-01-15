package cn.jarod.bluecat.core.controller;

import cn.jarod.bluecat.core.common.Constant;
import cn.jarod.bluecat.core.common.ReturnCode;
import cn.jarod.bluecat.core.exception.BaseException;
import cn.jarod.bluecat.core.model.MessageDTO;
import cn.jarod.bluecat.core.model.ResultDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @author jarod.jin 2019/9/3
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private final String logMsgPrefix = "调用地址路径为： "+ Constant.Symbol.BRACE +", 异常信息为："+ Constant.Symbol.BRACE;

    @ResponseBody
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResultDTO validErrorHandler(HttpServletRequest req, MethodArgumentNotValidException e) {
        log.warn("数据校验不通过: " + logMsgPrefix,req.getRequestURI(), e.getMessage());
        return new ResultDTO(ReturnCode.UNPROCESSABLE_ENTITY.getCode(), ReturnCode.UNPROCESSABLE_ENTITY.getMsg(), e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(value = BaseException.class)
    public BaseException defaultErrorHandler(HttpServletRequest req, BaseException e) {
        log.warn("参数异常："+ logMsgPrefix, req.getRequestURI(), e.getMsg());
        return e;
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResultDTO errorHandler(HttpServletRequest req, Exception ex) {
        log.error("接口异常："+ logMsgPrefix, req.getRequestURI(), ex.getMessage());
        return new ResultDTO(ReturnCode.SERVER_ERROR.getCode(),ReturnCode.SERVER_ERROR.getMsg(), ex.getMessage());
    }
}
