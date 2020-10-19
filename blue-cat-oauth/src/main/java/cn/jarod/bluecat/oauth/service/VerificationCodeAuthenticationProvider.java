package cn.jarod.bluecat.oauth.service;


import cn.jarod.bluecat.oauth.client.UserDetailsClient;
import cn.jarod.bluecat.oauth.model.IntegrationAuthentication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.stereotype.Service;

/**
 * @author Jarod Jin E-mail:kira277@163.com
 * @version 创建时间：2020/3/16
 */
@Slf4j
@Service
public class VerificationCodeAuthenticationProvider extends UsernamePasswordAuthenticationProvider {

    private final static String VERIFICATION_CODE_AUTH_TYPE = "vc";

    public static final String VC_TOKEN = "vc_token";

    public static final String VC_CODE = "vc_code";

    public VerificationCodeAuthenticationProvider(UserDetailsClient userDetailsClient, PasswordEncoder passwordEncoder) {
        super(userDetailsClient, passwordEncoder);
    }


    @Override
    public void prepare(IntegrationAuthentication integrationAuthentication) {
        String vcToken = integrationAuthentication.getAuthParameter(VC_TOKEN);
        String vcCode = integrationAuthentication.getAuthParameter(VC_CODE);
        //验证验证码

//        Boolean flag = true;
//        // todo 校验图形验证码
//        VerifyCodeBO verifyCodeBO=new VerifyCodeBO();
//        verifyCodeBO.setKey(vcToken);
//        verifyCodeBO.setCode(vcCode);
//
//        flag=verifyCodeUtils.checkVerifyCode(verifyCodeBO);

        if (!true) {
            throw new OAuth2Exception("Incorrect verification code");
        }
    }

    @Override
    public boolean support(IntegrationAuthentication integrationAuthentication) {
        return VERIFICATION_CODE_AUTH_TYPE.equals(integrationAuthentication.getAuthType());
    }
}
