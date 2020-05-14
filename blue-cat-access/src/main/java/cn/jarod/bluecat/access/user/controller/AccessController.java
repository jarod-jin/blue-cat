package cn.jarod.bluecat.access.user.controller;


import cn.jarod.bluecat.core.annotation.TimeDiff;
import cn.jarod.bluecat.core.base.controller.BaseController;

import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

/**
 * @author Jarod.jin
 */
@RestController
@RequestMapping(value = "/access")
public class AccessController extends BaseController {

//    private final UserAuthenticationHandler userAuthenticationHandler;
//
//    public AccessController(UserAuthenticationHandler userAuthenticationHandler) {
//        this.userAuthenticationHandler = userAuthenticationHandler;
//    }

    @TimeDiff
    @GetMapping(value = "/valid")
    public Boolean infoValid(@Param("type") @NotBlank String type, @Param("text") @NotBlank String text) {
//        return userAuthenticationHandler.validAuthority(type,text);
        return false;
    }

//    @TimeDiff
//    @ApiIdempotent
//    @PostMapping
//    public ResultDTO create(@RequestBody @NotNull SignUpDTO upDTO) {
//        return userAuthenticationHandler.signUp(upDTO);
//    }
//
//    @PostMapping(value = "${security.route.authentication.path}")
//    public ResultDTO createAuthenticationToken(@RequestBody AuthCredentials credentials){
//        return userAuthenticationHandler.signIn(credentials);
//    }
//
//    @GetMapping(value = "${security.route.authentication.refresh}")
//    public ResultDTO refreshAndGetAuthenticationToken(HttpServletRequest request){
//        return userAuthenticationHandler.refreshAccessToken(request);
//    }

}
