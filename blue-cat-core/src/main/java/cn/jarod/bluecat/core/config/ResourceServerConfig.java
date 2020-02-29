package cn.jarod.bluecat.core.config;

import cn.jarod.bluecat.core.common.Constant;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/**
 * @author Jarod Jin E-mail:kira277@163.com
 * @version 创建时间：2020/2/29
 */
@Configuration
@EnableResourceServer()
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {


    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        /**
         * 如果关闭 stateless，则 accessToken 使用时的 session id 会被记录，后续请求不携带 accessToken 也可以正常响应
         */
        resources.resourceId(Constant.Common.ACCESS_TOKEN).stateless(true);

//            resources.resourceId(Constant.Common.ACCESS_TOKEN).stateless(false);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
        //                .sessionManagement()
//                    .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
//                    .and()
                .requestMatchers()
                // 保险起见，防止被主过滤器链路拦截
                .antMatchers("/release/**").and()
                .authorizeRequests().anyRequest().authenticated();
//                .and()
//                .authorizeRequests()
//                .antMatchers("/qq/info/**").access("#oauth2.hasScope('get_user_info')")
//                .antMatchers("/qq/fans/**").access("#oauth2.hasScope('get_fanslist')");

    }
}
