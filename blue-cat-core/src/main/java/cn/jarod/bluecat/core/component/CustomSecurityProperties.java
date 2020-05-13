package cn.jarod.bluecat.core.component;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author jarod.jin 2019/6/12
 */
@Setter
@Getter
@Component
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "security")
public class CustomSecurityProperties {

    /**允许访问的url*/
    private  String[] permitAll;

    /**资源服务Id*/
    private String resourceId;

}
