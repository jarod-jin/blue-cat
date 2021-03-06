package cn.jarod.bluecat.oauth.service;

import cn.jarod.bluecat.oauth.client.UserDetailsClient;
import cn.jarod.bluecat.oauth.model.IntegrationAuthentication;
import cn.jarod.bluecat.oauth.model.UserAuthentication;
import cn.jarod.bluecat.core.common.enums.Constant;
import cn.jarod.bluecat.core.security.pojo.UserDetailDO;
import lombok.extern.slf4j.Slf4j;
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
                UserDetailDO user = userDetailsClient.loadUserByTel(tel);
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
