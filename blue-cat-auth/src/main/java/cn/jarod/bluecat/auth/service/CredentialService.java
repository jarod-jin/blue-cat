package cn.jarod.bluecat.auth.service;

import cn.jarod.bluecat.auth.entity.CredentialDO;
import cn.jarod.bluecat.auth.entity.UserInfoDO;
import cn.jarod.bluecat.auth.model.dto.SignUpDTO;
import cn.jarod.bluecat.auth.model.bo.*;
import cn.jarod.bluecat.core.model.auth.UserInfoDTO;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Optional;

/**
 * @author jarod.jin 2019/9/9
 */
public interface CredentialService {

    /**
     * 注册一个账号
     * @param userBO 注册用户
     * @param clearPwd 明文密码
     * @return UserInfoDO
     */
    UserInfoDO registerUser(CrudUserBO userBO, String clearPwd) ;

    /**
     * 删除用户
     * @param authBO 删除用户
     */
    void deleteUser(UserInfoDTO authBO);

    /**
     * 修改用户基本信息
     * @param authBO 修改用户
     * @return UserInfoDO
     */
    UserInfoDO modifyUser(CrudUserBO authBO);

    /**
     * 注册电话 邮箱 校验
     * @param type 注册校验类型
     * @param text 注册校验内容
     * @return ValidSignUpDTO
     */
    Boolean validSignUp(Integer type, String text);

    /**
     * 修改用户密码
     * @param credBO 密码修改对象
     */
    void modifyPassword(UpdateCredBO credBO);


    /**
     * 登录验证
     * @param username 登录名
     * @return boolean
     */
    Optional<CredentialDO> findCredentialByUsername(String username);

    /**
     * 根据登录用户名查询用户基本信息
     * @param name 用户名
     * @return UserInfoDTO
     */
    UserInfoDTO findUserInfo(String name);

    /**
     * 返回一个可用的用户名
     * @param crudUserBO
     * @return username
     */
    String bookUsername(CrudUserBO crudUserBO);


    /**
     * 设置注册信息至redis
     * @param keyword 关键信息
     */
    void setSignInfo2Redis(final @NotBlank String keyword);

    /**
     * 登录时用户信息至redis
     * @param userInfoDTO 用户信息
     * @return boolean
     */
    boolean setUserInfo2Cache(final UserInfoDTO userInfoDTO);
}
