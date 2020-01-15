package cn.jarod.bluecat.core.service.impl;

import cn.jarod.bluecat.core.annotation.AccessLimit;
import cn.jarod.bluecat.core.common.Constant;
import cn.jarod.bluecat.core.common.ReturnCode;
import cn.jarod.bluecat.core.service.SecurityService;
import cn.jarod.bluecat.core.utils.ApiResultUtil;
import cn.jarod.bluecat.core.utils.HttpUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author Jarod Jin E-mail:kira277@163.com
 * @version 创建时间：2020/1/15
 */
@Service
public class SecurityServiceImpl implements SecurityService {

    private static final String TOKEN_NAME = "token";

    private final StringRedisTemplate redisTemplate;

    @Value("${security.time-out.idempotent:10}")
    private Long idempotentTimeOut;

    public SecurityServiceImpl(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public String createToken() {
        String uuid = UUID.randomUUID().toString().toLowerCase();
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        operations.set(uuid, uuid, idempotentTimeOut, TimeUnit.MINUTES);
        return uuid;
    }

    @Override
    public void validToken(@NotNull HttpServletRequest request) {
        String token = request.getHeader(TOKEN_NAME);
        /*header中不存在token*/
        if (StringUtils.isEmpty(token)) {
            token = request.getParameter(TOKEN_NAME);
            /*中也不存在token*/
            if (StringUtils.isEmpty(token)) {
                throw ApiResultUtil.fail4BadParameter(ReturnCode.NOT_ACCEPTABLE,"参数中缺少Token");
            }
        }
        if (!redisTemplate.hasKey(token)) {
            throw ApiResultUtil.fail4BadParameter(ReturnCode.UNPROCESSABLE_ENTITY,"Token不存在或者已过期");
        }
        if (redisTemplate.delete(token)) {
            throw ApiResultUtil.fail4BadParameter(ReturnCode.ALREADY_EXISTED);
        }
    }

    @Override
    public void validAccessLimit(AccessLimit annotation, HttpServletRequest request) {
        long maxCount = annotation.maxCount();
        long seconds = annotation.timeBox();

        String key = Constant.Redis.ACCESS_LIMIT_PREFIX + HttpUtil.getIpAddress(request) + request.getRequestURI();
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        if (!redisTemplate.hasKey(key)) {
            operations.set(key, String.valueOf(1),seconds, TimeUnit.SECONDS);
        } else {
            long count = Long.parseLong(operations.get(key));
            if (count < maxCount) {
                /*获取剩余时间*/
                Long ttl = redisTemplate.getExpire(key);
                if (ttl <= 0) {
                    operations.set(key, String.valueOf(1), seconds, TimeUnit.SECONDS);
                } else {
                    operations.set(key, String.valueOf(++count), ttl.intValue(), TimeUnit.SECONDS);
                }
            } else {
                throw ApiResultUtil.fail4BadParameter(ReturnCode.FORBIDDEN,"单位时间超过提交次数限度");
            }
        }
    }
}
