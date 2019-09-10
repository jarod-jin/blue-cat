package cn.jarod.bluecat.auth.service.impl;

import cn.jarod.bluecat.auth.BlueCatAuthApplicationTest;
import cn.jarod.bluecat.auth.entity.AuthorityInfoDO;
import cn.jarod.bluecat.auth.model.ValidAuthBO;
import cn.jarod.bluecat.auth.service.ICredentialService;
import cn.jarod.bluecat.core.exception.BaseException;
import cn.jarod.bluecat.core.model.auth.AuthRegisterDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @auther jarod.jin 2019/9/10
 */
class CredentialServiceTest extends BlueCatAuthApplicationTest {


    @Autowired
    ICredentialService iCredentialService;


    @Test
    @DisplayName("内容为null")
    void validAuthority_no_text() {
        ValidAuthBO authBO = new ValidAuthBO();
        authBO = iCredentialService.validAuthority(authBO);
        assertFalse(authBO.isCanAuthority());
        assertFalse(authBO.isCanTel());
        assertFalse(authBO.isCanEmail());
    }

    @Test
    @DisplayName("内容为空白")
    void validAuthority_empty_text() {
        ValidAuthBO authBO = new ValidAuthBO();
        authBO.setAuthority("");
        authBO.setTel("");
        authBO.setEmail("");
        authBO = iCredentialService.validAuthority(authBO);
        assertFalse(authBO.isCanAuthority());
        assertFalse(authBO.isCanTel());
        assertFalse(authBO.isCanEmail());
    }

    @Test
    @DisplayName("创建账号_没有电话和邮箱")
    void registerAuthority_admin() {
        AuthRegisterDTO credDTO = new AuthRegisterDTO();
        credDTO.setAuthority("junit_test");
        credDTO.setPassword("junit_test");
        try{
            AuthorityInfoDO authDO = iCredentialService.registerAuthority(credDTO);
        }catch (BaseException e){
            assertEquals("电话和邮箱不能同时为空",e.getErrorMessage());
        }
    }

    @Test
    void modifyAuthority() {
    }
}