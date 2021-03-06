package cn.jarod.bluecat.utility;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;


/**
 * @author jarod.jin 2019/9/4
 */
@Slf4j
@EnableHystrix
@EnableDiscoveryClient
@EnableTransactionManagement
@EnableEncryptableProperties
@EnableFeignClients(basePackages = {"cn.jarod.bluecat.core.api"})
@SpringBootApplication(scanBasePackages = {"cn.jarod.bluecat.*"})
public class BlueCatUtilityApplication {
    public static void main(String[] args) {
        SpringApplication.run(BlueCatUtilityApplication.class, args);
    }
}
