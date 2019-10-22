package cn.jarod.bluecat.auth.service.impl;

import cn.jarod.bluecat.auth.entity.CredHistoryDO;
import cn.jarod.bluecat.auth.entity.CredentialDO;
import cn.jarod.bluecat.auth.entity.UserInfoDO;
import cn.jarod.bluecat.auth.model.bo.SignUpBO;
import cn.jarod.bluecat.auth.model.dto.CredModifyDTO;
import cn.jarod.bluecat.auth.model.dto.UserInfoDTO;
import cn.jarod.bluecat.auth.model.dto.UserModifyDTO;
import cn.jarod.bluecat.auth.model.dto.ValidSignUpDTO;
import cn.jarod.bluecat.auth.repository.CredHistoryRepository;
import cn.jarod.bluecat.auth.repository.CredentialRepository;
import cn.jarod.bluecat.auth.repository.UserInfoRepository;
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
    private UserInfoRepository userInfoRepository;

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
    public UserInfoDO registerUser(SignUpBO authDTO) {
        UserInfoDO authDO = BeanHelperUtil.createCopyBean(authDTO, UserInfoDO.class);
        if (!authDTO.hasTelOrEmail())
            throw new BaseException(ReturnCode.S400.getCode(), "电话和邮箱不能同时为空");
        authDO.setCreator(authDTO.getLoginName());
        authDO.setModifier(authDTO.getLoginName());
        authDO.setCredentialType(authDTO.getCredentialType());
        authDO = userInfoRepository.save(authDO);
        CredentialDO credDO = new CredentialDO();
        credDO.setUsername(authDO.getUsername());
        credDO.setPassword(EncryptUtil.stringEncodeSHA256(authDTO.getPassword()));
        credDO.setCreator(authDO.getUsername());
        credDO.setModifier(authDO.getUsername());
        credentialRepository.save(credDO);
        CredHistoryDO chDO = new CredHistoryDO(authDO.getUsername(),EncryptUtil.stringEncodeSHA256(authDTO.getPassword()));
        chDO.setCreator(authDO.getUsername());
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
    public void deleteUser(UserInfoDTO authDTO) {
        BaseException baseException = new BaseException(ReturnCode.S400.getCode(), "找不到该用户");
        userInfoRepository.delete(userInfoRepository.findByUsername(authDTO.getUsername()).orElseThrow(() -> baseException));
        credentialRepository.delete(credentialRepository.findByAuthority(authDTO.getUsername()).orElseThrow(()-> baseException));
        credHistoryRepository.deleteAll(credHistoryRepository.findAllByAuthority(authDTO.getUsername()));
    }

    /**
     * 注册账号前校验
     * @param authBO
     * @return
     */
    @Override
    @TimeDiff
    @Transactional(readOnly = true)
    public ValidSignUpDTO validSignUp(ValidSignUpDTO authBO) {
        UserInfoDO auth;
        if (StringUtils.hasText(authBO.getUsername())){
            auth = new UserInfoDO();
            auth.setUsername(authBO.getUsername());
            authBO.setCanUsername(!userInfoRepository.exists(Example.of(auth)));
        }
        if (StringUtils.hasText(authBO.getTel())){
            auth = new UserInfoDO();
            auth.setTel(authBO.getTel());
            authBO.setCanTel(CommonUtil.validTel(auth.getTel())  && !userInfoRepository.exists(Example.of(auth)));
        }
        if (StringUtils.hasText(authBO.getEmail())){
            auth = new UserInfoDO();
            auth.setEmail(authBO.getEmail());
            authBO.setCanEmail(CommonUtil.validEmail(auth.getEmail())  && !userInfoRepository.exists(Example.of(auth)));
        }
        return authBO;
    }


    @Override
    @TimeDiff
    @Transactional(rollbackFor = Exception.class)
    public UserInfoDO modifyUser(UserModifyDTO authDTO) {
        UserInfoDO target = BeanHelperUtil.createCopyBean(authDTO, UserInfoDO.class);
        userInfoRepository.findByUsername(authDTO.getUsername()).ifPresent(s -> BeanHelperUtil.copyNullProperties(s, target));
        return userInfoRepository.save(target);
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
        List<CredHistoryDO> list = credHistoryRepository.findAllByAuthority(chDO.getUsername());
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
        credentialDO.setUsername(signIn);
        credentialDO.setPassword(password);
        return credentialRepository.exists(Example.of(credentialDO));
    }

    @Override
    public UserInfoDTO findAuthorities(String name) {
        return null;
    }
}
