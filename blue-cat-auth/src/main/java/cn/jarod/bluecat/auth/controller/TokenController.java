package cn.jarod.bluecat.auth.controller;

import cn.jarod.bluecat.core.controller.BaseController;
import cn.jarod.bluecat.core.model.ResultDTO;
import cn.jarod.bluecat.core.service.SecurityService;
import cn.jarod.bluecat.core.utils.ApiResultUtil;
import org.springframework.web.bind.annotation.*;

/**
 * @author Jarod.jin
 */
@RestController
@RequestMapping(value = "/token")
public class TokenController extends BaseController {

    private final SecurityService securityService;

    public TokenController(SecurityService securityService) {
        this.securityService = securityService;
    }

    @GetMapping
    public ResultDTO getToken() {
        return ApiResultUtil.getSuccess(securityService.createToken());
    }


}
