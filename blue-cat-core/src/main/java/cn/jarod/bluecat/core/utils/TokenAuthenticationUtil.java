package cn.jarod.bluecat.core.utils;

import cn.jarod.bluecat.core.enums.ReturnCode;
import cn.jarod.bluecat.core.model.ResultBO;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.ImmutableMap;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * @auther jarod.jin 2018/12/3
 */
@Slf4j
public class TokenAuthenticationUtil {
    /**
     * 过期时间 8小时
     */
    static final long EXPIRATIONTIME = 1000 * 60 * 60 * 8;
    /**
     * JWT 密码
     */
    static final String SECRET = "dahua.isdp";
    /**
     * TOKEN前缀
     */
    static final String TOKEN_PREFIX = "Bearer";
    /**
     * 存放Token的Header Key
     */

    static final String AUTHORITIES = "authorities";

    public static final String USER_INFO= "info";

    public static final String AUTH = "token";

    public static void addAuthentication(HttpServletResponse response, Authentication auth) {
        // 生成JWT
        try {
            String jwt = getJWTString(auth);
            // 将 JWT 写入 Map
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            response.setStatus(HttpServletResponse.SC_OK);
//            log.info("授权返回："+ JSON.toJSONString(auth)+" 令牌为：" + jwt);
            response.getWriter().print(JSON.toJSONString(new ResultBO(ReturnCode.Q200.name(), "登录成功", ImmutableMap.<String, Object> builder()
                    .put(AUTH,jwt)
                    .build())));
            response.getWriter().close();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }


    public static String getJWTString(Authentication auth){
        String role = getRolesFromAuthority(auth);
        // 生成JWT
        return Jwts.builder()
                // 保存权限（角色）
                .claim(AUTHORITIES,role)
//                .claim(USER_INFO, JSON.toJSONString(auth.getDetails()))
                // 用户名写入标题
                .setSubject(auth.getName())
                // 有效期设置
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
                // 签名设置
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

    public static Authentication getAuthentication(HttpServletRequest request) {
        // 从Header中拿到token
        String token = request.getHeader(AUTH);
        if (token != null) {
            // 解析 Token
            Claims claims = Jwts.parser()
                    // 验签
                    .setSigningKey(SECRET)
                    // 去掉 Bearer
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                    .getBody();

            // 拿用户名
            String user = claims.getSubject();
            // 得到 权限（角色）
            List<GrantedAuthority> authorities =  AuthorityUtils.commaSeparatedStringToAuthorityList((String) claims.get(AUTHORITIES));
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user,null, authorities);
//            PersonInfoVO details = JSON.parseObject((String)claims.get(USER_INFO), PersonInfoVO.class);
//            authenticationToken.setDetails(details);
            // 返回验证令牌
            return user != null ? authenticationToken : null;
        }
        return null;
    }


    public static String getRolesFromAuthority(Authentication auth){
        StringBuilder builder = new StringBuilder();
        for (GrantedAuthority authority : auth.getAuthorities()) {
            builder.append(authority.getAuthority()).append(",");
        }
        return builder.toString().substring(0, builder.length() - 1);
    }


}
