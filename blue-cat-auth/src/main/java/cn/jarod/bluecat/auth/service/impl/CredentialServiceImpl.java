package cn.jarod.bluecat.auth.service.impl;

import cn.jarod.bluecat.auth.entity.CredHistoryDO;
import cn.jarod.bluecat.auth.entity.CredentialDO;
import cn.jarod.bluecat.auth.entity.UserInfoDO;
import cn.jarod.bluecat.auth.model.bo.UpdateCredBO;
import cn.jarod.bluecat.auth.model.bo.CrudUserBO;
import cn.jarod.bluecat.auth.repository.CredHistoryRepository;
import cn.jarod.bluecat.auth.repository.CredentialRepository;
import cn.jarod.bluecat.auth.repository.UserInfoRepository;
import cn.jarod.bluecat.auth.service.CredentialService;
import cn.jarod.bluecat.core.annotation.TimeDiff;
import cn.jarod.bluecat.core.enums.ReturnCode;
import cn.jarod.bluecat.core.exception.BaseException;
import cn.jarod.bluecat.core.model.auth.UserInfoDTO;
import cn.jarod.bluecat.core.utils.BeanHelperUtil;
import cn.jarod.bluecat.core.utils.CommonUtil;
import cn.jarod.bluecat.core.utils.Const;
import cn.jarod.bluecat.core.utils.EncryptUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotBlank;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static cn.jarod.bluecat.core.utils.Const.*;
import static cn.jarod.bluecat.core.utils.Const.TEL;

/**
 * @author jarod.jin 2019/9/9
 */
@Slf4j
@Service
public class CredentialServiceImpl implements CredentialService {

    public static final int TIMEOUT = 3600;
    private final UserInfoRepository userInfoRepository;

    private final CredentialRepository credentialRepository;

    private final CredHistoryRepository credHistoryRepository;

    private final StringRedisTemplate redisTemplate;

    @Value("${security.password.number:3}")
    private Integer passNumber;

    @Autowired
    public CredentialServiceImpl(UserInfoRepository userInfoRepository, CredentialRepository credentialRepository, CredHistoryRepository credHistoryRepository, StringRedisTemplate redisTemplate) {
        this.userInfoRepository = userInfoRepository;
        this.credentialRepository = credentialRepository;
        this.credHistoryRepository = credHistoryRepository;
        this.redisTemplate = redisTemplate;
    }

    /**
     * 注册账号
     * @param userBO
     * @return
     */
    @Override
    @TimeDiff
    @Transactional(rollbackFor = Exception.class)
    public UserInfoDO registerUser(CrudUserBO userBO, String password) {
        UserInfoDO authDO = BeanHelperUtil.createCopyBean(userBO, UserInfoDO.class);
        authDO.setCreator(userBO.getUsername());
        authDO.setModifier(userBO.getUsername());
        authDO = userInfoRepository.save(authDO);
        CredentialDO credDO = new CredentialDO();
        /*密码加密*/
        String pwd = EncryptUtil.stringEncodeSHA256(password);
        credDO.setUsername(authDO.getUsername());
        credDO.setPassword(pwd);
        credDO.setCreator(authDO.getUsername());
        credDO.setModifier(authDO.getUsername());
        credentialRepository.save(credDO);
        CredHistoryDO chDO = new CredHistoryDO(authDO.getUsername(),pwd,authDO.getUsername());
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
     * 注册电话 邮箱 校验
     * @param type 注册校验类型
     * @param text 注册校验内容
     * @return ValidSignUpDTO
     */
    @Override
    @TimeDiff
    @Transactional(readOnly = true)
    public Boolean validSignUp(Integer type, String text) {
        UserInfoDO auth = new UserInfoDO();
        switch (type) {
            case TEL:
                auth.setTel(text.trim());
                return CommonUtil.validTel(auth.getTel())  && !existsKey(text) && !userInfoRepository.exists(Example.of(auth));
            case EMAIL:
                auth.setEmail(text.trim());
                return CommonUtil.validEmail(auth.getEmail())  && !existsKey(text) && !userInfoRepository.exists(Example.of(auth));
            default:
                return false;
        }
    }


    @Override
    @TimeDiff
    @Transactional(rollbackFor = Exception.class)
    public UserInfoDO modifyUser(CrudUserBO authBO) {
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

    @Override
    public String bookUsername(CrudUserBO crudUserBO) {
        StringBuilder buffer = new StringBuilder(EncryptUtil.getRandomCode(4,true));
        buffer.append(REGEX_HYPHEN);
        buffer.append(EncryptUtil.getRandomCode(6,true));
        if (existsKey(buffer.toString()) && userInfoRepository.exists(Example.of(new UserInfoDO(buffer.toString())))){
            throw new BaseException(ReturnCode.NOT_ACCEPTABLE);
        }
        return buffer.toString();
    }

    /**
     * 注册信息在redis
     * @param key
     */
    @Override
    public void setSignInfo2Redis(final @NotBlank String key){
        try {
            ValueOperations<String, String> operations = redisTemplate.opsForValue();
            operations.set(EncryptUtil.stringEncodeMD5(key), key);
            redisTemplate.expire(key, TIMEOUT, TimeUnit.SECONDS);
        }catch (Exception e){
            log.error("写入redis缓存（设置expire存活时间）失败！错误信息为：" + e.getMessage());
            throw new BaseException(ReturnCode.NOT_ACCEPTABLE);
        }
    }

    /**
     * 检查key是否存在
     * @param keyString key
     * @return boolean
     */
    private boolean existsKey(final @NotBlank String keyString){
        try {
            return redisTemplate.hasKey(EncryptUtil.stringEncodeMD5(keyString));
        } catch (Exception e) {
            log.error("判断redis缓存中是否有对应的key失败！错误信息为：" + e.getMessage());
            return false;
        }
    }



}
