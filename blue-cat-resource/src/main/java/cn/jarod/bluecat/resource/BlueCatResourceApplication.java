package cn.jarod.bluecat.resource;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author jarod.jin 2019/11/13
 */
@EnableHystrix
@EnableDiscoveryClient
@EnableTransactionManagement
@EnableFeignClients(basePackages = {"cn.jarod.bluecat.core.api"})
@SpringBootApplication(scanBasePackages = {"cn.jarod.bluecat.*"}, exclude = {DataSourceAutoConfiguration.class, DruidDataSourceAutoConfigure.class})
public class BlueCatResourceApplication {
    public static void main(String[] args) {
        SpringApplication.run(BlueCatResourceApplication.class, args);
    }
}
