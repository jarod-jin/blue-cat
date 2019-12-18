package cn.jarod.bluecat.gateway;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author jarod.jin 2019/9/4
 */
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "cn.jarod.bluecat.core.api")
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, DruidDataSourceAutoConfigure.class})
public class BlueCatGatewayApplication {
    public static void main(String args[]) {
        SpringApplication.run(BlueCatGatewayApplication.class, args);
    }
}
