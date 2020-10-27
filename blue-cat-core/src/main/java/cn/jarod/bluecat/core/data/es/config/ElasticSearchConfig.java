package cn.jarod.bluecat.core.data.es.config;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.List;

@Slf4j
@Configuration
public class ElasticSearchConfig {

    private final ElasticSearchProperties elasticsearchProperties;

    public ElasticSearchConfig(ElasticSearchProperties elasticSearchProperties) {
        this.elasticsearchProperties = elasticSearchProperties;
    }

    @Bean(name = "highLevelClient")
    public RestHighLevelClient elasticsearchClient() {
        // 设置es节点
        List<HttpHost> httpHosts = Lists.newArrayList();
        List<String> clusterNodes = elasticsearchProperties.getClusterNodes();
        clusterNodes.forEach(node -> {
            try {
                String[] parts = StringUtils.split(node, ":");
                Assert.notNull(parts, "Must defined");
                Assert.state(parts.length == 2, "Must be defined as 'host:port'");
                httpHosts.add(new HttpHost(parts[0], Integer.parseInt(parts[1]), elasticsearchProperties.getSchema()));
            } catch (Exception e) {
                throw new IllegalStateException("Invalid ES nodes " + "property '" + node + "'", e);
            }
        });
        RestClientBuilder builder = RestClient.builder(httpHosts.toArray(new HttpHost[0]));
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials(elasticsearchProperties.getAccount().getUsername(), elasticsearchProperties.getAccount().getPassword()));
        builder.setHttpClientConfigCallback(httpClientBuilder -> httpClientBuilder
                // 认证设置
                .setDefaultCredentialsProvider(credentialsProvider))
                // 超时时间
                .setRequestConfigCallback(
                        requestConfigBuilder -> requestConfigBuilder.setConnectTimeout(5000).setSocketTimeout(60000));
        return new RestHighLevelClient(builder);
    }
}
