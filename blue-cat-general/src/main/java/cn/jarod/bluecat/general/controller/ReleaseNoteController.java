package cn.jarod.bluecat.general.controller;

import cn.jarod.bluecat.core.api.BlueCatAuthClient;
import cn.jarod.bluecat.core.controller.BaseController;
import cn.jarod.bluecat.core.model.auth.UserDetailDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jarod.jin 2019/9/5
 */
@Slf4j
@RestController
public class ReleaseNoteController extends BaseController {

    @Autowired
    BlueCatAuthClient authService;

    @PostMapping(value = "/hello2")
    public String hello() {
        UserDetailDTO userInfo= findCurrentUserInfo();
        return "hello : " + authService.hello() + "--" + userInfo.getTerminalVersion();
    }

}
