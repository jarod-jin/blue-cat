package cn.jarod.bluecat.access.user.service;

import cn.jarod.bluecat.access.user.pojo.CrudUserBO;
import cn.jarod.bluecat.access.user.pojo.UpdateCredBO;
import cn.jarod.bluecat.access.user.pojo.entity.CredentialPO;
import cn.jarod.bluecat.access.user.pojo.entity.UserInfoPO;
import cn.jarod.bluecat.access.user.repository.UserInfoRepository;
import cn.jarod.bluecat.access.enums.SignType;
import cn.jarod.bluecat.core.api.util.ApiResultUtil;
import cn.jarod.bluecat.core.common.enums.Constant;
import cn.jarod.bluecat.core.api.enums.ReturnCode;
import cn.jarod.bluecat.core.api.exception.BaseException;
import cn.jarod.bluecat.core.security.pojo.UserDetailDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


/**
 * @author jarod.jin 2019/9/9
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    public static final int LENGTH = 4;

    private final UserInfoRepository userInfoRepository;

    private final StringRedisTemplate redisTemplate;

    @Value("${security.password.number:3}")
    private Integer passNumber;

    /**用户注册时分布式锁的时间：单位秒*/
    @Value("${security.time-out.sign-up:3600}")
    private Integer signUpTimeOut;

    @Value("${security.password.salt-length:10}")
    private Integer saltLength;

    /**用户登录过期时间：单位小时*/
    @Value("${security.time-out.user:24}")
    private Integer userTimeOut;

    public UserServiceImpl(UserInfoRepository userInfoRepository, StringRedisTemplate redisTemplate) {
        this.userInfoRepository = userInfoRepository;
        this.redisTemplate = redisTemplate;
    }

    /**
     * 注册账号
     * @param userBO 注册用户
     * @param clearPwd 明文密码
     * @return UserInfoPO
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserInfoPO registerUser(CrudUserBO userBO, String clearPwd) {
        UserInfoPO authDO = BeanHelperUtil.createCopyBean(userBO, UserInfoPO.class);
        authDO.setCreator(userBO.getUsername());
        authDO.setModifier(userBO.getUsername());
        authDO = userInfoRepository.save(authDO);
        return authDO;
    }


    /**
     * 删除账号
     * @param authBO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUser(UserDetailDO authBO) {
        BaseException userNotFound = ApiResultUtil.fail4BadParameter(ReturnCode.NOT_ACCEPTABLE, "找不到该用户");
        userInfoRepository.delete(userInfoRepository.findByUsername(authBO.getUsername()).orElseThrow(() -> userNotFound));
    }

    /**
     * 注册电话 邮箱 校验
     * @param type 注册校验类型
     * @param text 注册校验内容
     * @return ValidSignUpDTO
     */
    @Override
    @Transactional(readOnly = true)
    public Boolean validSignUp(SignType type, String text) {
        UserInfoPO auth = new UserInfoPO();
        String key = Constant.Redis.SIGN_UP_PREFIX + text.trim();
        switch (type) {
            case tel:
                auth.setTel(text.trim());
                return CommonUtil.validTel(auth.getTel())
                        && !redisTemplate.hasKey(key)
                        && !userInfoRepository.exists(Example.of(auth));
            case email:
                auth.setEmail(text.trim());
                return CommonUtil.validEmail(auth.getEmail())
                        && !redisTemplate.hasKey(key)
                        && !userInfoRepository.exists(Example.of(auth));
            default:
                return false;
        }
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserInfoPO modifyUser(CrudUserBO authBO) {
        UserInfoPO target = BeanHelperUtil.createCopyBean(authBO, UserInfoPO.class);
        userInfoRepository.findByUsername(authBO.getUsername()).ifPresent(s -> BeanHelperUtil.copyNullProperties(s, target));
        return userInfoRepository.save(target);
    }


    /**
     * 修改密码
     * @param credBO
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void modifyPassword(UpdateCredBO credBO) {
        UserInfoPO userPO =  findUserByUsername(credBO.getUsername());
        if (!userPO.getCredential().getPassword().equals(credBO.getCurrentPassword())) {
            throw ApiResultUtil.fail4BadParameter(ReturnCode.NOT_ACCEPTABLE,"原密码错误");
        }
        List<String> list = userPO.getCredential().getHistoryPassword();
        if (list.size()> passNumber){
            list.remove(list.get(0));
        }
        list.add(credBO.getModifiedPassword());
        userPO.getCredential().setHistoryPassword(list);
        userPO.getCredential().setPassword(credBO.getModifiedPassword());
    }


    /**
     * 如果数据库密码数量超过设定值那么最早的密码被删除
     * @param chDO
     */
    private void handleCredPassword(CredentialPO chDO) {

    }

    /**
     * 登录密码校验
     * @param username 用户名
     * @return Optional<CredentialDO>
     */
    @Override
    public UserInfoPO findUserByUsername(String username) {
        return userInfoRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("username无效"));
    }

    /**
     * 根据用户名查询信息
     * @param name
     * @return
     */
    @Override
    public UserDetailDO findUserInfo(String name) {
        UserInfoPO UserInfoPO = userInfoRepository.findByUsername(name).orElseThrow(()->new BaseException(ReturnCode.INVALID_REQUEST));
        return BeanHelperUtil.createCopyBean(UserInfoPO, UserDetailDO.class);
    }

    @Override
    public String bookUsername(CrudUserBO crudUserBO) {
        StringBuilder buffer = new StringBuilder(EncryptUtil.getRandomCode(LENGTH,Boolean.TRUE));
        buffer.append(Constant.Symbol.HYPHEN);
        buffer.append(EncryptUtil.getRandomCode(6,true));
        if (redisTemplate.hasKey(Constant.Redis.SIGN_UP_PREFIX + buffer.toString()) && userInfoRepository.exists(Example.of(new UserInfoPO(buffer.toString())))){
            throw ApiResultUtil.fail4BadParameter(ReturnCode.NOT_ACCEPTABLE);
        }
        return buffer.toString();
    }

    /**
     * 注册信息在redis
     * @param keyWord
     */
    @Override
    public void setSignInfo2Redis(final @NotBlank String keyWord){
        try {
            ValueOperations<String, String> operations = redisTemplate.opsForValue();
            operations.set(Constant.Redis.SIGN_UP_PREFIX + keyWord, keyWord,signUpTimeOut, TimeUnit.SECONDS);
        }catch (Exception e){
            log.error("写入redis缓存（设置expire存活时间）失败！错误信息为：" + e.getMessage());
            throw new BaseException(ReturnCode.SERVER_ERROR);
        }
    }

    @Override
    public boolean setUserInfo2Cache(UserDetailDO userInfoDTO) {
        try {
            ValueOperations<String, String> operations = redisTemplate.opsForValue();
            operations.set(Constant.Redis.USER_INFO_PREFIX + userInfoDTO.getUsername(), JsonUtil.toJson(userInfoDTO), userTimeOut, TimeUnit.HOURS);
            return true;
        }catch (Exception e){
            log.error("写入redis缓存（设置expire存活时间）失败！错误信息为：" + e.getMessage());
        }
        return false;
    }

    @Override
    public Map<String, String> refresh(String token) {
        return null;
    }



}
