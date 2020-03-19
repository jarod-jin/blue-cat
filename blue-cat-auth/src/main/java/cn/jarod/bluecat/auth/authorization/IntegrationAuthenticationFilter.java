package cn.jarod.bluecat.auth.authorization;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.GenericFilterBean;

import javax.annotation.Nullable;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;

/**
 * @author Jarod Jin E-mail:kira277@163.com
 * @version 创建时间：2020/3/16
 */
@Slf4j
@Service
public class IntegrationAuthenticationFilter extends GenericFilterBean implements ApplicationContextAware {


    private static final String AUTH_TYPE_PARAM_NAME = "auth_type";

    public static final String USERNAME_PARAM_NAME = "username";

    private static final String OAUTH_TOKEN_URL = "/oauth/token";


    private Collection<IntegrationAuthenticationProvider> authenticators;

    private ApplicationContext applicationContext;

    private RequestMatcher requestMatcher;

    public IntegrationAuthenticationFilter(){
        this.requestMatcher = new OrRequestMatcher(
                new AntPathRequestMatcher(OAUTH_TOKEN_URL, "GET"),
                new AntPathRequestMatcher(OAUTH_TOKEN_URL, "POST")
        );
    }


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if(requestMatcher.matches(request)){
            //设置集成登录信息
            IntegrationAuthentication integrationAuthentication = new IntegrationAuthentication();
            integrationAuthentication.setAuthType(request.getParameter(AUTH_TYPE_PARAM_NAME));
            integrationAuthentication.setUsername(request.getParameter(USERNAME_PARAM_NAME));
            integrationAuthentication.setAuthParameters(request.getParameterMap());
            IntegrationAuthenticationContext.set(integrationAuthentication);
            try{
                //预处理
                this.prepare(integrationAuthentication);

                filterChain.doFilter(request,response);

                //后置处理
                this.complete(integrationAuthentication);
            }finally {
                IntegrationAuthenticationContext.clear();
            }
        }else{
            filterChain.doFilter(request,response);
        }

    }

    /**
     * 进行预处理
     * @param integrationAuthentication
     */
    private void prepare(IntegrationAuthentication integrationAuthentication) {

        //延迟加载认证器
        if(this.authenticators == null){
            synchronized (this){
                Map<String, IntegrationAuthenticationProvider> integrationAuthenticatorMap = applicationContext.getBeansOfType(IntegrationAuthenticationProvider.class);
                this.authenticators = integrationAuthenticatorMap.values();
            }
        }

        /*
        if(this.authenticators == null){
            this.authenticators = new ArrayList<>();
        }
        */
        authenticators.stream()
                .filter(a->a.support(integrationAuthentication))
                .findAny()
                .ifPresent(e->e.prepare(integrationAuthentication));
    }

    /**
     * 后置处理
     * @param integrationAuthentication
     */
    private void complete(IntegrationAuthentication integrationAuthentication){
        authenticators.stream()
                .filter(a->a.support(integrationAuthentication))
                .findAny()
                .ifPresent(e->e.complete(integrationAuthentication));
    }

    @Override
    public void setApplicationContext(@Nullable ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
