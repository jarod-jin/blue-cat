package cn.jarod.bluecat.auth.controller;

import cn.jarod.bluecat.auth.procedure.CredentialAuthenticate;
import cn.jarod.bluecat.core.controller.BaseController;
import cn.jarod.bluecat.core.model.ResultDTO;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Map;

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

    @PostMapping(value = "/{username}")
    public String create(@PathVariable("username") @NotBlank String username, @RequestBody @NotNull Map map) {
        return username + JSON.toJSONString(map);
    }

}
