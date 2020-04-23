package cn.jarod.bluecat.auth.service;

import cn.jarod.bluecat.auth.client.UserDetailsClient;
import cn.jarod.bluecat.auth.model.IntegrationAuthentication;
import cn.jarod.bluecat.auth.model.UserAuthentication;
import cn.jarod.bluecat.core.model.auth.UserDetailDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

/**
 * @author Jarod Jin E-mail:kira277@163.com
 * @version 创建时间：2020/4/23
 */
@Slf4j
@Service
public class SmsCodeAuthenticationProvider extends AbstractIntegrationAuthenticationProvider {

    private final static String SMS_CODE_AUTH_TYPE = "sms";

    private final UserDetailsClient userDetailsClient;

    public SmsCodeAuthenticationProvider(UserDetailsClient userDetailsClient) {
        this.userDetailsClient = userDetailsClient;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getPrincipal().toString();
        String password = authentication.getCredentials().toString();
        if (!user.getPassword().equals(password)){
            UserDetailDTO user = userDetailsClient.loadUserByTel(username);
            return new UsernamePasswordAuthenticationToken(new UserAuthentication(), "N/A", user.getRoleList());

        }
        throw new BadCredentialsException("验证码不正确");
    }

    @Override
    public boolean support(IntegrationAuthentication integrationAuthentication) {
        return SMS_CODE_AUTH_TYPE.equals(integrationAuthentication.getAuthType());
    }
}
