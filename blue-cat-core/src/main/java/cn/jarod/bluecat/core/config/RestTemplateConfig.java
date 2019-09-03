package cn.jarod.bluecat.core.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

/**
 * @auther jarod.jin 2019/9/3
 */
@Slf4j
@Configuration
public class RestTemplateConfig {

    @Autowired
    private Environment props;

    @Bean
    public RestTemplate restTemplate(SimpleClientHttpRequestFactory factory){
        return new RestTemplate(factory);
    }

    @Bean
    public SimpleClientHttpRequestFactory simpleClientHttpRequestFactory(){
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        Integer readTimeout = Integer.parseInt(props.getProperty("rest.template.read-timeout"));
        Integer connectTimeout = Integer.parseInt(props.getProperty("rest.template.connect-timeout"));
        factory.setReadTimeout(readTimeout);//ms
        factory.setConnectTimeout(connectTimeout);//ms
        log.info("readTimeout:{}，connectTimeout：{}",readTimeout,connectTimeout);
        return factory;
    }
}
