package cn.jarod.bluecat.core.service.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @auther jarod.jin 2019/9/5
 */
@FeignClient(value="blue-cat-auth")
public interface BlueCatAuthService {
    @GetMapping("/hello")
    String hello(@RequestParam(name = "name") String name);
}
