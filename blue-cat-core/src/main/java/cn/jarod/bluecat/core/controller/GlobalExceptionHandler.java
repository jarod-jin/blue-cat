package cn.jarod.bluecat.core.controller;

import cn.jarod.bluecat.core.enums.ReturnCode;
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
 * @auther jarod.jin 2019/9/3
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public ResultDTO validErrorHandler(HttpServletRequest req, MethodArgumentNotValidException e) {
        log.warn("数据校验不通过: {},{}",req.getRequestURI(), e.getMessage());
        return new ResultDTO(ReturnCode.UNPROCESSABLE_ENTITY.getCode(), ReturnCode.UNPROCESSABLE_ENTITY.getMsg(),new MessageDTO( e.getMessage()));
    }

    @ExceptionHandler(value = BaseException.class)
    @ResponseBody
    public ResultDTO defaultErrorHandler(HttpServletRequest req, BaseException e) {
        log.warn("接口调用不正确： {}, {}", req.getRequestURI(), e.getMessage());
        return new ResultDTO(e.getCode(), e.getMsg(), new MessageDTO( e.getMessage()));
    }


    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResultDTO errorHandler(HttpServletRequest req, Exception ex) {
        log.error("接口异常：{}，{}", req.getRequestURI(), ex.getMessage());
        return new ResultDTO(ReturnCode.SERVER_ERROR.getCode(),ReturnCode.SERVER_ERROR.getMsg(),new MessageDTO(ex.getMessage()));
    }
}
