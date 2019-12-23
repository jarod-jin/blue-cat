package cn.jarod.bluecat.auth.procedure;

import cn.jarod.bluecat.auth.service.CredentialService;
import cn.jarod.bluecat.core.annotation.TimeDiff;
import cn.jarod.bluecat.core.enums.ReturnCode;
import cn.jarod.bluecat.core.model.ResultDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author jarod.jin 2019/9/10
 */
@Slf4j
@Service
public class CredentialAuthenticate {


    private final CredentialService credentialService;

    public CredentialAuthenticate(CredentialService credentialService) {
        this.credentialService = credentialService;
    }

    /**
     * 注册前校验
     * @return ResultDTO
     */
    public ResultDTO validAuthority(String type,String text) {
        return new ResultDTO(ReturnCode.GET_SUCCESS, credentialService.validSignUp(type,text));
    }




}
