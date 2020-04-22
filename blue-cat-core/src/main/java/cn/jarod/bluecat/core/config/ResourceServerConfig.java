package cn.jarod.bluecat.core.config;

import cn.jarod.bluecat.core.component.CustomLogoutHandler;
import cn.jarod.bluecat.core.component.CustomSecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * @author Jarod Jin E-mail:kira277@163.com
 * @version 创建时间：2020/3/4
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    private final CustomSecurityProperties propertyConfig;

    private final TokenStore tokenStore;

    public static final String JSESSIONID = "JSESSIONID";

    public ResourceServerConfig(CustomSecurityProperties propertyConfig,TokenStore tokenStore) {
        this.tokenStore = tokenStore;
        this.propertyConfig = propertyConfig;
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId(propertyConfig.getResourceId()).stateless(true);
    }

    /**
     *
     * @return
     */
    @Bean
    public CustomLogoutHandler customLogoutHandler(){ return new CustomLogoutHandler(tokenStore);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(propertyConfig.getPermitAll())
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .logout()
                .logoutSuccessHandler(customLogoutHandler())
                //清除cook键值
                .deleteCookies(JSESSIONID)
                .and()
                .csrf().disable();
    }
}
