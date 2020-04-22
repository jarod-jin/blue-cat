package cn.jarod.bluecat.auth.config;

import cn.jarod.bluecat.auth.service.IntegrationAuthenticationProvider;
import cn.jarod.bluecat.auth.converter.CustomUserAuthenticationConverter;
import cn.jarod.bluecat.auth.exception.CustomOauthException;
import cn.jarod.bluecat.core.common.Constant;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * @author Jarod Jin E-mail:kira277@163.com
 * @version 创建时间：2020/2/29
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    private final TokenStore tokenStore;

    private final ClientDetailsService clientDetailsService;

    private final IntegrationAuthenticationProvider integrationAuthenticator;


    public AuthorizationServerConfig(IntegrationAuthenticationProvider integrationAuthenticator,
                                     @Qualifier("CustomClientDetailsService") ClientDetailsService clientDetailsService,
                                     TokenStore tokenStore) {
        this.integrationAuthenticator = integrationAuthenticator;
        this.clientDetailsService = clientDetailsService;
        this.tokenStore = tokenStore;
    }

    /**
     * 自定义异常返回
     * @return WebResponseExceptionTranslator
     */
    @Bean
    public WebResponseExceptionTranslator<OAuth2Exception> customWebResponseExceptionTranslator() {
        return exception -> {
            if (exception instanceof OAuth2Exception) {
                OAuth2Exception oAuth2Exception = (OAuth2Exception) exception;
                return ResponseEntity
                        .status(oAuth2Exception.getHttpErrorCode())
                        .body(new CustomOauthException(oAuth2Exception.getMessage()));
            } else if (exception instanceof AuthenticationException) {
                AuthenticationException authenticationException = (AuthenticationException) exception;
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body(new CustomOauthException(authenticationException.getMessage()));
            }
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new CustomOauthException(exception.getMessage()));
        };
    }

    @Bean
    public AccessTokenConverter customAccessTokenConverter(){
        DefaultAccessTokenConverter defaultAccessTokenConverter = new DefaultAccessTokenConverter();
        defaultAccessTokenConverter.setUserTokenConverter(new CustomUserAuthenticationConverter());
        return defaultAccessTokenConverter;
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
     * 客户端登录信息
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(clientDetailsService);
    }

    /**
     * 认证服务端点配置
     * 密码模式下需要配置认证管理器AuthenticationManager
     *
     * @param endpoints
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints.tokenStore(this.tokenStore)
                .authenticationManager(integrationAuthenticator::authenticate)
                .exceptionTranslator(customWebResponseExceptionTranslator())
                .accessTokenConverter(customAccessTokenConverter());
    }

    /**
     * 认证安全检查流程配置
     * 配置checkTokenAccess为`isAuthenticated()`，允许所有客户端发送请求，避免Spring Security拦截。默认是`denyAll()`，
     * @param security
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        security
                .allowFormAuthenticationForClients()
                .tokenKeyAccess(Constant.Auth.PERMIT)
                .checkTokenAccess(Constant.Auth.AUTHENTICATED);
    }
}