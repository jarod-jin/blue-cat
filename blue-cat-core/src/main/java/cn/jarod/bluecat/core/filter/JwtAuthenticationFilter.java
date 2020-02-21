package cn.jarod.bluecat.core.filter;

import cn.jarod.bluecat.core.common.Constant;
import cn.jarod.bluecat.core.component.SecurityProperties;
import cn.jarod.bluecat.core.utils.ApiResultUtil;
import cn.jarod.bluecat.core.utils.JsonUtil;
import cn.jarod.bluecat.core.utils.TokenAuthenticationUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


/**
 * @author jarod.jin 2018/12/3
 */
@Slf4j
public class JwtAuthenticationFilter extends GenericFilterBean {

    private static final String SIGN_FALSE = "签名校验失败 ";

    private static final String TOKEN_EXPIRE = "认证已过期 ";

    private static final String LOGIN_FAIL = "认证验证失败 ";

    private SecurityProperties securityConfig;

    public JwtAuthenticationFilter(SecurityProperties config) {
        this.securityConfig = config;
    }


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        try {
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            Authentication authentication = TokenAuthenticationUtil.getAuthentication((HttpServletRequest) request, securityConfig);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (SignatureException e) {
            log.warn(SIGN_FALSE + Constant.Symbol.BRACE, e.getMessage());
            response.getWriter().print(JsonUtil.toJson(ApiResultUtil.fail4Unauthorized(SIGN_FALSE)));
            response.getWriter().close();
            return;
        }catch (ExpiredJwtException e){
            log.warn(TOKEN_EXPIRE + Constant.Symbol.BRACE, e.getMessage());
            response.getWriter().print(JsonUtil.toJson(ApiResultUtil.fail4Unauthorized(TOKEN_EXPIRE)));
            response.getWriter().close();
            return;
        }catch (Exception e){
            log.warn(LOGIN_FAIL + Constant.Symbol.BRACE, e.getMessage());
            response.getWriter().print(JsonUtil.toJson(ApiResultUtil.fail4Unauthorized(LOGIN_FAIL)));
            response.getWriter().close();
            return;
        }
        filterChain.doFilter(request, response);
    }


}
