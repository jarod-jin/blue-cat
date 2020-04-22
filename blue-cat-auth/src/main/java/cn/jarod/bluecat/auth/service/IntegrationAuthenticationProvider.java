package cn.jarod.bluecat.auth.service;

import cn.jarod.bluecat.auth.model.IntegrationAuthentication;
import org.springframework.security.authentication.AuthenticationProvider;


/**
 * @author Jarod Jin E-mail:kira277@163.com
 * @version 创建时间：2020/3/16
 */
public interface IntegrationAuthenticationProvider extends AuthenticationProvider {


    /**
     * 进行预处理
     * @param integrationAuthentication
     */
    void prepare(IntegrationAuthentication integrationAuthentication);

    /**
     * 判断是否支持集成认证类型
     * @param integrationAuthentication
     * @return
     */
    boolean support(IntegrationAuthentication integrationAuthentication);

    /**
     * 认证结束后执行
     * @param integrationAuthentication
     */
    void complete(IntegrationAuthentication integrationAuthentication);


}
