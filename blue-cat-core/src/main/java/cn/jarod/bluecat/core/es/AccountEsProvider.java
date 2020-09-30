package cn.jarod.bluecat.account.config;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class AccountEsProvider extends ElasticSearchProvider{


    public AccountEsProvider(@Qualifier("highLevelClient") RestHighLevelClient esClient) {
        super(esClient);
    }
}
