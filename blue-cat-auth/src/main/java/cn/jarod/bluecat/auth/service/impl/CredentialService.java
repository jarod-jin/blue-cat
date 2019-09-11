package cn.jarod.bluecat.auth.service.impl;

import cn.jarod.bluecat.auth.entity.AuthorityInfoDO;
import cn.jarod.bluecat.auth.entity.CredentialDO;
import cn.jarod.bluecat.auth.model.ValidAuthBO;
import cn.jarod.bluecat.auth.repository.AuthorityInfoRepository;
import cn.jarod.bluecat.auth.repository.CredentialRepository;
import cn.jarod.bluecat.auth.service.ICredentialService;
import cn.jarod.bluecat.core.enums.ReturnCode;
import cn.jarod.bluecat.core.exception.BaseException;
import cn.jarod.bluecat.core.model.auth.AuthRegisterDTO;
import cn.jarod.bluecat.core.model.auth.AuthorityDTO;
import cn.jarod.bluecat.core.utils.BeanHelperUtil;
import cn.jarod.bluecat.core.utils.CommonUtil;
import cn.jarod.bluecat.core.utils.EncryptUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * @auther jarod.jin 2019/9/9
 */
@Slf4j
@Service
public class CredentialService implements ICredentialService {

    @Autowired
    private AuthorityInfoRepository authorityInfoRepository;

    @Autowired
    private CredentialRepository credentialRepository;

    /**
     * 注册账号
     * @param authDTO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public AuthorityInfoDO registerAuthority(AuthRegisterDTO authDTO) {
        log.info("registerAuthority 参数为：{}", JSON.toJSONString(authDTO));
        AuthorityInfoDO authDO = BeanHelperUtil.getCopyBean(authDTO, AuthorityInfoDO.class);
        if (!authDTO.hasTelOrEmail())
            throw new BaseException(ReturnCode.S400.getCode(), "电话和邮箱不能同时为空");
        String operator = authDO.getAuthority();
        authDO.setCreator(operator);
        authDO.setModifier(operator);
        authDO = authorityInfoRepository.save(authDO);
        CredentialDO credDO = new CredentialDO();
        credDO.setAuthority(authDTO.getAuthority());
        credDO.setCredentialType(authDTO.getCredentialType());
        credDO.setPassword(EncryptUtil.stringEncodeSHA256(authDTO.getPassword()));
        credDO.setCreator(operator);
        credDO.setModifier(operator);
        credentialRepository.save(credDO);
        return authDO;
    }

    /**
     * 删除账号
     * @param authDTO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAuthority(AuthRegisterDTO authDTO) {
        BaseException baseException = new BaseException(ReturnCode.S400.getCode(), "找不到该用户");
        authorityInfoRepository.delete(authorityInfoRepository.findByAuthority(authDTO.getAuthority()).orElseThrow(() -> baseException));
        credentialRepository.delete(credentialRepository.findByAuthority(authDTO.getAuthority()).orElseThrow(()-> baseException));
    }

    /**
     * 注册账号前校验
     * @param authBO
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public ValidAuthBO validAuthority(ValidAuthBO authBO) {
        log.info("validAuthority 参数为：{}", JSON.toJSONString(authBO));
        AuthorityInfoDO auth;
        if (StringUtils.hasText(authBO.getAuthority())){
            auth = new AuthorityInfoDO();
            auth.setAuthority(authBO.getAuthority());
            authBO.setCanAuthority(!authorityInfoRepository.exists(Example.of(auth)));
        }
        if (StringUtils.hasText(authBO.getTel())){
            auth = new AuthorityInfoDO();
            auth.setTel(authBO.getTel());
            authBO.setCanTel(CommonUtil.validTel(auth.getTel())  && !authorityInfoRepository.exists(Example.of(auth)));
        }
        if (StringUtils.hasText(authBO.getEmail())){
            auth = new AuthorityInfoDO();
            auth.setEmail(authBO.getEmail());
            authBO.setCanEmail(CommonUtil.validEmail(auth.getEmail())  && !authorityInfoRepository.exists(Example.of(auth)));
        }
        return authBO;
    }


    @Override
    @Transactional
    public AuthorityInfoDO modifyAuthority(AuthorityDTO authDTO) {
        log.info("modifyAuthority 参数为：{}", JSON.toJSONString(authDTO));
        AuthorityInfoDO target = BeanHelperUtil.getCopyBean(authDTO, AuthorityInfoDO.class);
        authorityInfoRepository.findByAuthority(authDTO.getAuthority()).ifPresent(
                s -> BeanHelperUtil.copyNullProperties(s, target));
        return authorityInfoRepository.save(target);
    }

}
