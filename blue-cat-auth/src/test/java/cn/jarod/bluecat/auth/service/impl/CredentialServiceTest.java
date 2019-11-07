package cn.jarod.bluecat.auth.service.impl;

import cn.jarod.bluecat.auth.BlueCatAuthApplicationTest;
import cn.jarod.bluecat.auth.entity.UserInfoDO;
import cn.jarod.bluecat.auth.model.bo.CredModifyBO;
import cn.jarod.bluecat.auth.model.bo.SignUpBO;
import cn.jarod.bluecat.auth.model.dto.CredModifyDTO;
import cn.jarod.bluecat.auth.model.dto.UserInfoDTO;
import cn.jarod.bluecat.auth.model.dto.UserModifyDTO;
import cn.jarod.bluecat.auth.model.dto.ValidSignUpDTO;
import cn.jarod.bluecat.auth.service.ICredentialService;
import cn.jarod.bluecat.core.exception.BaseException;
import cn.jarod.bluecat.core.utils.BeanHelperUtil;
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
    private ICredentialService credentialService;

    private SignUpBO upBO;

    private UserModifyDTO userDTO;

    private ValidSignUpDTO upDTO;

    private CredModifyBO credBO;


    @BeforeEach
    void setUp()  {
        //注册数据
        upBO = new SignUpBO();
        upBO.setLoginName("junit_test");
        upBO.setPassword("junit_test");
        userDTO = new UserModifyDTO();
        userDTO.setUsername("admin");
        //密码修改数据
        credBO = new CredModifyBO();
        credBO.setUsername("admin");
        credBO.setCurrentPassword("admin123");
        credBO.setModifiedPassword("admin123");

        upDTO = new ValidSignUpDTO();
    }

    @AfterEach
    void tearDown()  {
        upBO = null;
        userDTO = null;
        upDTO = null;
        credBO = null;
    }



    @Test
    @DisplayName("校验内容为null")
    void validAuthorityNullText() {
        upDTO = credentialService.validSignUp(upDTO);
        assertAll("检验返回结果",
                ()->  assertFalse(upDTO.isCanUsername()),
                ()->  assertFalse(upDTO.isCanTel()),
                ()->  assertFalse(upDTO.isCanEmail())
        );
    }

    @Test
    @DisplayName("校验内容为空白")
    void validAuthorityEmptyText() {
        upDTO.setUsername("");
        upDTO.setTel("");
        upDTO.setEmail("");
        upDTO = credentialService.validSignUp(upDTO);
        assertAll("检验返回结果",
                ()->  assertFalse(upDTO.isCanUsername()),
                ()->  assertFalse(upDTO.isCanTel()),
                ()->  assertFalse(upDTO.isCanEmail())
        );
    }


    @Test
    @DisplayName("校验Authority为admin")
    void validAuthorityAdmin() {
        upDTO.setUsername("admin");
        upDTO = credentialService.validSignUp(upDTO);
        assertAll("检验返回结果",
                ()->  assertFalse(upDTO.isCanUsername()),
                ()->  assertFalse(upDTO.isCanTel()),
                ()->  assertFalse(upDTO.isCanEmail())
        );
    }

    @Test
    @DisplayName("校验Authority为junit_test")
    void validAuthorityJunit() {
        upDTO.setUsername("junit_test");
        upDTO = credentialService.validSignUp(upDTO);
        assertAll("检验返回结果",
                ()->  assertTrue(upDTO.isCanUsername()),
                ()->  assertFalse(upDTO.isCanTel()),
                ()->  assertFalse(upDTO.isCanEmail())
        );

    }

    @Test
    @DisplayName("创建账号时没有电话和邮箱")
    void registerAuthorityNoParams() {
        try{
            credentialService.registerUser(upBO);
        }catch (BaseException e){
            assertEquals("电话和邮箱不能同时为空",e.getErrorMessage());
        }
    }


    @Test
    @DisplayName("创建测试账号")
    void registerAuthorityJunitTest() {
        upBO.setTel("13105818757");
        UserInfoDO userInfoDO = credentialService.registerUser(upBO);
        assertNotNull(userInfoDO.getId());
        credentialService.deleteUser(BeanHelperUtil.createCopyBean(userInfoDO, UserInfoDTO.class));
    }

    @Test
    @DisplayName("修改admin账号数据")
    void modifyAuthorityAdmin() {
        userDTO.setEmail("kira277@163.com");
        userDTO.setNickname("超级管理员");
        UserInfoDO userDO = credentialService.modifyUser(userDTO);
        assertEquals(userDTO.getNickname(),userDO.getNickname());
    }

    @Test
    @DisplayName("修改密码时原始密码错误")
    void modifyPasswordOne() {
        credBO.setCurrentPassword("123456");
        try{
            credentialService.modifyPassword(new CredModifyDTO(credBO));
        }catch (BaseException e){
            assertEquals("原密码错误",e.getErrorMessage());
        }
    }


    @Test
    @DisplayName("修改密码时密码和前次相同")
    void modifyPasswordTwo() {
        credBO.setModifiedPassword("admin12");
        try{
            credentialService.modifyPassword(new CredModifyDTO(credBO));
        }catch (BaseException e){
            assertEquals("密码不能和前3次相同",e.getErrorMessage());
        }
    }


    @Test
    @DisplayName("修改密码成功")
    void modifyPasswordThree() {
        try{
            credentialService.modifyPassword(new CredModifyDTO(credBO));
        }catch (BaseException e){
            fail();
        }

    }


    @Test
    @DisplayName("校验登录名密码")
    void validCredential() {
        assertTrue(credentialService.validCredential(credBO.getUsername(),credBO.getModifiedPassword()));
    }


}