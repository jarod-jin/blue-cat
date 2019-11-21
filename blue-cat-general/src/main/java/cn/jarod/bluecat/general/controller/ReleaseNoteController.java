package cn.jarod.bluecat.general.controller;

import cn.jarod.bluecat.core.controller.BaseController;
import cn.jarod.bluecat.core.model.auth.UserDetailDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @auther jarod.jin 2019/9/5
 */
@Slf4j
@RestController
public class ReleaseNoteController extends BaseController {



    @PostMapping(value = "/hello2")
    public String hello() {
        UserDetailDTO userInfo= takeUserAuthInfo();
        return "hello : " + userInfo.getUsername() + "-" + userInfo.getTerminalVersion();
    }

}
