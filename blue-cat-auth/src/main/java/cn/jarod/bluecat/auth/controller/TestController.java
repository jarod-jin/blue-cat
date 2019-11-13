package cn.jarod.bluecat.auth.controller;

import cn.jarod.bluecat.core.controller.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @auther jarod.jin 2019/9/5
 */
@Slf4j
@RestController
public class TestController extends BaseController {


    @PostMapping(value = "/hello")
    public String hello() {
        takeUserAuthInfo();
        return "hello : " + takeUserAuthInfo().getUsername();
    }

}
