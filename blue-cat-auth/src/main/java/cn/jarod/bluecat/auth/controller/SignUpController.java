package cn.jarod.bluecat.auth.controller;

import cn.jarod.bluecat.auth.procedure.CredentialAuthenticate;
import cn.jarod.bluecat.core.controller.BaseController;
import cn.jarod.bluecat.core.model.ResultDTO;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

/**
 * @author Jarod.jin
 */
@RestController
@RequestMapping(value = "/signup")
public class SignUpController extends BaseController {

    private final CredentialAuthenticate credentialAuthenticate;

    public SignUpController(CredentialAuthenticate credentialAuthenticate) {
        this.credentialAuthenticate = credentialAuthenticate;
    }

    @GetMapping(value = "/valid/{type}/{text}")
    public ResultDTO infoValid(@PathVariable("type") @NotBlank String type, @PathVariable("text") @NotBlank String text) {
        return credentialAuthenticate.validAuthority(type,text);
    }
}
