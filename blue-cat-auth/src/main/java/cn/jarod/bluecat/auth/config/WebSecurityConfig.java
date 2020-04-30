package cn.jarod.bluecat.auth.config;

import cn.jarod.bluecat.core.component.CustomLogoutHandler;
import cn.jarod.bluecat.core.component.CustomSecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * @author Jarod Jin E-mail:kira277@163.com
 * @version 创建时间：2020/4/30
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    private final CustomSecurityProperties propertyConfig;

    private final TokenStore tokenStore;

    public static final String JSESSIONID = "JSESSIONID";

    public WebSecurityConfig(CustomSecurityProperties propertyConfig,TokenStore tokenStore) {
        this.tokenStore = tokenStore;
        this.propertyConfig = propertyConfig;
    }

    /**
     * 注册一个密码生成器
     *
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
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


    /**
     * 必须注入 AuthenticationManager，不然oauth  无法处理四种授权方式
     *
     * @return
     * @throws Exception
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
