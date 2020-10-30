package cn.jarod.bluecat.gateway;

import cn.jarod.bluecat.core.data.es.config.ElasticSearchConfig;
import cn.jarod.bluecat.core.data.sql.config.DataSourceConfig;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchDataAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

/**
 * @author jarod.jin 2019/9/4
 */
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "cn.jarod.bluecat.core.api.client")
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, DruidDataSourceAutoConfigure.class})
@ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {ElasticSearchConfig.class, DataSourceConfig.class}))
public class BlueCatGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(BlueCatGatewayApplication.class, args);
    }
}
