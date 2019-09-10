package cn.jarod.bluecat.auth.service.impl;

import cn.jarod.bluecat.auth.BlueCatAuthApplicationTest;
import cn.jarod.bluecat.auth.model.ValidAuthBO;
import cn.jarod.bluecat.auth.service.ICredentialService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * @auther jarod.jin 2019/9/10
 */
public class CredentialServiceTest extends BlueCatAuthApplicationTest {


    @Autowired
    ICredentialService iCredentialService;

    @Test
    void registerAuthority() {
    }

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
    void modifyAuthority() {
    }
}