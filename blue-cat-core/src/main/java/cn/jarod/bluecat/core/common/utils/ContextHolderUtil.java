package cn.jarod.bluecat.core.common.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author Jarod Jin E-mail:kira277@163.com
 * @version 创建时间：2020/1/16
 */
public class ContextHolderUtil {

    public static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

    }

    public static HttpSession getSession() {
        return getRequest().getSession();
    }
}
