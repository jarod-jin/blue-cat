package cn.jarod.bluecat.auth.service.impl;

import cn.jarod.bluecat.auth.entity.AuthorityInfoDO;
import cn.jarod.bluecat.auth.repository.AuthorityInfoRepository;
import cn.jarod.bluecat.auth.service.ICredentialService;
import cn.jarod.bluecat.core.enums.ReturnCode;
import cn.jarod.bluecat.core.model.ResultDTO;
import cn.jarod.bluecat.core.model.auth.AuthRegisterDTO;
import cn.jarod.bluecat.core.model.auth.AuthorityDTO;
import cn.jarod.bluecat.core.utils.BeanHelperUtil;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.Valid;

/**
 * @auther jarod.jin 2019/9/9
 */
public class CredentialService implements ICredentialService {

    @Autowired
    private AuthorityInfoRepository authorityInfoRepository;

    @Override
    public ResultDTO registerAuthority(@Valid AuthRegisterDTO credDTO) {
        AuthorityInfoDO auth = BeanHelperUtil.getCopyBean(credDTO, AuthorityInfoDO.class);
        if (!credDTO.hasTelOrEmail())
            return new ResultDTO(ReturnCode.S400);
        return null;
    }


    @Override
    public ResultDTO validAuthority(AuthRegisterDTO credDTO) {
        AuthorityInfoDO auth = BeanHelperUtil.getCopyBean(credDTO, AuthorityInfoDO.class);
        if (credDTO.validTel()){

        }
        if (credDTO.validTel()){

        }
        return new ResultDTO(ReturnCode.S400);
    }


    @Override
    public ResultDTO modifyAuthority(@Valid AuthorityDTO credDTO) {
        AuthorityInfoDO auth = BeanHelperUtil.getCopyBean(credDTO, AuthorityInfoDO.class);
        return null;
    }

}
