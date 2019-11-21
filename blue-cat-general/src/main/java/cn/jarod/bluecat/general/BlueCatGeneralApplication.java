package cn.jarod.bluecat.general;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @auther jarod.jin 2019/11/13
 */
@EnableHystrix
@EnableDiscoveryClient
@EnableTransactionManagement
@EnableFeignClients(basePackages = {"cn.jarod.bluecat.core.api"})
@SpringBootApplication(scanBasePackages = {"cn.jarod.bluecat.*"})
public class BlueCatGeneralApplication {
    public static void main(String args[]) {
        SpringApplication.run(BlueCatGeneralApplication.class, args);
    }
}
