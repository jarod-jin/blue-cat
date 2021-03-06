package cn.jarod.bluecat.access.user.service;

import cn.jarod.bluecat.access.BlueCatAccessApplicationTest;
import cn.jarod.bluecat.access.user.pojo.CrudUserBO;
import cn.jarod.bluecat.access.user.pojo.UpdateCredBO;
import cn.jarod.bluecat.access.user.pojo.UpdateCredDTO;
import cn.jarod.bluecat.core.api.exception.BaseException;
import cn.jarod.bluecat.core.security.pojo.UserDetailDO;
import cn.jarod.bluecat.core.common.utils.BeanHelperUtil;
import cn.jarod.bluecat.access.user.pojo.entity.UserInfoPO;
import cn.jarod.bluecat.access.enums.SignType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author jarod.jin 2019/9/10
 */
class CredentialServiceTest extends BlueCatAccessApplicationTest {


    @Autowired
    private UserService userInfoService;

    @Autowired
    PasswordEncoder passwordEncoder;

    private CrudUserBO admin;

    private CrudUserBO tmp;

    private CrudUserBO userDTO;

    private UpdateCredDTO credBO;


    @BeforeEach
    void setUp()  {
        //注册数据
        admin = new CrudUserBO();
        admin.setUsername("admin");
        admin.setCredentialType(1);
        admin.setEmail("admin@blue-cat.com");

        userDTO = new CrudUserBO();
        userDTO.setUsername("admin");

        tmp = new CrudUserBO();
        tmp.setUsername("tmp");
        tmp.setCredentialType(1);

        //密码修改数据
        credBO = new UpdateCredDTO();
        credBO.setUsername("admin");
        credBO.setCurrentPassword("admin1234");
        credBO.setModifiedPassword("admin@1234");

    }

    @AfterEach
    void tearDown()  {
        admin = null;
        tmp = null;
        userDTO = null;
        credBO = null;
    }


    @Test
    @DisplayName("校验手机，邮箱，用户名为空白")
    void validEmailEmpty() {
        assertAll("检验返回结果",
                ()->  assertFalse(userInfoService.validSignUp(SignType.tel,"")),
                ()->  assertFalse(userInfoService.validSignUp(SignType.email,""))
        );
    }


    @Test
    @DisplayName("校验Authority为admin")
    void validAuthorityAdmin() {
        assertAll("检验返回结果",
                ()->  assertTrue(userInfoService.validSignUp(SignType.tel,"18158105518")),
                ()->  assertFalse(userInfoService.validSignUp(SignType.email,""))
        );
    }

    @Test
    @DisplayName("创建测试账号")
    void register4JunitTest() {
        UserInfoPO userInfoPO = userInfoService.registerUser(tmp,"admin1234");
        assertNotNull(userInfoPO.getId());
        userInfoService.deleteUser(BeanHelperUtil.createCopyBean(userInfoPO, UserDetailDO.class));
    }

    @Test
    @DisplayName("创建管理员测试账号")
    void registerUserAdmin() {
        UserInfoPO UserInfoPO = userInfoService.registerUser(admin,"admin1234");
        assertNotNull(UserInfoPO.getId());
    }

    @Test
    @DisplayName("修改admin账号数据")
    void modifyUser4Admin() {
        userDTO.setEmail("kira277@163.com");
        userDTO.setNickname("超级管理员");
        UserInfoPO userDO = userInfoService.modifyUser(userDTO);
        assertEquals(userDTO.getNickname(),userDO.getNickname());
    }

    @Test
    @DisplayName("修改密码时原始密码错误")
    void modifyPasswordOne() {
        credBO.setCurrentPassword("123456");
        assertThrows(BaseException.class, ()-> userInfoService.modifyPassword(new UpdateCredBO(credBO)),"原密码错误");
    }


    @Test
    @DisplayName("修改密码时密码和前次相同")
    void modifyPasswordTwo() {
        credBO.setModifiedPassword("admin123");
        assertThrows(BaseException.class, ()-> userInfoService.modifyPassword(new UpdateCredBO(credBO)),"密码不能和前3次相同");
    }


    @Test
    @DisplayName("修改密码成功")
    void modifyPasswordThree() {
        try{
            String pwd = passwordEncoder.encode("111111");
            System.out.println(pwd);
        }catch (BaseException e){
            fail();
        }

    }


    @Test
    @DisplayName("打印加密串")
    void printEncode() {
        PasswordEncoder passwordEncoder=  PasswordEncoderFactories.createDelegatingPasswordEncoder();
        System.out.println(passwordEncoder.encode("B0123456!AbC"));
        System.out.println(passwordEncoder.encode("0123456"));
    }

}