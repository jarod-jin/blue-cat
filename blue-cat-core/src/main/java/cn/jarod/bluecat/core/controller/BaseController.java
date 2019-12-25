package cn.jarod.bluecat.core.controller;

import cn.jarod.bluecat.core.enums.ReturnCode;
import cn.jarod.bluecat.core.exception.BaseException;
import cn.jarod.bluecat.core.model.auth.UserAuthority;
import cn.jarod.bluecat.core.model.auth.UserDetailDTO;
import cn.jarod.bluecat.core.model.auth.UserGrantedAuthority;
import cn.jarod.bluecat.core.utils.BeanHelperUtil;
import cn.jarod.bluecat.core.utils.EncryptUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;

import javax.security.auth.login.CredentialException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author jarod.jin 2019/9/3
 */
@Slf4j
public class BaseController {

    @Autowired
    private StringRedisTemplate redisTemplate;
    
    protected UserDetailDTO findCurrentUserInfo(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        try {
            ValueOperations<String, String> operations = redisTemplate.opsForValue();
            String userJson = operations.get(EncryptUtil.stringEncodeMD5(String.valueOf(auth.getPrincipal())));
            if (StringUtils.hasText(userJson)){
                return JSON.parseObject(userJson,UserDetailDTO.class);
            }else {
                log.error("获取Redis缓存中用户：{}的权限信息失败！请重新登录！", auth.getPrincipal());
            }
        } catch (Exception e) {
            log.error("获取Redis缓存中用户：{}的权限信息失败！错误信息为：{}", auth.getPrincipal() ,e.getMessage());
        }
        throw new BaseException(ReturnCode.FORBIDDEN);
    }
}
