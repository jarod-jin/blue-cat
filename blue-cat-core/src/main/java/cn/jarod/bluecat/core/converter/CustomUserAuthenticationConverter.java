package cn.jarod.bluecat.core.converter;

import com.google.common.collect.ImmutableMap;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;

import java.util.Map;

/**
 * @author Jarod Jin E-mail:kira277@163.com
 * @version 创建时间：3/30/2020
 */
public class CustomUserAuthenticationConverter extends DefaultUserAuthenticationConverter {

    public static final String USER_NAME = "user_name";

    @Override
    public Map<String, ?> convertUserAuthentication(Authentication authentication) {
        return ImmutableMap.<String,Object>builder().put(USER_NAME, authentication.getPrincipal()).build();
    }
}
