package cn.jarod.bluecat.access.user.controller;

import cn.jarod.bluecat.core.log.annotation.TimeDiff;
import cn.jarod.bluecat.core.api.controller.BaseController;
import org.springframework.web.bind.annotation.*;

/**
 * @author jarod.jin
 */
@RestController
@RequestMapping(value = "/user")
public class UserInfoController extends BaseController {

    @TimeDiff
    @GetMapping(value = "/hello")
    public String query() {
        return "hello. baby";
    }



}
