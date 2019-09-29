package cn.jarod.bluecat.core.controller;

import cn.jarod.bluecat.core.enums.ReturnCode;
import cn.jarod.bluecat.core.exception.BaseException;
import cn.jarod.bluecat.core.model.MessageDTO;
import cn.jarod.bluecat.core.model.ResultBO;
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
    public ResultBO validErrorHandler(HttpServletRequest req, MethodArgumentNotValidException e) {
        log.warn("数据校验不通过: {},{}",req.getRequestURI(), e.getMessage());
        return new ResultBO(ReturnCode.S400.getCode(), ReturnCode.S400.getMsg(),new MessageDTO( e.getMessage()));
    }

    @ExceptionHandler(value = BaseException.class)
    @ResponseBody
    public ResultBO defaultErrorHandler(HttpServletRequest req, BaseException e) {
        log.warn("接口调用不正确： {}, {}", req.getRequestURI(), e.getErrorMessage());
        return new ResultBO(e.getErrorCode(), e.getErrorMessage(), new MessageDTO( e.getMessage()));
    }


    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResultBO errorHandler(HttpServletRequest req, Exception ex) {
        log.error("接口异常：{}，{}", req.getRequestURI(), ex.getMessage());
        return new ResultBO(ReturnCode.R500.getCode(),ReturnCode.R500.getMsg(),new MessageDTO(ex.getMessage()));
    }
}
