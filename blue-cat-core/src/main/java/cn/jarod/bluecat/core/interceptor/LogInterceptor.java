package cn.jarod.bluecat.core.interceptor;

import cn.jarod.bluecat.core.model.OperationLogDTO;
import cn.jarod.bluecat.core.utils.LoggerUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

/**
 * @author Jarod Jin E-mail:kira277@163.com
 * @version 创建时间：2020/2/28
 */
@Slf4j
public class LogInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) {

        OperationLogDTO operationLog = null;
        try {
            operationLog = LoggerUtil.getLog(httpServletRequest,(HandlerMethod) handler);
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
        }
        httpServletRequest.setAttribute(LoggerUtil.LOG_OPERATE, operationLog);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, ModelAndView modelAndView){

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, Exception e)  {
        //返回视图时，插入操作日志
        OperationLogDTO operationLog = (OperationLogDTO) httpServletRequest.getAttribute(LoggerUtil.LOG_OPERATE);
        if (operationLog == null) {
            log.warn("日志信息为空");
        } else {
            HashMap<String, Object> logMap = new HashMap<>(8);
            logMap.put("operatorTime",operationLog.getOperatorTime());
            logMap.put("ip",operationLog.getIp());
            logMap.put("description",operationLog.getDescription());
            logMap.put("operation",operationLog.getOperationType());
            logMap.put("operator",operationLog.getOperator());
            logMap.put("result", null == httpServletRequest.getAttribute("flag"));
            log.info("执行记录系统操作日志操作{}",logMap);
        }


    }
}
