package cn.jarod.bluecat.core.utils;

import cn.jarod.bluecat.core.config.SecurityPropertyConfig;
import cn.jarod.bluecat.core.enums.ReturnCode;
import cn.jarod.bluecat.core.model.ResultDTO;
import cn.jarod.bluecat.core.model.auth.UserAuthority;
import cn.jarod.bluecat.core.model.auth.UserGrantedAuthority;
import cn.jarod.bluecat.core.model.auth.UserInfoDTO;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
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
import java.util.stream.Collectors;

/**
 * @auther jarod.jin 2018/12/3
 */
@Slf4j
public class TokenAuthenticationUtil {

    private static final String AUTHORITIES = "authorities";

    private static final String USER_INFO = "userInfo";

    public static void addAuthentication(HttpServletResponse response, Authentication auth, SecurityPropertyConfig config) {
        // 生成JWT
        try {
            String jwt = getJWTString(auth, config.getExpirationTime(), config.getTokenSalt());
            // 将 JWT 写入 Map
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            response.setStatus(HttpServletResponse.SC_OK);
//            log.info("授权返回："+ JSON.toJSONString(auth)+" 令牌为：" + jwt);
            response.getWriter().print(JSON.toJSONString(new ResultDTO(ReturnCode.Q200.name(), "登录成功", ImmutableMap.<String, Object> builder()
                    .put(Const.ACCESS_TOKEN,jwt)
                    .build())));
            response.getWriter().close();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }


    private static String getJWTString(Authentication auth, long expirationTime, String salt){
        String role = getRolesFromAuthority(auth);
        // 生成JWT
        return Jwts.builder()
                // 保存权限（角色）
                .claim(AUTHORITIES,role)
                .claim(USER_INFO, JSON.toJSONString(auth.getDetails()))
                // 用户名写入标题
                .setSubject(auth.getName())
                // 有效期设置
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime ))
                // 签名设置
                .signWith(SignatureAlgorithm.HS512, salt)
                .compact();
    }

    public static Authentication getAuthentication(HttpServletRequest request, SecurityPropertyConfig config) {
        // 从Header中拿到token
        String token = request.getHeader(Const.ACCESS_TOKEN);
        if (token != null) {
            // 解析 Token
            Claims claims = Jwts.parser()
                    // 验签
                    .setSigningKey(config.getTokenSalt())
                    // 去掉 Bearer
                    .parseClaimsJws(token.replace(config.getTokenPrefix(), ""))
                    .getBody();

            // 拿用户名
            String user = claims.getSubject();
            // 得到 权限（角色）
            JSONArray authArray =  JSON.parseArray((String) claims.get(AUTHORITIES));
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user,null,
                    authArray.stream().map(m-> new UserGrantedAuthority(JSON.parseObject(String.valueOf(m),UserAuthority.class))).collect(Collectors.toList()));
            authenticationToken.setDetails(JSON.parseObject((String)claims.get(USER_INFO), UserInfoDTO.class));
            // 返回验证令牌
            return user != null ? authenticationToken : null;
        }
        return null;
    }


    private static String getRolesFromAuthority(Authentication auth){
        List<String> authStrList = auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        return JSON.toJSONString(authStrList);
    }


}
