package cn.jarod.bluecat.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @auther jarod.jin 2019/9/4
 */
@SpringBootApplication(scanBasePackages = {"cn.jarod.bluecat.auth",
        "cn.jarod.bluecat.core.controller","cn.jarod.bluecat.core.config"})
@EnableTransactionManagement
public class BlueCatAuthApplication {
    public static void main(String args[]) {
        SpringApplication.run(BlueCatAuthApplication.class, args);
    }
}
