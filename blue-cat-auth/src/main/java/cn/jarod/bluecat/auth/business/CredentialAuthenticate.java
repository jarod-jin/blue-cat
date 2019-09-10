package cn.jarod.bluecat.auth.business;

import cn.jarod.bluecat.auth.model.ValidAuthBO;
import cn.jarod.bluecat.auth.service.ICredentialService;
import cn.jarod.bluecat.core.enums.ReturnCode;
import cn.jarod.bluecat.core.model.ResultDTO;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @auther jarod.jin 2019/9/10
 */
@Slf4j
@Component
public class CredentialAuthenticate {


    @Autowired
    private ICredentialService credentialService;


    public ResultDTO validAuthority(ValidAuthBO authBO) {
        log.info("validAuthority校验参数为：{}", JSON.toJSONString(authBO));
        credentialService.validAuthority(authBO);
        return new ResultDTO(ReturnCode.Q200, authBO);
    }
}
