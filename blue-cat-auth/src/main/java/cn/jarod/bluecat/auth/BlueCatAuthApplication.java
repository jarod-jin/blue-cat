package cn.jarod.bluecat.auth;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @auther jarod.jin 2019/9/4
 */
//@SpringBootApplication(scanBasePackages = {"cn.jarod.bluecat.auth",
//        "cn.jarod.bluecat.core.controller","cn.jarod.bluecat.core.config"})
//@EnableTransactionManagement
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"cn.jarod.bluecat.core.service.api"})
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, DruidDataSourceAutoConfigure.class})
public class BlueCatAuthApplication {
    public static void main(String args[]) {
        SpringApplication.run(BlueCatAuthApplication.class, args);
    }
}
