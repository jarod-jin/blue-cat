package cn.jarod.bluecat.core.controller;

import cn.jarod.bluecat.core.model.auth.UserDetailDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @auther jarod.jin 2019/9/3
 */
@Slf4j
public class BaseController {

    public UserDetailDTO takeUserDetailInfo(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return null;
    }
}
