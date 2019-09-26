package cn.jarod.bluecat.auth.service.impl;

import cn.jarod.bluecat.auth.BlueCatAuthApplicationTest;
import cn.jarod.bluecat.auth.entity.AuthorityInfoDO;
import cn.jarod.bluecat.auth.model.bo.CredModifyBO;
import cn.jarod.bluecat.auth.model.bo.SignInBO;
import cn.jarod.bluecat.auth.model.dto.CredModifyDTO;
import cn.jarod.bluecat.auth.model.dto.SignInDTO;
import cn.jarod.bluecat.auth.model.dto.ValidAuthDTO;
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
    private ICredentialService iCredentialService;

    private AuthRegisterDTO authRegDTO;

    private AuthorityDTO authDTO;

    private ValidAuthDTO authBO;

    private CredModifyBO credBO;

    private SignInBO signInBO;

    @BeforeEach
    void setUp()  {
        //注册数据
        authRegDTO = new AuthRegisterDTO();
        authRegDTO.setAuthority("junit_test");
        authRegDTO.setPassword("junit_test");
        authDTO = new AuthorityDTO();
        authDTO.setAuthority("admin");
        //密码修改数据
        credBO = new CredModifyBO();
        credBO.setAuthority("admin");
        credBO.setCurrentPassword("admin123");
        credBO.setModifiedPassword("admin123");
        //密码校验数据
        signInBO = new SignInBO();
        signInBO.setAuthority(credBO.getAuthority());
        signInBO.setPassword(credBO.getModifiedPassword());

        authBO = new ValidAuthDTO();
    }

    @AfterEach
    void tearDown()  {
        authRegDTO = null;
        authDTO = null;
        authBO = null;
        credBO = null;
        signInBO = null;
    }



    @Test
    @DisplayName("校验内容为null")
    void validAuthorityNullText() {
        authBO = iCredentialService.validAuthority(authBO);
        assertFalse(authBO.isCanAuthority());
        assertFalse(authBO.isCanTel());
        assertFalse(authBO.isCanEmail());
    }

    @Test
    @DisplayName("校验内容为空白")
    void validAuthorityEmptyText() {
        authBO.setAuthority("");
        authBO.setTel("");
        authBO.setEmail("");
        authBO = iCredentialService.validAuthority(authBO);
        assertFalse(authBO.isCanAuthority());
        assertFalse(authBO.isCanTel());
        assertFalse(authBO.isCanEmail());
    }


    @Test
    @DisplayName("校验Authority为admin")
    void validAuthorityAdmin() {
        authBO.setAuthority("admin");
        authBO = iCredentialService.validAuthority(authBO);
        assertFalse(authBO.isCanAuthority());
        assertFalse(authBO.isCanTel());
        assertFalse(authBO.isCanEmail());
    }

    @Test
    @DisplayName("校验Authority为junit_test")
    void validAuthorityJunit() {
        authBO.setAuthority("junit_test");
        authBO = iCredentialService.validAuthority(authBO);
        assertTrue(authBO.isCanAuthority());
        assertFalse(authBO.isCanTel());
        assertFalse(authBO.isCanEmail());
    }

    @Test
    @DisplayName("创建账号时没有电话和邮箱")
    void registerAuthorityNoParams() {
        try{
            iCredentialService.registerAuthority(authRegDTO);
        }catch (BaseException e){
            assertEquals("电话和邮箱不能同时为空",e.getErrorMessage());
        }
    }


    @Test
    @DisplayName("创建测试账号")
    void registerAuthorityJunitTest() {
        authRegDTO.setTel("13105818757");
        AuthorityInfoDO authDO = iCredentialService.registerAuthority(authRegDTO);
        assertNotNull(authDO.getId());
        iCredentialService.deleteAuthority(authRegDTO);
    }

    @Test
    @DisplayName("修改admin账号数据")
    void modifyAuthorityAdmin() {
        authDTO.setEmail("kira277@163.com");
        authDTO.setNickname("超级管理员");
        AuthorityInfoDO authDO = iCredentialService.modifyAuthority(authDTO);
        assertEquals(authDTO.getNickname(),authDO.getNickname());
    }

    @Test
    @DisplayName("修改密码时原始密码错误")
    void modifyPasswordOne() {
        credBO.setCurrentPassword("123456");
        try{
            iCredentialService.modifyPassword(new CredModifyDTO(credBO));
        }catch (BaseException e){
            assertEquals("原密码错误",e.getErrorMessage());
        }
    }


    @Test
    @DisplayName("修改密码时密码和前次相同")
    void modifyPasswordTwo() {
        credBO.setModifiedPassword("admin12");
        try{
            iCredentialService.modifyPassword(new CredModifyDTO(credBO));
        }catch (BaseException e){
            assertEquals("密码不能和前3次相同",e.getErrorMessage());
        }
    }


    @Test
    @DisplayName("修改密码成功")
    void modifyPasswordThree() {
        try{
            iCredentialService.modifyPassword(new CredModifyDTO(credBO));
        }catch (BaseException e){
            fail();
        }

    }


    @Test
    @DisplayName("校验登录名密码")
    void validCredential() {
        assertTrue(iCredentialService.validCredential(new SignInDTO(signInBO)));
    }


}