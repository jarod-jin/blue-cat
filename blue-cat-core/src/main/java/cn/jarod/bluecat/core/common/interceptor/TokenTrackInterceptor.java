package cn.jarod.bluecat.core.common.interceptor;

import cn.jarod.bluecat.core.common.enums.Constant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Token拦截器，用于发送时拦截请求并在头部加入Token
 * @author Jarod Jin Email:kira277@163.com
 */
@Slf4j
@Component
public class TokenTrackInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        HttpHeaders headers = httpRequest.getHeaders();
        if (!headers.containsKey(Constant.Common.AUTHORIZATION)) {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes != null ){
                HttpServletRequest request = attributes.getRequest();
                //添加token
                headers.add(Constant.Common.AUTHORIZATION, request.getHeader(Constant.Common.AUTHORIZATION));
            }
        }
        log.info("当前的token：{}", headers.get(Constant.Common.AUTHORIZATION));
        // 保证请求继续被执行
        return execution.execute(httpRequest, body);
    }
}
