package cn.jarod.bluecat.core.api.client;

import cn.jarod.bluecat.core.common.enums.Constant;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author jarod.jin
 */
@Slf4j
@Component
public class AuthClientFallbackFactory implements FallbackFactory<BlueCatAuthClient> {

    @Override
    public BlueCatAuthClient create(Throwable throwable) {
        return () -> {
            log.error(throwable.getMessage());
            return "Execute raw fallback: access service fail , req= " + LocalDateTime.now() + " reason = " + throwable.getCause();
        };
    }
}

