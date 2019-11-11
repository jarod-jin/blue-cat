package cn.jarod.bluecat.auth.service;

import cn.jarod.bluecat.auth.entity.UserInfoDO;
import cn.jarod.bluecat.auth.model.dto.SignUpDTO;
import cn.jarod.bluecat.auth.model.bo.*;
import cn.jarod.bluecat.auth.model.dto.ValidSignUpDTO;

/**
 * @auther jarod.jin 2019/9/9
 */
public interface ICredentialService {

    UserInfoDO registerUser(SignUpDTO credDTO);

    void deleteUser(SaveUserInfoBO authBO);

    UserInfoDO modifyUser(UpdateUserBO authBO);

    ValidSignUpDTO validSignUp(ValidSignUpDTO authDTO);

    void modifyPassword(UpdateCredBO credBO);

    boolean validCredential(String signIn, String password);

    SaveUserInfoBO findUserInfo(String name);

}
