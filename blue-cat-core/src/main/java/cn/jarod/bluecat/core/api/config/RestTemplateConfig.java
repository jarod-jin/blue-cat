package cn.jarod.bluecat.core.api.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * @author jarod.jin 2019/9/3
 */
@Slf4j
@Configuration
public class RestTemplateConfig {

    @Value("${rest.template.read-timeout:5000}")
    private Integer readTimeout;

    @Value("${rest.template.connect-timeout:10000}")
    private Integer connectTimeout;

    @Bean
    public RestTemplate restTemplate(SimpleClientHttpRequestFactory factory){
        return new RestTemplate(factory);
    }

    @Bean
    public SimpleClientHttpRequestFactory simpleClientHttpRequestFactory(){
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setReadTimeout(readTimeout);
        factory.setConnectTimeout(connectTimeout);
        return factory;
    }
}
