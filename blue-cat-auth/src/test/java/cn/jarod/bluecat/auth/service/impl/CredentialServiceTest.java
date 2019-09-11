package cn.jarod.bluecat.auth.service.impl;

import cn.jarod.bluecat.auth.BlueCatAuthApplicationTest;
import cn.jarod.bluecat.auth.entity.AuthorityInfoDO;
import cn.jarod.bluecat.auth.model.ValidAuthBO;
import cn.jarod.bluecat.auth.service.ICredentialService;
import cn.jarod.bluecat.core.exception.BaseException;
import cn.jarod.bluecat.core.model.auth.AuthRegisterDTO;
import cn.jarod.bluecat.core.model.auth.AuthorityDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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

    AuthRegisterDTO authRegDTO;

    AuthorityDTO authDTO;

    ValidAuthBO authBO;

    @BeforeEach
    public void setUp()  {
        authRegDTO = new AuthRegisterDTO();
        authRegDTO.setAuthority("junit_test");
        authRegDTO.setPassword("junit_test");

        authDTO = new AuthorityDTO();
        authDTO.setAuthority("admin");

        authBO = new ValidAuthBO();

    }

    @AfterEach
    public void tearDown()  {
        authRegDTO = null;
        authDTO = null;
    }



    @Test
    @DisplayName("内容为null")
    void validAuthorityNullText() {
        ValidAuthBO authBO = new ValidAuthBO();
        authBO = iCredentialService.validAuthority(authBO);
        assertFalse(authBO.isCanAuthority());
        assertFalse(authBO.isCanTel());
        assertFalse(authBO.isCanEmail());
    }

    @Test
    @DisplayName("内容为空白")
    void validAuthorityEmptyText() {
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
    void registerAuthorityNoParams() {
        try{
            iCredentialService.registerAuthority(authRegDTO);
        }catch (BaseException e){
            assertEquals("电话和邮箱不能同时为空",e.getErrorMessage());
        }
    }


    @Test
    @DisplayName("创建账号_测试账号")
    void registerAuthorityJunitTest() {
        authRegDTO.setTel("13105818757");
        AuthorityInfoDO authDO = iCredentialService.registerAuthority(authRegDTO);
        assertNotNull(authDO.getId());
        iCredentialService.deleteAuthority(authRegDTO);
    }

    @Test
    @DisplayName("修改admin账号数据")
    void modifyAuthorityAdmin() {
        authDTO.setNickname("金亦冰");
        authDTO.setEmail("kira277@163.com");
        AuthorityInfoDO authDO = iCredentialService.modifyAuthority(authDTO);
        assertEquals(authDTO.getNickname(),authDO.getNickname());
    }


}