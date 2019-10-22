package cn.jarod.bluecat.auth.service;

import cn.jarod.bluecat.auth.entity.UserInfoDO;
import cn.jarod.bluecat.auth.model.bo.SignUpBO;
import cn.jarod.bluecat.auth.model.dto.*;

/**
 * @auther jarod.jin 2019/9/9
 */
public interface ICredentialService {

    UserInfoDO registerUser(SignUpBO credDTO);

    void deleteUser(UserInfoDTO authDTO);

    UserInfoDO modifyUser(UserModifyDTO authDTO);

    ValidSignUpDTO validSignUp(ValidSignUpDTO authBO);

    void modifyPassword(CredModifyDTO credDTO);

    boolean validCredential(String signIn, String password);

    UserInfoDTO findAuthorities(String name);

}
