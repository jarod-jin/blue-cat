package cn.jarod.bluecat.core.interceptor;

import cn.jarod.bluecat.core.annotation.AccessLimit;
import cn.jarod.bluecat.core.service.SecurityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * 接口幂等性拦截器
 * @author Jarod Jin E-mail:kira277@163.com
 * @version 创建时间：2020/1/15
 */
@Slf4j
public class AccessLimitInterceptor implements HandlerInterceptor {

    private final SecurityService securityService;

    public AccessLimitInterceptor(SecurityService securityService){
        this.securityService = securityService;
    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        AccessLimit methodAnnotation = method.getAnnotation(AccessLimit.class);
        if (methodAnnotation!= null) {
            securityService.validAccessLimit(methodAnnotation, request);
        }
        return true;
    }


    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    }

}
