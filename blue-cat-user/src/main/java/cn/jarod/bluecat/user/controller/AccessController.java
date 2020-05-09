package cn.jarod.bluecat.user.controller;

import cn.jarod.bluecat.auth.model.dto.SignUpDTO;
import cn.jarod.bluecat.auth.procedure.UserAuthenticationHandler;
import cn.jarod.bluecat.core.annotation.ApiIdempotent;
import cn.jarod.bluecat.core.annotation.TimeDiff;
import cn.jarod.bluecat.core.base.controller.BaseController;
import cn.jarod.bluecat.core.model.auth.AuthCredentials;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author Jarod.jin
 */
@RestController
@RequestMapping(value = "/access")
public class AccessController extends BaseController {

    private final UserAuthenticationHandler userAuthenticationHandler;

    public AccessController(UserAuthenticationHandler userAuthenticationHandler) {
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

    @PostMapping(value = "${security.route.authentication.path}")
    public ResultDTO createAuthenticationToken(@RequestBody AuthCredentials credentials){
        return userAuthenticationHandler.signIn(credentials);
    }

    @GetMapping(value = "${security.route.authentication.refresh}")
    public ResultDTO refreshAndGetAuthenticationToken(HttpServletRequest request){
        return userAuthenticationHandler.refreshAccessToken(request);
    }

}
