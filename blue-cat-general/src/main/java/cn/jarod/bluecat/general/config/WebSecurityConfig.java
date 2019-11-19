package cn.jarod.bluecat.general.config;


import cn.jarod.bluecat.core.config.WebUrlConfig;
import cn.jarod.bluecat.core.filter.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableConfigurationProperties
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private WebUrlConfig webUrlConfig;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            // 对请求进行认证
            .authorizeRequests()
            //注册的所有请求 都放行
            .antMatchers(webUrlConfig.getPermitAll()).permitAll()
            // 对Rest请求需要身份认证, 放行OPTIONS
            .antMatchers(HttpMethod.POST).authenticated()
            .antMatchers(HttpMethod.PUT).authenticated()
            .antMatchers(HttpMethod.DELETE).authenticated()
            .antMatchers(HttpMethod.GET).authenticated()
            .and()
            // 添加一个过滤器验证其他请求的Token是否合法
            .addFilterBefore(new JwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
