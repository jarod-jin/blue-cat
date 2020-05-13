package cn.jarod.bluecat.core.utils;

import cn.jarod.bluecat.core.log.model.OperationLogDTO;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @author Jarod Jin E-mail:kira277@163.com
 * @version 创建时间：2020/2/28
 */
public class LoggerUtil {

    public static final String LOG_OPERATE = "operation";

    public LoggerUtil() {
    }

    public static OperationLogDTO getLog(HttpServletRequest request, HandlerMethod handler) {

        String description = null;
        Method method = handler.getMethod();
        Annotation[] declaredAnnotations = method.getDeclaredAnnotations();
        for (Annotation annotation : declaredAnnotations) {
            Class<? extends Annotation> clazz = annotation.annotationType();
            if ("io.swagger.annotations.ApiOperation".equals(clazz.getName())) {
                String annotationString = annotation.toString();
                String responseString = annotationString.substring(annotationString.indexOf("response=class"),annotationString.length());
                description = responseString.substring(responseString.indexOf("value=")+6,responseString.length()-1);
                break;
            }
        }
        OperationLogDTO log = new OperationLogDTO();
        log.setIp(HttpUtil.getIpAddress(request));
        log.setOperator("operator");
        log.setOperatorName(request.getHeader("userId"));
        log.setOperationType(handler.getMethod().getName());
        log.setDescription(description);
        log.getParams().putAll(request.getParameterMap());
        return log;
    }
}
