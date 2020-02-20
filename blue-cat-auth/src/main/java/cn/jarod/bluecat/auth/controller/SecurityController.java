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
public class SecurityController extends BaseController {

    private final SecurityService securityService;

    public SecurityController(SecurityService securityService) {
        this.securityService = securityService;
    }

    @PostMapping
    public ResultDTO takeToken() {
        return ApiResultUtil.getSuccess(securityService.createToken());
    }


}
