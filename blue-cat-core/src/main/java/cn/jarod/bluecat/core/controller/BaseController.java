package cn.jarod.bluecat.core.controller;

import cn.jarod.bluecat.core.model.auth.UserAuthority;
import cn.jarod.bluecat.core.model.auth.UserDetailDTO;
import cn.jarod.bluecat.core.model.auth.UserGrantedAuthority;
import cn.jarod.bluecat.core.utils.BeanHelperUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @auther jarod.jin 2019/9/3
 */
@Slf4j
public class BaseController {

    protected UserDetailDTO takeUserAuthInfo(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailDTO user = BeanHelperUtil.createCopyBean(auth.getDetails(),UserDetailDTO.class);
        List<UserAuthority> list = auth.getAuthorities().stream().map(e-> ((UserGrantedAuthority) e).getUserAuthority()).collect(Collectors.toList());
        user.setRoleList(list);
        return user;
    }
}
