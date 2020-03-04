package cn.jarod.bluecat.auth.service.impl;

import cn.jarod.bluecat.auth.entity.OauthClientDetailsDO;
import cn.jarod.bluecat.auth.repository.CredentialRepository;
import cn.jarod.bluecat.auth.repository.OauthClientDetailsRepository;
import cn.jarod.bluecat.core.common.Constant;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * @author Jarod Jin E-mail:kira277@163.com
 * @version 创建时间：2020/3/4
 */
@Service("CustomClientDetailsService")
public class ClientDetailsServiceImpl implements ClientDetailsService {

    private final OauthClientDetailsRepository clientDetailsRepository;

    public ClientDetailsServiceImpl(OauthClientDetailsRepository clientDetailsRepository) {
        this.clientDetailsRepository = clientDetailsRepository;
    }

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        OauthClientDetailsDO client = clientDetailsRepository.findByClientId(clientId).orElseThrow(
                ()-> new ClientRegistrationException("clientId无效"));
        BaseClientDetails clientDetails = new BaseClientDetails();
        clientDetails.setClientId(client.getClientId());
        clientDetails.setClientSecret(client.getClientSecret());
        clientDetails.setRegisteredRedirectUri(Sets.newHashSet(Arrays.asList(client.getWebServerRedirectUri().split(Constant.Symbol.COMMA))));
        clientDetails.setAuthorizedGrantTypes(Arrays.asList(client.getAuthorizedGrantTypes().split(Constant.Symbol.COMMA)));
        clientDetails.setScope(Arrays.asList(client.getScope().split(Constant.Symbol.COMMA)));
        clientDetails.setAccessTokenValiditySeconds(client.getAccessTokenValidity());
        clientDetails.setRefreshTokenValiditySeconds(client.getRefreshTokenValidity());
        return clientDetails;
    }
}
