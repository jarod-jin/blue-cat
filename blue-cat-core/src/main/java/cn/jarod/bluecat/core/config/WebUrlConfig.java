package cn.jarod.bluecat.core.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @auther jarod.jin 2019/6/12
 */
@Setter
@Getter
@Component
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "security.jwt.url")
public class WebUrlConfig {

    private  String login;

    private  String[] permitAll;
}
