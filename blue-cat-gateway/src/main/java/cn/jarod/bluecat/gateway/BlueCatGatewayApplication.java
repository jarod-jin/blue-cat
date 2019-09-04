package cn.jarod.bluecat.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @auther jarod.jin 2019/9/4
 */
@SpringBootApplication(scanBasePackages = {"cn.jarod.bluecat.*"})
@EnableTransactionManagement
public class BlueCatGatewayApplication {
    public static void main(String args[]) {
        SpringApplication.run(BlueCatGatewayApplication.class, args);
    }
}
