package cn.jarod.bluecat.core.service;

import cn.jarod.bluecat.core.common.annotation.AccessLimit;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Jarod Jin E-mail:kira277@163.com
 * @version 创建时间：2020/1/15
 */
public interface SecurityService {
    /**
     * 创建验证码
     * @return String
     */
    String createToken();

    /**
     * 校验token，不通过直接抛出异常
     * @param request 请求
     */
    void validToken(HttpServletRequest request);

    /**
     * 校验限制次数
     * @param annotation
     * @param request
     */
    void validAccessLimit(AccessLimit annotation, HttpServletRequest request);
}
