package cn.jarod.bluecat.auth.service;

import cn.jarod.bluecat.auth.entity.UserInfoDO;
import cn.jarod.bluecat.auth.model.dto.SignUpDTO;
import cn.jarod.bluecat.auth.model.bo.*;
import cn.jarod.bluecat.auth.model.dto.ValidSignUpDTO;
import cn.jarod.bluecat.core.model.auth.UserInfoDTO;

/**
 * @author jarod.jin 2019/9/9
 */
public interface ICredentialService {

    /**
     * 注册一个账号
     * @param credDTO 注册用户对象
     * @return UserInfoDO
     */
    UserInfoDO registerUser(SignUpDTO credDTO);

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
    UserInfoDO modifyUser(UpdateUserBO authBO);

    /**
     * 注册电话 邮箱 校验
     * @param authDTO 注册校验对象
     * @return ValidSignUpDTO
     */
    ValidSignUpDTO validSignUp(ValidSignUpDTO authDTO);

    /**
     * 修改用户密码
     * @param credBO 密码修改对象
     */
    void modifyPassword(UpdateCredBO credBO);


    /**
     * 登录验证
     * @param signIn 登录名
     * @param password 密码
     * @return boolean
     */
    boolean validCredential(String signIn, String password);

    /**
     * 根据登录用户名查询用户基本信息
     * @param name 用户名
     * @return UserInfoDTO
     */
    UserInfoDTO findUserInfo(String name);

}
