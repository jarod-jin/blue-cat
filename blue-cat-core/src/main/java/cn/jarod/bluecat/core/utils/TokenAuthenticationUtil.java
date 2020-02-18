package cn.jarod.bluecat.core.utils;

import cn.jarod.bluecat.core.common.Constant;
import cn.jarod.bluecat.core.component.SecurityPropertyConfiguration;
import cn.jarod.bluecat.core.common.ReturnCode;
import cn.jarod.bluecat.core.model.ResultDTO;
import cn.jarod.bluecat.core.model.auth.UserGrantedAuthority;
import com.google.common.collect.ImmutableMap;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author jarod.jin 2018/12/3
 */
@Slf4j
public class TokenAuthenticationUtil {

    private static final String AUTHORITIES = "authorities";
    /**
     * 返回jwt
     * @param response 返回
     * @param auth 认证
     * @param config
     */
    public static void addAuthentication(HttpServletResponse response, Authentication auth, SecurityPropertyConfiguration config) {
        /*生成JWT*/
        try {
            String jwt = getJWTString(auth, TimeUnit.HOURS.toMillis(config.getExpireTime()), config.getTokenSalt());
            /*将 JWT 写入 返回对象*/
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().print(JsonUtil.toJson(new ResultDTO(ReturnCode.GET_SUCCESS.getCode(), "登录成功",
                    ImmutableMap.<String, Object> builder()
                    .put(Constant.Common.ACCESS_TOKEN,jwt)
                    .build())));
            response.getWriter().close();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }


    /**
     * 根据认证对象返回JWT
     * @param auth 认证信息
     * @param expirationTime 过期时间
     * @param salt 加密盐
     * @return String
     */
    private static String getJWTString(Authentication auth, long expirationTime, String salt){
        String role = getRolesFromAuthority(auth);
        /*生成JWT*/
        return Jwts.builder()
                /*保存权限（角色）*/
                .claim(AUTHORITIES,role)
                /*用户名写入标题*/
                .setSubject(auth.getName())
                /*有效期设置*/
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime ))
                /*签名设置*/
                .signWith(SignatureAlgorithm.HS512, salt)
                .compact();
    }

    /**
     * 请求头中获取登录用户信息
     * @param request http请求
     * @param config 安全配置
     * @return
     */
    public static Authentication getAuthentication(HttpServletRequest request, SecurityPropertyConfiguration config) {
        /*从Header中拿到token*/
        String token = request.getHeader(Constant.Common.ACCESS_TOKEN);
        if (token != null) {
            /*解析 Token*/
            Claims claims = Jwts.parser()
                    /*验签*/
                    .setSigningKey(config.getTokenSalt())
                    /*去掉 Bearer*/
                    .parseClaimsJws(token.replace(config.getTokenPrefix(), ""))
                    .getBody();

            /*拿用户名*/
            String user = claims.getSubject();
            /*得到 权限（角色）*/
            List<String> authArray =  JsonUtil.parseObject((String) claims.get(AUTHORITIES),List.class);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user,null,
                    authArray.stream().map(m-> new UserGrantedAuthority(String.valueOf(m))).collect(Collectors.toList()));
            /*返回验证令牌*/
            return user != null ? authenticationToken : null;
        }
        return null;
    }


    private static String getRolesFromAuthority(Authentication auth){
        List<String> authStrList = auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        return JsonUtil.toJson(authStrList);
    }


}
