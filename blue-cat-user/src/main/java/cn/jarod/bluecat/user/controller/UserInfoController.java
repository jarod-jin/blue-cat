package cn.jarod.bluecat.user.controller;

import cn.jarod.bluecat.auth.procedure.UserAuthenticationHandler;
import cn.jarod.bluecat.core.annotation.ApiIdempotent;
import cn.jarod.bluecat.core.annotation.TimeDiff;
import cn.jarod.bluecat.core.base.controller.BaseController;
import cn.jarod.bluecat.core.utils.ApiResultUtil;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

/**
 * @author jarod.jin
 */
@RestController
@RequestMapping(value = "/user")
public class UserInfoController extends BaseController {

    private final UserAuthenticationHandler userAuthenticationHandler;

    public UserInfoController(UserAuthenticationHandler userAuthenticationHandler) {
        this.userAuthenticationHandler = userAuthenticationHandler;
    }

    @TimeDiff
    @GetMapping(value = "/hello")
    public ResultDTO query() {
        return ApiResultUtil.getSuccess("hello. baby");
    }

    @TimeDiff
    @ApiIdempotent
    @PostMapping(value = "/{username}")
    public ResultDTO modify(@PathVariable("username") @NotBlank String username) {
        return ApiResultUtil.getSuccess(username);
    }



}
