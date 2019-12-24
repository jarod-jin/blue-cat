package cn.jarod.bluecat.auth.controller;

import cn.jarod.bluecat.auth.model.dto.SignUpDTO;
import cn.jarod.bluecat.auth.procedure.UserAuthenticationProcedure;
import cn.jarod.bluecat.core.controller.BaseController;
import cn.jarod.bluecat.core.model.ResultDTO;
import com.alibaba.fastjson.JSON;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author Jarod.jin
 */
@RestController
@RequestMapping(value = "/signup")
public class SignUpController extends BaseController {

    private final UserAuthenticationProcedure userAuthenticationProcedure;

    public SignUpController(UserAuthenticationProcedure userAuthenticationProcedure) {
        this.userAuthenticationProcedure = userAuthenticationProcedure;
    }

    @GetMapping(value = "/valid/{type}/{text}")
    public ResultDTO infoValid(@PathVariable("type") @NotBlank String type, @PathVariable("text") @NotBlank String text) {
        return userAuthenticationProcedure.validAuthority(type,text);
    }

    @PostMapping
    public ResultDTO create(@RequestBody @NotNull SignUpDTO upDTO) {
        return userAuthenticationProcedure.signUp(upDTO);
    }

}
