package cn.jarod.bluecat.core.filter;

import com.alibaba.fastjson.JSON;
import com.netflix.zuul.context.RequestContext;
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
 * @auther jarod.jin 2018/12/3
 */
@Slf4j
public class JwtAuthenticationFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        Authentication authentication;
        try {
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            authentication = TokenAuthenticationService.getAuthentication((HttpServletRequest) request);
        } catch (SignatureException e) {
            log.info("签名校验失败 ", e.getMessage());
            response.getWriter().print(JSON.toJSONString(new ResultDTO(AuthReturnCode.AU413.name(), AuthReturnCode.AU411.getMsg())));
            response.getWriter().close();
            return;
        }catch (ExpiredJwtException e){
            log.info("Token已过期 ", e.getMessage());
            response.getWriter().print(JSON.toJSONString(new ResultDTO(AuthReturnCode.AU413.name(), AuthReturnCode.AU412.getMsg())));
            response.getWriter().close();
            return;
        }catch (Exception e){
            log.info("权限验证失败 ", e.getMessage());
            response.getWriter().print(JSON.toJSONString(new ResultDTO(AuthReturnCode.AU413.name(), AuthReturnCode.AU413.getMsg())));
            response.getWriter().close();
            return;
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        if (authentication!=null){
            RequestContext requestContext = RequestContext.getCurrentContext();
            requestContext.addZuulRequestHeader(Const.AUTHENTICATION, JSON.toJSONString(authentication.getDetails()));
            String token = ((HttpServletRequest) request).getHeader(Const.HEADER_STRING);
            requestContext.addZuulRequestHeader(Const.HEADER_STRING, token);
        }
        filterChain.doFilter(request, response);
    }


}
