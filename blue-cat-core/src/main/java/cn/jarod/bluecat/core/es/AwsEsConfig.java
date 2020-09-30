package cn.jarod.bluecat.account.config;

import com.amazonaws.auth.AWS4Signer;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequestInterceptor;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class AwsEsConfig  {

    private final AwsEsClientProperties awsEsClientProperties;

    public AwsEsConfig(AwsEsClientProperties awsEsClientProperties) {
        this.awsEsClientProperties = awsEsClientProperties;
    }

    @Bean(name = "highLevelClient")
    public RestHighLevelClient elasticsearchClient() {
        return buildClient();
    }

    private RestHighLevelClient buildClient() {
            AWS4Signer signer = new AWS4Signer();
            signer.setServiceName(awsEsClientProperties.getServiceName());
            signer.setRegionName(awsEsClientProperties.getRegion());
            System.setProperty("aws.accessKeyId", awsEsClientProperties.getAccessKey());
            System.setProperty("aws.secretKey", awsEsClientProperties.getSecretKey());
            AWSCredentialsProvider credentialsProvider = new DefaultAWSCredentialsProviderChain();
            HttpRequestInterceptor interceptor = new AwsRequestSigningApacheInterceptor(awsEsClientProperties.getServiceName(), signer, credentialsProvider);
            return new RestHighLevelClient(RestClient.builder(HttpHost
                    .create(awsEsClientProperties.getAesEndpoint()))
                    .setHttpClientConfigCallback(h -> h.addInterceptorLast(interceptor)));
    }
}
