package cn.jarod.bluecat.auth.authorization;



import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author Jarod Jin E-mail:jin_yibing@dahuatech.com
 * @version 创建时间：2020/3/9
 */
@Slf4j
@Service
@Primary
public class UsernamePasswordAuthenticationProvider extends AbstractIntegrationAuthenticationProvider {

    private static final String PASSWORD = "password";

    @Value("${spring.profiles.active}")
    private String profiles;

    private final UserDetailsService integrationUserDetailsService;

    public UsernamePasswordAuthenticationProvider(@Qualifier("integrationUserDetailsService") UserDetailsService integrationUserDetailsService) {
        this.integrationUserDetailsService = integrationUserDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = (String) authentication.getPrincipal();
        //TODO 目前为明文密码，加密密码在此处理
        String password = (String) authentication.getCredentials();
        if (log.isDebugEnabled()) {
            log.debug("前端传过来的明文密码:" + password);
        }
        //AD域验证
        if (profiles.equals("prod")){
            boolean loginSuccess = LDAPUtil.authenricate(username, password);
            if (!loginSuccess) {
                //throw new DisabledException("password is invalid.");
                throw new DisabledException("Please Enter the correct user name or password.");
            }
        }
        UserDetailDTO userDO = (UserDetailDTO)integrationUserDetailsService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDO,password,userDO.getAuthorities());
        token.setDetails(userDO);
        return token;
    }

    @Override
    public boolean support(IntegrationAuthentication integrationAuthentication) {
        return StringUtils.isEmpty(integrationAuthentication.getAuthType());
    }
}
