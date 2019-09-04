package cn.jarod.bluecat.core.controller;

import cn.jarod.bluecat.core.enums.ReturnCode;
import cn.jarod.bluecat.core.exception.BaseException;
import cn.jarod.bluecat.core.model.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @auther jarod.jin 2019/9/3
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public ResultVO validErrorHandler(MethodArgumentNotValidException e) {
        log.warn("数据校验不通过 validErrorHandler {}", e.getMessage());
        return new ResultVO(ReturnCode.S400.getCode(), ReturnCode.S400.getMsg(), e.getMessage());
    }

    @ExceptionHandler(value = BaseException.class)
    @ResponseBody
    public ResultVO defaultErrorHandler(HttpServletRequest req, BaseException e) {
        log.warn("defaultErrorHandler {}, {}", req.getRequestURI(), e.getErrorMessage());
        return new ResultVO(e.getErrorCode(), e.getErrorMessage(), "");
    }


    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResultVO errorHandler(Exception ex) {
        log.error("异常信息为 {}",  ex);
        return new ResultVO(ReturnCode.R500.getCode(),ReturnCode.R500.getMsg(),ex.getMessage());
    }
}
