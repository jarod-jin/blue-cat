package cn.jarod.bluecat.auth.service;

import cn.jarod.bluecat.auth.entity.AuthorityInfoDO;
import cn.jarod.bluecat.auth.model.dto.ValidAuthDTO;
import cn.jarod.bluecat.auth.model.dto.CredModifyDTO;
import cn.jarod.bluecat.core.model.auth.CredentialsDTO;
import cn.jarod.bluecat.auth.model.dto.AuthRegisterDTO;
import cn.jarod.bluecat.auth.model.dto.AuthorityDTO;

/**
 * @auther jarod.jin 2019/9/9
 */
public interface ICredentialService {

    AuthorityInfoDO registerAuthority(AuthRegisterDTO credDTO);

    void deleteAuthority(AuthRegisterDTO authDTO);

    AuthorityInfoDO modifyAuthority(AuthorityDTO authDTO);

    ValidAuthDTO validAuthority(ValidAuthDTO authBO);

    void modifyPassword(CredModifyDTO credDTO);

    boolean validCredential(CredentialsDTO signIn);

}
