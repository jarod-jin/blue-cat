package cn.jarod.bluecat.auth.controller;

import cn.jarod.bluecat.auth.procedure.UserAuthenticationProcedure;
import cn.jarod.bluecat.core.controller.BaseController;
import cn.jarod.bluecat.core.model.ResultDTO;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author jarod.jin
 */
@RestController
@RequestMapping(value = "/user")
public class UserInfoController extends BaseController {

    private final UserAuthenticationProcedure userAuthenticationProcedure;

    public UserInfoController(UserAuthenticationProcedure userAuthenticationProcedure) {
        this.userAuthenticationProcedure = userAuthenticationProcedure;
    }

    @GetMapping(value = "/{username}")
    public ResultDTO query(@PathVariable("username") @NotNull String username, @PathVariable("text") @NotBlank String text) {
        return userAuthenticationProcedure.validAuthority(username,text);
    }

    @PutMapping(value = "/{username}")
    public ResultDTO modify(@PathVariable("username") @NotBlank String username, @PathVariable("text") @NotBlank String text) {
        return userAuthenticationProcedure.validAuthority(username,text);
    }
}
