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
public class SecurityPropertyConfiguration {

    /**登录url*/
    private  String loginUrl;

    /**允许访问的url*/
    private  String[] permitAllUrl;

    /**过期时间 单位小时*/
    private long expireTime;

    /**JWT 密码*/
    private String tokenSalt;

    /**TOKEN前缀*/
    private String tokenPrefix;

}
