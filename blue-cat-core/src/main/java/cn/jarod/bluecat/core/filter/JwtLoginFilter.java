package cn.jarod.bluecat.core.filter;

import cn.jarod.bluecat.core.config.SecurityPropertyConfig;
import cn.jarod.bluecat.core.enums.ReturnCode;
import cn.jarod.bluecat.core.exception.BaseException;
import cn.jarod.bluecat.core.model.ResultDTO;
import cn.jarod.bluecat.core.model.auth.AuthCredentials;
import cn.jarod.bluecat.core.model.auth.UserGrantedAuthority;
import cn.jarod.bluecat.core.utils.ApiResultUtil;
import cn.jarod.bluecat.core.utils.TokenAuthenticationUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author jarod.jin 2018/12/3
 */
@Slf4j
public class JwtLoginFilter extends AbstractAuthenticationProcessingFilter {

    private SecurityPropertyConfig securityConfig;

    public static final String LOGIN_INFO_MISS = "登录信息不完善";

    public JwtLoginFilter(AuthenticationManager authManager, SecurityPropertyConfig config) {
        super(new AntPathRequestMatcher(config.getLoginUrl(), RequestMethod.POST.toString()));
        setAuthenticationManager(authManager);
        this.securityConfig = config;
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
            throws IOException {
        /*JSON反序列化成 User**/
        AuthCredentials credentials = JSON.parseObject(req.getInputStream(), AuthCredentials.class);
        if (credentials==null || credentials.loginValid()){
            log.info(LOGIN_INFO_MISS);
            throw new BadCredentialsException(LOGIN_INFO_MISS);
        }
        /*返回一个验证令牌*/
        return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(credentials.getSignIn(),
                credentials.getPassword(), Lists.newArrayList(new UserGrantedAuthority(credentials.getBelongTo(), credentials.getTerminalVersion()))));
    }


    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res,
                                            FilterChain chain, Authentication auth) {
        TokenAuthenticationUtil.addAuthentication(res, auth, securityConfig);
    }


    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().print(JSON.toJSONString(ApiResultUtil.fail4Unauthorized(failed.getMessage())));
        response.getWriter().close();
    }
}
