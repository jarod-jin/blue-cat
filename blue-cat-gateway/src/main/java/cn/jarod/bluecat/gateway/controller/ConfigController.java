package cn.jarod.bluecat.gateway.controller;

import cn.jarod.bluecat.core.api.BlueCatAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @auther jarod.jin 2019/9/5
 */
@RestController
@RequestMapping("/config")
@RefreshScope
public class ConfigController {

    @Value("${useLocalCache:null}")
    private String useLocalCache;

    @Autowired
    BlueCatAuthService client;

    /**
     * http://localhost:8090/config/get
     */
    @RequestMapping("/get")
    public String get() {
        return useLocalCache;
    }

    /**
     * http://localhost:8090/config/hello?name=xxx
     */
    @GetMapping("/hello")
    public String hello(@RequestParam(name = "name") String name) {
        return "你好 : " + client.hello(name);
    }
}
