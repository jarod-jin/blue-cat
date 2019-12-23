package cn.jarod.bluecat.auth.config;


import cn.jarod.bluecat.core.config.SecurityPropertyConfig;
import cn.jarod.bluecat.core.filter.JwtAuthenticationFilter;
import cn.jarod.bluecat.core.filter.JwtLoginFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author 45216
 */
@Configuration
@EnableWebSecurity
@EnableConfigurationProperties
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final SecurityPropertyConfig propertyConfig;

    private final AuthenticationProvider customAuthenticationProvider;

    @Autowired
    public WebSecurityConfig(SecurityPropertyConfig propertyConfig, AuthenticationProvider customAuthenticationProvider) {
        this.propertyConfig = propertyConfig;
        this.customAuthenticationProvider = customAuthenticationProvider;
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) {
        /*使用自定义身份验证组件*/
        auth.authenticationProvider(customAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            /*对请求进行认证*/
            .authorizeRequests()
            /*注册的所有请求 都放行*/
            .antMatchers(propertyConfig.getPermitAllUrl()).permitAll()
            /*对Rest请求需要身份认证, 放行OPTIONS*/
            .antMatchers(HttpMethod.POST).authenticated()
            .antMatchers(HttpMethod.PUT).authenticated()
            .antMatchers(HttpMethod.DELETE).authenticated()
            .antMatchers(HttpMethod.GET).authenticated()
            .and()
            /*添加一个过滤器 所有访问 /login 的请求交给 JWTLoginFilter 来处理 这个类处理所有的JWT相关内容*/
            .addFilterBefore(new JwtLoginFilter(authenticationManager(), propertyConfig), UsernamePasswordAuthenticationFilter.class)
            /*添加一个过滤器验证其他请求的Token是否合法*/
            .addFilterBefore(new JwtAuthenticationFilter(propertyConfig), UsernamePasswordAuthenticationFilter.class);
    }
}
