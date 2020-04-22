package cn.jarod.bluecat.auth.service;


import cn.jarod.bluecat.auth.client.UserDetailsClient;
import cn.jarod.bluecat.auth.model.IntegrationAuthentication;
import cn.jarod.bluecat.auth.model.UserAuthentication;
import cn.jarod.bluecat.core.model.auth.UserDetailDTO;
import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * @author Jarod Jin E-mail:jin_yibing@dahuatech.com
 * @version 创建时间：2020/3/9
 */
@Slf4j
@Service
@Primary
public class UsernamePasswordAuthenticationProvider extends AbstractIntegrationAuthenticationProvider {

    public static final String SUCCESS = "Success";


    private final UserDetailsClient userDetailsClient;


    public UsernamePasswordAuthenticationProvider(UserDetailsClient userDetailsClient) {
        this.userDetailsClient = userDetailsClient;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getPrincipal().toString();
        String password = authentication.getCredentials().toString();

        UserDetailDTO user = userDetailsClient.loadUserByUsername(username);

        return new UsernamePasswordAuthenticationToken(new UserAuthentication(), "N/A", user.getRoleList());
//        throw new BadCredentialsException("用户密码不正确");
    }




    @Override
    public boolean support(IntegrationAuthentication integrationAuthentication) {
        return StringUtils.isEmpty(integrationAuthentication.getAuthType());
    }

}
