package cn.jarod.bluecat.auth.service.impl;

import cn.jarod.bluecat.auth.entity.CredHistoryDO;
import cn.jarod.bluecat.auth.entity.CredentialDO;
import cn.jarod.bluecat.auth.entity.UserInfoDO;
import cn.jarod.bluecat.auth.model.bo.UpdateCredBO;
import cn.jarod.bluecat.auth.model.bo.UpdateUserBO;
import cn.jarod.bluecat.auth.model.dto.SignUpDTO;
import cn.jarod.bluecat.auth.model.dto.ValidSignUpDTO;
import cn.jarod.bluecat.auth.repository.CredHistoryRepository;
import cn.jarod.bluecat.auth.repository.CredentialRepository;
import cn.jarod.bluecat.auth.repository.UserInfoRepository;
import cn.jarod.bluecat.auth.service.ICredentialService;
import cn.jarod.bluecat.core.annotation.TimeDiff;
import cn.jarod.bluecat.core.enums.ReturnCode;
import cn.jarod.bluecat.core.exception.BaseException;
import cn.jarod.bluecat.core.model.auth.UserInfoDTO;
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
 * @author jarod.jin 2019/9/9
 */
@Slf4j
@Service
public class CredentialService implements ICredentialService {

    private final UserInfoRepository userInfoRepository;

    private final CredentialRepository credentialRepository;

    private final CredHistoryRepository credHistoryRepository;

    @Value("${security.password.number:3}")
    private Integer passNumber;

    @Autowired
    public CredentialService(UserInfoRepository userInfoRepository, CredentialRepository credentialRepository, CredHistoryRepository credHistoryRepository) {
        this.userInfoRepository = userInfoRepository;
        this.credentialRepository = credentialRepository;
        this.credHistoryRepository = credHistoryRepository;
    }

    /**
     * 注册账号
     * @param authDTO
     * @return
     */
    @Override
    @TimeDiff
    @Transactional(rollbackFor = Exception.class)
    public UserInfoDO registerUser(SignUpDTO authDTO) {
        UserInfoDO authDO = BeanHelperUtil.createCopyBean(authDTO, UserInfoDO.class);
        if (!authDTO.hasTelOrEmail()){
            throw new BaseException(ReturnCode.NOT_ACCEPTABLE.getCode(), "电话和邮箱不能同时为空");
        }
        authDO.setCreator(authDTO.getUsername());
        authDO.setModifier(authDTO.getUsername());
        authDO.setCredentialType(authDTO.getCredentialType());
        authDO = userInfoRepository.save(authDO);
        CredentialDO credDO = new CredentialDO();
        credDO.setUsername(authDO.getUsername());
        credDO.setPassword(EncryptUtil.stringEncodeSHA256(authDTO.getPassword()));
        credDO.setCreator(authDO.getUsername());
        credDO.setModifier(authDO.getUsername());
        credentialRepository.save(credDO);
        CredHistoryDO chDO = new CredHistoryDO(authDO.getUsername(),EncryptUtil.stringEncodeSHA256(authDTO.getPassword()),authDO.getUsername());
        credHistoryRepository.save(chDO);
        return authDO;
    }

    /**
     * 删除账号
     * @param authBO
     * @return
     */
    @TimeDiff
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUser(UserInfoDTO authBO) {
        BaseException baseException = new BaseException(ReturnCode.NOT_ACCEPTABLE.getCode(), "找不到该用户");
        userInfoRepository.delete(userInfoRepository.findByUsername(authBO.getUsername()).orElseThrow(() -> baseException));
        credentialRepository.delete(credentialRepository.findByUsername(authBO.getUsername()).orElseThrow(()-> baseException));
        credHistoryRepository.deleteAll(credHistoryRepository.findAllByUsername(authBO.getUsername()));
    }

    /**
     * 注册账号前校验
     * @param authDTO
     * @return
     */
    @Override
    @TimeDiff
    @Transactional(readOnly = true)
    public ValidSignUpDTO validSignUp(ValidSignUpDTO authDTO) {
        UserInfoDO auth;
        if (StringUtils.hasText(authDTO.getUsername())){
            auth = new UserInfoDO();
            auth.setUsername(authDTO.getUsername());
            authDTO.setCanUsername(!userInfoRepository.exists(Example.of(auth)));
        }
        if (StringUtils.hasText(authDTO.getTel())){
            auth = new UserInfoDO();
            auth.setTel(authDTO.getTel());
            authDTO.setCanTel(CommonUtil.validTel(auth.getTel())  && !userInfoRepository.exists(Example.of(auth)));
        }
        if (StringUtils.hasText(authDTO.getEmail())){
            auth = new UserInfoDO();
            auth.setEmail(authDTO.getEmail());
            authDTO.setCanEmail(CommonUtil.validEmail(auth.getEmail())  && !userInfoRepository.exists(Example.of(auth)));
        }
        return authDTO;
    }


    @Override
    @TimeDiff
    @Transactional(rollbackFor = Exception.class)
    public UserInfoDO modifyUser(UpdateUserBO authBO) {
        UserInfoDO target = BeanHelperUtil.createCopyBean(authBO, UserInfoDO.class);
        userInfoRepository.findByUsername(authBO.getUsername()).ifPresent(s -> BeanHelperUtil.copyNullProperties(s, target));
        return userInfoRepository.save(target);
    }


    /**
     * 修改密码
     * @param credBO
     */
    @Override
    @TimeDiff
    @Transactional(rollbackFor = Exception.class)
    public void modifyPassword(UpdateCredBO credBO) {
        credentialRepository.findByUsername(credBO.getAuthority()).ifPresent(
            c -> {
                if (!c.getPassword().equals(credBO.getCurrentPassword())) {
                    throw new BaseException(ReturnCode.NOT_ACCEPTABLE.getCode(),"原密码错误");
                }
                if (credHistoryRepository.existsByUsernameAndPassword(credBO.getAuthority(), credBO.getModifiedPassword())) {
                    throw new BaseException(ReturnCode.NOT_ACCEPTABLE.getCode(),"密码不能和前"+ passNumber +"次相同");
                }
                c.setPassword(credBO.getModifiedPassword());
                CredHistoryDO chDO = new CredHistoryDO(credBO.getAuthority(), credBO.getModifiedPassword(),credBO.getAuthority());
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
        List<CredHistoryDO> list = credHistoryRepository.findAllByUsername(chDO.getUsername());
        if (list.size()> passNumber){
            list.stream().min(Comparator.comparing(CredHistoryDO::getGmtCreate)).ifPresent(credHistoryRepository::delete);
        }
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

    /**
     * 根据用户名查询信息
     * @param name
     * @return
     */
    @Override
    public UserInfoDTO findUserInfo(String name) {
        UserInfoDO userInfoDO = userInfoRepository.findByUsername(name).orElseThrow(()->new BaseException(ReturnCode.INVALID_REQUEST));
        return BeanHelperUtil.createCopyBean(userInfoDO,UserInfoDTO.class);
    }
}
