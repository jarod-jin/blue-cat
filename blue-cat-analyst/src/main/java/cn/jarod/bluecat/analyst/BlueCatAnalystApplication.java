package cn.jarod.bluecat.analyst;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author jarod.jin 2019/12/2
 */
@EnableHystrix
@EnableDiscoveryClient
@EnableTransactionManagement
@EnableFeignClients(basePackages = {"cn.jarod.bluecat.core.api"})
@SpringBootApplication(scanBasePackages = {"cn.jarod.bluecat.*"},exclude = {DataSourceAutoConfiguration.class, DruidDataSourceAutoConfigure.class})
public class BlueCatAnalystApplication {
    public static void main(String[] args) {
        SpringApplication.run(BlueCatAnalystApplication.class, args);
    }
}
