package cn.jarod.bluecat.core.config;

import cn.jarod.bluecat.core.interceptor.AccessLimitInterceptor;
import cn.jarod.bluecat.core.interceptor.ApiIdempotentInterceptor;
import cn.jarod.bluecat.core.log.interceptor.LogInterceptor;
import cn.jarod.bluecat.core.service.SecurityService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @author Jarod Jin E-mail:kira277@163.com
 * @version 创建时间：2020/1/15
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

    private final SecurityService securityService;

    public WebMvcConfig(SecurityService securityService) {
        this.securityService = securityService;
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        /*日志拦截器*/
        registry.addInterceptor(logInterceptor()).addPathPatterns("/**");
        /*接口幂等性拦截器*/
        registry.addInterceptor(apiIdempotentInterceptor());
        /*接口防刷限流拦截器*/
        registry.addInterceptor(accessLimitInterceptor());
        super.addInterceptors(registry);
    }

    @Bean
    public LogInterceptor logInterceptor() {
        return new LogInterceptor();
    }

    @Bean
    public ApiIdempotentInterceptor apiIdempotentInterceptor() {
        return new ApiIdempotentInterceptor(securityService);
    }

    @Bean
    public AccessLimitInterceptor accessLimitInterceptor() {
        return new AccessLimitInterceptor(securityService);
    }

}
