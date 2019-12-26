package cn.jarod.bluecat.core.api;

import cn.jarod.bluecat.core.api.BlueCatAuthClient;
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
        return new BlueCatAuthClient() {
            @Override
            public String hello() {
                log.error("异常处理={}", throwable);
                return "Execute raw fallback: access service fail , req= " + LocalDateTime.now() + " reason = " + throwable;
            }
        };
    }
}