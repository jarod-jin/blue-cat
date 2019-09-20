package cn.jarod.bluecat.auth.service;

import cn.jarod.bluecat.auth.entity.AuthorityInfoDO;
import cn.jarod.bluecat.auth.entity.CredentialDO;
import cn.jarod.bluecat.auth.model.ValidAuthBO;
import cn.jarod.bluecat.core.model.auth.AuthRegisterDTO;
import cn.jarod.bluecat.core.model.auth.AuthorityDTO;

/**
 * @auther jarod.jin 2019/9/9
 */
public interface ICredentialService {

    AuthorityInfoDO registerAuthority(AuthRegisterDTO credDTO);

    void deleteAuthority(AuthRegisterDTO authDTO);

    AuthorityInfoDO modifyAuthority(AuthorityDTO authDTO);

    ValidAuthBO validAuthority(ValidAuthBO authBO);

    void modifyPassword(CredentialDO credDO);

}
