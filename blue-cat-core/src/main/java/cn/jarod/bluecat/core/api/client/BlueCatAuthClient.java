package cn.jarod.bluecat.core.api.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author jarod.jin 2019/9/5
 */
@FeignClient(value="blue-cat-auth", fallbackFactory = AuthClientFallbackFactory.class)
public interface BlueCatAuthClient {
    @PostMapping("/hello")
    String hello();
}
