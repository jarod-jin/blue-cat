package cn.jarod.bluecat.core.data.es.component;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author : jarod_jin
 * @version : 1.0
 * @description : AWS的ES配置类
 * @create : 2020-09-22
 **/
@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "aws.es")
public class EsClientProperties {

    private String serviceName;

    private String region;

    private String accessKey;

    private String secretKey;

    private String aesEndpoint;

}

