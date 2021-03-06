package cn.jarod.bluecat.core.api.config;

import cn.jarod.bluecat.core.common.enums.Constant;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Feign配置
 * 使用FeignClient进行服务间调用，传递headers信息
 * @author jarod.jin 2019/11/21
 */
@Configuration
public class FeignConfig  implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate template) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            //添加token
            if (!template.headers().containsKey(Constant.Common.AUTHORIZATION)){
                template.header(Constant.Common.AUTHORIZATION, request.getHeader(Constant.Common.AUTHORIZATION));
            }
        }
    }
}
