package cn.jarod.bluecat.core.api.controller;

import cn.jarod.bluecat.core.api.enums.ReturnCode;
import cn.jarod.bluecat.core.api.exception.BaseException;
import cn.jarod.bluecat.core.security.pojo.UserDetailDO;
import cn.jarod.bluecat.core.common.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


/**
 * @author jarod.jin 2019/9/3
 */
@Slf4j
public class BaseController {
    
    protected UserDetailDO findCurrentUserInfo(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.isAuthenticated()){
            String userJson = String.valueOf(auth.getPrincipal());
            return JsonUtil.parsePojo(userJson, UserDetailDO.class);
        }
        log.warn("获取用户：{} 的基本信息失败！请重新登录！", auth.getPrincipal());
        throw new BaseException(ReturnCode.FORBIDDEN);
    }
}
