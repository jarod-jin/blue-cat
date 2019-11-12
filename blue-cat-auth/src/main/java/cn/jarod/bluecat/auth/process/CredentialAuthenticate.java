package cn.jarod.bluecat.auth.process;

import cn.jarod.bluecat.auth.model.dto.ValidSignUpDTO;
import cn.jarod.bluecat.auth.service.ICredentialService;
import cn.jarod.bluecat.core.enums.ReturnCode;
import cn.jarod.bluecat.core.model.ResultDTO;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

/**
 * @auther jarod.jin 2019/9/10
 */
@Slf4j
@Service
public class CredentialAuthenticate {


    @Autowired
    private ICredentialService credentialService;


    public ResultDTO validAuthority(@Valid ValidSignUpDTO authBO) {
        log.info("validAuthority校验参数为：{}", JSON.toJSONString(authBO));
        credentialService.validSignUp(authBO);
        return new ResultDTO(ReturnCode.Q200, authBO);
    }




}
