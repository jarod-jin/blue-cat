package cn.jarod.bluecat.auth.service;

import cn.jarod.bluecat.auth.client.UserDetailsClient;
import cn.jarod.bluecat.auth.model.IntegrationAuthentication;
import cn.jarod.bluecat.auth.model.UserAuthentication;
import cn.jarod.bluecat.core.common.Constant;
import cn.jarod.bluecat.core.common.ReturnCode;
import cn.jarod.bluecat.core.model.auth.UserDetailDTO;
import cn.jarod.bluecat.core.utils.ApiResultUtil;
import cn.jarod.bluecat.core.utils.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.framework.qual.DefaultInUncheckedCodeFor;
import org.springframework.data.redis.core.RedisTemplate;
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

    private final RedisTemplate redisTemplate;

    public SmsCodeAuthenticationProvider(UserDetailsClient userDetailsClient,RedisTemplate redisTemplate) {
        this.userDetailsClient = userDetailsClient;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String tel = authentication.getPrincipal().toString();
        String secretCode = authentication.getCredentials().toString();
        String key = Constant.Redis.SMS_TEL_PREFIX + tel;
        if (redisTemplate.hasKey(key)){
            String redisCode = redisTemplate.opsForValue().get(key).toString();
            if (redisCode.equals(secretCode)){
                UserDetailDTO user = userDetailsClient.loadUserByTel(tel);
                return new UsernamePasswordAuthenticationToken(new UserAuthentication(), "N/A", user.getRoleList());
            }
            throw new BadCredentialsException("验证码不正确");
        }
        throw new BadCredentialsException("验证码已过期");
    }

    @Override
    public boolean support(IntegrationAuthentication integrationAuthentication) {
        return SMS_CODE_AUTH_TYPE.equals(integrationAuthentication.getAuthType());
    }
}
