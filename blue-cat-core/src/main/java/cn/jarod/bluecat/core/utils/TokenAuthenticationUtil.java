package cn.jarod.bluecat.core.utils;

import cn.jarod.bluecat.core.common.Constant;
import cn.jarod.bluecat.core.component.CustomSecurityProperties;
import cn.jarod.bluecat.core.common.ReturnCode;
import cn.jarod.bluecat.core.model.ResultDTO;
import cn.jarod.bluecat.core.model.auth.UserGrantedAuthority;
import com.google.common.collect.ImmutableMap;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import javax.servlet.http.HttpServletRequest;
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
     * @param auth 认证
     * @param config
     */
    public static ResultDTO addAuthentication(Authentication auth, CustomSecurityProperties config) {
        /*生成JWT*/
        String jwt = getJwtString(auth, TimeUnit.HOURS.toMillis(config.getExpireTime()), config.getTokenSalt());
        return new ResultDTO(ReturnCode.GET_SUCCESS.getCode(), "登录成功",
                ImmutableMap.<String, Object> builder()
                .put(Constant.Common.ACCESS_TOKEN,jwt)
                .build());
    }


    /**
     * 根据认证对象返回JWT
     * @param auth 认证信息
     * @param expirationTime 过期时间
     * @param salt 加密盐
     * @return String
     */
    private static String getJwtString(Authentication auth, long expirationTime, String salt){
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
    public static Authentication getAuthentication(HttpServletRequest request, CustomSecurityProperties config) {
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
