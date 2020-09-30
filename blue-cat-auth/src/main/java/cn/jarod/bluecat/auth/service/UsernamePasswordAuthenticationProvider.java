package cn.jarod.bluecat.auth.service;


import cn.jarod.bluecat.auth.client.UserDetailsClient;
import cn.jarod.bluecat.auth.model.IntegrationAuthentication;
import cn.jarod.bluecat.auth.model.UserAuthentication;
import cn.jarod.bluecat.core.model.auth.UserDetailDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author Jarod Jin E-mail:kira277@163.com
 * @version 创建时间：2020/3/9
 */
@Slf4j
@Service
@Primary
public class UsernamePasswordAuthenticationProvider extends AbstractIntegrationAuthenticationProvider {

    private final UserDetailsClient userDetailsClient;

    private final PasswordEncoder passwordEncoder;


    public UsernamePasswordAuthenticationProvider(UserDetailsClient userDetailsClient,PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        this.userDetailsClient = userDetailsClient;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getPrincipal().toString();
        String password = passwordEncoder.encode(authentication.getCredentials().toString());
        UserDetailDTO user = userDetailsClient.loadUserByUsername(username);
        if (!user.getPassword().equals(password)){
            throw new BadCredentialsException("用户密码不正确");
        }
        return new UsernamePasswordAuthenticationToken(new UserAuthentication(), "N/A", user.getRoleList());
    }


    @Override
    public boolean support(IntegrationAuthentication integrationAuthentication) {
        return StringUtils.isEmpty(integrationAuthentication.getAuthType());
    }

}
