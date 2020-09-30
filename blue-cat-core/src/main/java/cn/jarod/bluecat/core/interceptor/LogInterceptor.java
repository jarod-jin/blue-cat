package cn.jarod.bluecat.core.interceptor;

import cn.jarod.bluecat.core.model.OperationLogDTO;
import cn.jarod.bluecat.core.model.ResultDTO;
import cn.jarod.bluecat.core.utils.JsonUtil;
import cn.jarod.bluecat.core.utils.LoggerUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.TaskExecutor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Jarod Jin E-mail:kira277@163.com
 * @version 创建时间：2020/2/28
 */
@Slf4j
public class LogInterceptor implements HandlerInterceptor {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private TaskExecutor taskExecutor;

    @Value("${sys-domain.common:https://localhost:8080/dahua-b-common}")
    private String commonPath;

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
            log.info("执行记录系统操作日志操作{}", JsonUtil.toJson(operationLog));
            //如果有值则调用common接口进行日志信息插入
            if(!operationLog.getFlag() && StringUtils.hasText(operationLog.getModuleName())){
                sendErrorLog(httpServletRequest, operationLog);
            }
        }
    }

    /**
     * 将错误日志信息发送到common模块
     * @param httpServletRequest
     * @param operationLog
     */
    private void sendErrorLog(HttpServletRequest httpServletRequest, OperationLogDTO operationLog) {
        taskExecutor.execute(() -> {
            log.info("将错误日志信息发送到common模块");
            HttpHeaders httpHeaders = new HttpHeaders();
            //模拟发送token请求，后期需修改
            httpHeaders.add("Authorization", httpServletRequest.getHeader("Authorization"));
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<OperationLogDTO> httpEntity = new HttpEntity<>(operationLog, httpHeaders);
            ResponseEntity<ResultDTO> resultEntity= restTemplate.postForEntity(commonPath+"/queue-log", httpEntity, ResultDTO.class);
            if (resultEntity.getStatusCodeValue()!=200){
                log.error("日志记录失败，{}",resultEntity.getBody());
            }
            if (resultEntity.getStatusCodeValue()==200){
                ResultDTO result = resultEntity.getBody();
                if (result.getCode()!=200){
                    log.error("日志记录失败，{}",resultEntity.getBody());
                }
            }
        });
    }
}
