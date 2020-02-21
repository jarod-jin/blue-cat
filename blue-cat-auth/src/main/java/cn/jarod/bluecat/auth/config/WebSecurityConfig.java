package cn.jarod.bluecat.auth.config;


import cn.jarod.bluecat.core.component.SecurityProperties;
import cn.jarod.bluecat.core.filter.JwtAuthenticationFilter;
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
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author 45216
 */
@Configuration
@EnableWebSecurity
@EnableConfigurationProperties
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final SecurityProperties propertyConfig;

    private final AuthenticationProvider customAuthenticationProvider;



    @Autowired
    public WebSecurityConfig(SecurityProperties propertyConfig, AuthenticationProvider customAuthenticationProvider) {
        this.propertyConfig = propertyConfig;
        this.customAuthenticationProvider = customAuthenticationProvider;
    }


    @Autowired
    public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        /*使用自定义验证组件*/
        authenticationManagerBuilder.authenticationProvider(customAuthenticationProvider);
    }


    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
            /*由于使用的是JWT，我们这里不需要csrf*/
            .csrf().disable()
            /*基于token，所以不需要session*/
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
            /*对请求进行认证*/
            .authorizeRequests()
            /*注册的所有请求 都放行*/
            .antMatchers(propertyConfig.getPermitAll()).permitAll()
            /*对Rest请求需要身份认证, 放行OPTIONS*/
            .antMatchers(HttpMethod.POST).authenticated()
            .antMatchers(HttpMethod.PUT).authenticated()
            .antMatchers(HttpMethod.DELETE).authenticated()
            .antMatchers(HttpMethod.GET).authenticated()
            .antMatchers(HttpMethod.PATCH).authenticated()
            .and()
            /*添加一个过滤器验证其他请求的Token是否合法*/
            .addFilterBefore(new JwtAuthenticationFilter(propertyConfig), UsernamePasswordAuthenticationFilter.class);

        /*禁用缓存*/
        httpSecurity.headers().cacheControl();
    }
}
