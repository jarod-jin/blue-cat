package cn.jarod.bluecat.core.filter;

import cn.jarod.bluecat.core.config.SecurityPropertyConfig;
import cn.jarod.bluecat.core.constant.Symbol;
import cn.jarod.bluecat.core.enums.ReturnCode;
import cn.jarod.bluecat.core.exception.BaseException;
import cn.jarod.bluecat.core.model.ResultDTO;
import cn.jarod.bluecat.core.utils.ApiResultUtil;
import cn.jarod.bluecat.core.utils.TokenAuthenticationUtil;
import com.alibaba.fastjson.JSON;
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

    private SecurityPropertyConfig securityConfig;

    public JwtAuthenticationFilter(SecurityPropertyConfig config) {
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
            log.warn(SIGN_FALSE + Symbol.BRACE, e.getMessage());
            response.getWriter().print(JSON.toJSONString(ApiResultUtil.fail4Unauthorized(SIGN_FALSE)));
            response.getWriter().close();
            return;
        }catch (ExpiredJwtException e){
            log.warn(TOKEN_EXPIRE + Symbol.BRACE, e.getMessage());
            response.getWriter().print(JSON.toJSONString(ApiResultUtil.fail4Unauthorized(TOKEN_EXPIRE)));
            response.getWriter().close();
            return;
        }catch (Exception e){
            log.warn(LOGIN_FAIL + Symbol.BRACE, e.getMessage());
            response.getWriter().print(JSON.toJSONString(ApiResultUtil.fail4Unauthorized(LOGIN_FAIL)));
            response.getWriter().close();
            return;
        }
        filterChain.doFilter(request, response);
    }


}
