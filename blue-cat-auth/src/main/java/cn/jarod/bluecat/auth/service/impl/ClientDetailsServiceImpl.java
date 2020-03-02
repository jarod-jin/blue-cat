package cn.jarod.bluecat.auth.service.impl;

import cn.jarod.bluecat.auth.entity.OauthClientDetailsDO;
import cn.jarod.bluecat.auth.repository.OauthClientDetailsRepository;
import cn.jarod.bluecat.core.common.Constant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;

/**
 * @author Jarod Jin E-mail:kira277@163.com
 * @version 创建时间：2020/3/2
 */
@Slf4j
@Service
public class ClientDetailsServiceImpl implements ClientDetailsService {


    private final OauthClientDetailsRepository oauthClientInfoRepository;

    public ClientDetailsServiceImpl(OauthClientDetailsRepository oauthClientInfoRepository) {
        this.oauthClientInfoRepository = oauthClientInfoRepository;
    }

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        OauthClientDetailsDO client = oauthClientInfoRepository.findByClientId(clientId)
                .orElseThrow(()->new UsernameNotFoundException("username无效"));
        BaseClientDetails clientDetails = new BaseClientDetails();
        clientDetails.setClientId(client.getClientId());
        clientDetails.setClientSecret(client.getClientSecret());
        clientDetails.setRegisteredRedirectUri(new HashSet<>(Arrays.asList(client.getRedirectUrl().split(Constant.Symbol.COMMA))));
        clientDetails.setAuthorizedGrantTypes(Arrays.asList(client.getGrantType().split(Constant.Symbol.COMMA)));
        clientDetails.setScope(Arrays.asList(client.getScope().split(Constant.Symbol.COMMA)));
        return clientDetails;
    }
}
