package cn.jarod.bluecat.auth.service;

import cn.jarod.bluecat.auth.entity.AuthorityInfoDO;
import cn.jarod.bluecat.auth.model.dto.*;

/**
 * @auther jarod.jin 2019/9/9
 */
public interface ICredentialService {

    AuthorityInfoDO registerAuthority(AuthRegisterDTO credDTO);

    void deleteAuthority(AuthRegisterDTO authDTO);

    AuthorityInfoDO modifyAuthority(AuthModifyDTO authDTO);

    ValidAuthDTO validAuthority(ValidAuthDTO authBO);

    void modifyPassword(CredModifyDTO credDTO);

    boolean validCredential(String signIn, String password);

    AuthorityDTO findAuthorities(String name);

}
