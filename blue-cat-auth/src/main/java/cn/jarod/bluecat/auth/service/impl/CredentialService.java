package cn.jarod.bluecat.auth.service.impl;

import cn.jarod.bluecat.auth.entity.AuthorityInfoDO;
import cn.jarod.bluecat.auth.entity.CredHistoryDO;
import cn.jarod.bluecat.auth.entity.CredentialDO;
import cn.jarod.bluecat.auth.model.dto.*;
import cn.jarod.bluecat.auth.repository.AuthorityInfoRepository;
import cn.jarod.bluecat.auth.repository.CredHistoryRepository;
import cn.jarod.bluecat.auth.repository.CredentialRepository;
import cn.jarod.bluecat.auth.service.ICredentialService;
import cn.jarod.bluecat.core.annotation.TimeDiff;
import cn.jarod.bluecat.core.enums.ReturnCode;
import cn.jarod.bluecat.core.exception.BaseException;
import cn.jarod.bluecat.core.utils.BeanHelperUtil;
import cn.jarod.bluecat.core.utils.CommonUtil;
import cn.jarod.bluecat.core.utils.EncryptUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Comparator;
import java.util.List;

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

    @Autowired
    private CredHistoryRepository credHistoryRepository;

    @Value("${security.password.number:3}")
    private Integer passNumber;

    /**
     * 注册账号
     * @param authDTO
     * @return
     */
    @Override
    @TimeDiff
    @Transactional(rollbackFor = Exception.class)
    public AuthorityInfoDO registerAuthority(AuthRegisterDTO authDTO) {
        AuthorityInfoDO authDO = BeanHelperUtil.createCopyBean(authDTO, AuthorityInfoDO.class);
        if (!authDTO.hasTelOrEmail())
            throw new BaseException(ReturnCode.S400.getCode(), "电话和邮箱不能同时为空");
        authDO.setCreator(authDO.getAuthority());
        authDO.setModifier(authDO.getAuthority());
        authDO.setCredentialType(authDTO.getCredentialType());
        authDO = authorityInfoRepository.save(authDO);
        CredentialDO credDO = new CredentialDO();
        credDO.setAuthority(authDTO.getAuthority());
        credDO.setPassword(EncryptUtil.stringEncodeSHA256(authDTO.getPassword()));
        credDO.setCreator(authDO.getAuthority());
        credDO.setModifier(authDO.getAuthority());
        credentialRepository.save(credDO);
        CredHistoryDO chDO = new CredHistoryDO(authDTO.getAuthority(),EncryptUtil.stringEncodeSHA256(authDTO.getPassword()));
        chDO.setCreator(authDTO.getAuthority());
        credHistoryRepository.save(chDO);
        return authDO;
    }

    /**
     * 删除账号
     * @param authDTO
     * @return
     */
    @TimeDiff
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAuthority(AuthRegisterDTO authDTO) {
        BaseException baseException = new BaseException(ReturnCode.S400.getCode(), "找不到该用户");
        authorityInfoRepository.delete(authorityInfoRepository.findByAuthority(authDTO.getAuthority()).orElseThrow(() -> baseException));
        credentialRepository.delete(credentialRepository.findByAuthority(authDTO.getAuthority()).orElseThrow(()-> baseException));
        credHistoryRepository.deleteAll(credHistoryRepository.findAllByAuthority(authDTO.getAuthority()));
    }

    /**
     * 注册账号前校验
     * @param authBO
     * @return
     */
    @Override
    @TimeDiff
    @Transactional(readOnly = true)
    public ValidAuthDTO validAuthority(ValidAuthDTO authBO) {
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
    @TimeDiff
    @Transactional(rollbackFor = Exception.class)
    public AuthorityInfoDO modifyAuthority(AuthModifyDTO authDTO) {
        AuthorityInfoDO target = BeanHelperUtil.createCopyBean(authDTO, AuthorityInfoDO.class);
        authorityInfoRepository.findByAuthority(authDTO.getAuthority()).ifPresent(s -> BeanHelperUtil.copyNullProperties(s, target));
        return authorityInfoRepository.save(target);
    }


    /**
     * 修改密码
     * @param credDTO
     */
    @Override
    @TimeDiff
    @Transactional(rollbackFor = Exception.class)
    public void modifyPassword(CredModifyDTO credDTO) {
        credentialRepository.findByAuthority(credDTO.getAuthority()).ifPresent(
            c -> {
                if (!c.getPassword().equals(credDTO.getCurrentPassword()))
                    throw new BaseException(ReturnCode.S400.getCode(),"原密码错误");
                CredHistoryDO chDO = new CredHistoryDO(credDTO.getAuthority(),credDTO.getModifiedPassword());
                if (credHistoryRepository.exists(Example.of(chDO)))
                    throw new BaseException(ReturnCode.S400.getCode(),"密码不能和前"+ passNumber +"次相同");
                c.setPassword(credDTO.getModifiedPassword());
                chDO.setCreator(credDTO.getAuthority());
                credHistoryRepository.save(chDO);
                handleCredPassword(chDO);
            }
        );
    }


    /**
     * 如果数据库密码数量超过设定值那么最早的密码被删除
     * @param chDO
     */
    private void handleCredPassword(CredHistoryDO chDO) {
        List<CredHistoryDO> list = credHistoryRepository.findAllByAuthority(chDO.getAuthority());
        if (list.size()> passNumber)
            list.stream().min(Comparator.comparing(CredHistoryDO::getCreateDate)).ifPresent(e -> credHistoryRepository.delete(e));
    }

    /**
     * 登录密码校验
     * @param signIn
     * @return
     */
    @Override
    public boolean validCredential(String signIn, String password) {
        CredentialDO credentialDO = new CredentialDO();
        credentialDO.setAuthority(signIn);
        credentialDO.setPassword(password);
        return credentialRepository.exists(Example.of(credentialDO));
    }

    @Override
    public AuthorityDTO findAuthorities(String name) {
        return null;
    }
}
