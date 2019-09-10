package cn.jarod.bluecat.auth.service;

import cn.jarod.bluecat.auth.entity.AuthorityInfoDO;
import cn.jarod.bluecat.auth.model.ValidAuthBO;
import cn.jarod.bluecat.core.model.ResultDTO;
import cn.jarod.bluecat.core.model.auth.AuthRegisterDTO;
import cn.jarod.bluecat.core.model.auth.AuthorityDTO;

import javax.validation.Valid;

/**
 * @auther jarod.jin 2019/9/9
 */
public interface ICredentialService {

    AuthorityInfoDO registerAuthority(@Valid AuthRegisterDTO credDTO);

    ResultDTO modifyAuthority(@Valid AuthorityDTO credDTO);

    ValidAuthBO validAuthority(ValidAuthBO authBO);
}
