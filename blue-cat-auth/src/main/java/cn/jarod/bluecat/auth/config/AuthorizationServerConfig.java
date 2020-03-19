package cn.jarod.bluecat.auth.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.RandomValueAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import java.util.concurrent.TimeUnit;

/**
 * @author Jarod Jin E-mail:kira277@163.com
 * @version 创建时间：2020/2/29
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    private final RedisConnectionFactory redisConnectionFactory;

    private final ClientDetailsService clientDetailsService;

    private final IntegrationAuthenticationProvider integrationAuthenticator;

    private final IntegrationAuthenticationFilter integrationAuthenticationFilter;

    public AuthorizationServerConfig(IntegrationAuthenticationProvider integrationAuthenticator,
                                     IntegrationUserDetailsServiceImpl integrationUserDetailsService,
                                     RedisConnectionFactory redisConnectionFactory,
                                     @Qualifier("CustomClientDetailsService") ClientDetailsService clientDetailsService,
                                     IntegrationAuthenticationFilter integrationAuthenticationFilter) {
        this.integrationAuthenticator = integrationAuthenticator;
        this.integrationUserDetailsService = integrationUserDetailsService;
        this.redisConnectionFactory = redisConnectionFactory;
        this.clientDetailsService = clientDetailsService;
        this.integrationAuthenticationFilter = integrationAuthenticationFilter;
    }

    /**
     * 注册一个密码生成器
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }


    /**
     * 注入Redis保存信息的Bean
     * @param redisConnectionFactory
     * @return TokenStore
     */
    @Bean
    public TokenStore tokenStore(RedisConnectionFactory redisConnectionFactory) {
        return new RedisTokenStore(redisConnectionFactory);
    }


    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(clientDetailsService);
    }

    /**
     * 认证服务端点配置
     * 密码模式下需要配置认证管理器AuthenticationManager
     * @param endpoints
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints){
        endpoints
                .tokenStore(tokenStore(redisConnectionFactory))
                .authenticationManager(integrationAuthenticator::authenticate);
    }


    /**
     * 认证安全检查流程配置
     * 配置checkTokenAccess为`permitAll()`，允许所有客户端发送请求，避免Spring Security拦截。默认是`denyAll()`，
     * @param security
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        security
                .allowFormAuthenticationForClients()
                .tokenKeyAccess("isAuthenticated()")
                .checkTokenAccess("permitAll()");
    }

}