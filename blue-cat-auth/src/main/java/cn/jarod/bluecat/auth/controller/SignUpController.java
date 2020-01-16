package cn.jarod.bluecat.auth.controller;

import cn.jarod.bluecat.auth.model.dto.SignUpDTO;
import cn.jarod.bluecat.auth.procedure.UserAuthenticationHandler;
import cn.jarod.bluecat.core.annotation.ApiIdempotent;
import cn.jarod.bluecat.core.annotation.TimeDiff;
import cn.jarod.bluecat.core.controller.BaseController;
import cn.jarod.bluecat.core.model.ResultDTO;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author Jarod.jin
 */
@RestController
@RequestMapping(value = "/signup")
public class SignUpController extends BaseController {

    private final UserAuthenticationHandler userAuthenticationHandler;

    public SignUpController(UserAuthenticationHandler userAuthenticationHandler) {
        this.userAuthenticationHandler = userAuthenticationHandler;
    }

    @TimeDiff
    @GetMapping(value = "/valid")
    public ResultDTO infoValid(@Param("type") @NotBlank String type, @Param("text") @NotBlank String text) {
        return userAuthenticationHandler.validAuthority(type,text);
    }

    @TimeDiff
    @ApiIdempotent
    @PostMapping
    public ResultDTO create(@RequestBody @NotNull SignUpDTO upDTO) {
        return userAuthenticationHandler.signUp(upDTO);
    }

}
