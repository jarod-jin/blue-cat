package cn.jarod.bluecat.oauth.service;

import cn.jarod.bluecat.oauth.model.IntegrationAuthentication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * @author Jarod Jin E-mail:kira277@163.com
 * @version 创建时间：2020/3/16
 */
@Slf4j
public class AbstractIntegrationAuthenticationProvider implements IntegrationAuthenticationProvider {

    @Override
    public void prepare(IntegrationAuthentication integrationAuthentication) {
    }

    @Override
    public boolean support(IntegrationAuthentication integrationAuthentication) {
        return false;
    }

    @Override
    public void complete(IntegrationAuthentication integrationAuthentication) {

    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
