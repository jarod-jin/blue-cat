package cn.jarod.bluecat.core.log.component;

import cn.jarod.bluecat.core.log.pojo.OperationLogDTO;
import cn.jarod.bluecat.core.log.utils.LoggerUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Jarod Jin E-mail:kira277@163.com
 * @version 创建时间：2020/2/28
 */
@Slf4j
public class SyslogInterceptor implements HandlerInterceptor {

    @Autowired
    private OperationLogService operationLogService;

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
            operationLog.setOperationType(httpServletRequest.getAttribute("flag")==null? String.valueOf(httpServletRequest.getAttribute("flag")):"N/A");
            log.info("执行记录系统操作日志操作{}",operationLog.toString());
            operationLogService.create(operationLog);
        }
    }
}
