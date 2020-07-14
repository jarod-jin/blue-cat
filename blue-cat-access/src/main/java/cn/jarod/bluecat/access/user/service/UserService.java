package cn.jarod.bluecat.access.user.service;

import cn.jarod.bluecat.access.user.pojo.entity.UserInfoPO;
import cn.jarod.bluecat.access.enums.SignType;
import cn.jarod.bluecat.access.user.pojo.CrudUserBO;
import cn.jarod.bluecat.access.user.pojo.UpdateCredBO;
import cn.jarod.bluecat.core.model.auth.UserDetailDTO;

import javax.validation.constraints.NotBlank;
import java.util.Map;

/**
 * @author jarod.jin 2019/9/9
 */
public interface UserService {

    /**
     * 注册一个账号
     * @param userBO 注册用户
     * @param clearPwd 明文密码
     * @return UserInfoPO
     */
    UserInfoPO registerUser(CrudUserBO userBO, String clearPwd) ;

    /**
     * 删除用户
     * @param authBO 删除用户
     */
    void deleteUser(UserDetailDTO authBO);

    /**
     * 修改用户基本信息
     * @param authBO 修改用户
     * @return UserInfoPO
     */
    UserInfoPO modifyUser(CrudUserBO authBO);

    /**
     * 注册电话 邮箱 校验
     * @param type 注册校验类型
     * @param text 注册校验内容
     * @return ValidSignUpDTO
     */
    Boolean validSignUp(SignType type, String text);

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
    UserInfoPO findUserByUsername(String username);

    /**
     * 根据登录用户名查询用户基本信息
     * @param name 用户名
     * @return UserInfoDTO
     */
    UserDetailDTO findUserInfo(String name);

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
    boolean setUserInfo2Cache(final UserDetailDTO userInfoDTO);

    /**
     * 获取刷新Token
     * @param token
     * @return Map
     */
    Map<String,String> refresh(String token);
}
