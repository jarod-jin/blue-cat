package cn.jarod.bluecat.core.component;

import cn.jarod.bluecat.core.model.ResultDTO;
import cn.jarod.bluecat.core.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @author Jarod Jin E-mail:jin_yibing@dahuatech.com
 * @version 创建时间：2020/3/7
 */
@Slf4j
public class CustomLogoutHandler implements LogoutSuccessHandler {

    public static final String ACCESS_TOKEN = "access_token";

    private final TokenStore tokenStore;

    public CustomLogoutHandler(TokenStore tokenStore){
        this.tokenStore = tokenStore;
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) {
        try {
            String accessToken = httpServletRequest.getParameter(ACCESS_TOKEN);
            if (StringUtils.hasText(accessToken)) {
                OAuth2AccessToken oAuth2AccessToken = tokenStore.readAccessToken(accessToken);
                if (oAuth2AccessToken != null) {
                    if (log.isDebugEnabled()){
                       log.debug("access_token: " + oAuth2AccessToken.getValue());
                    }
                    tokenStore.removeAccessToken(oAuth2AccessToken);
                    OAuth2RefreshToken oAuth2RefreshToken = oAuth2AccessToken.getRefreshToken();
                    tokenStore.removeRefreshToken(oAuth2RefreshToken);
                    tokenStore.removeAccessTokenUsingRefreshToken(oAuth2RefreshToken);
                }
            }
            httpServletResponse.setContentType("application/json;charset=UTF-8");
            httpServletResponse.getWriter().write(Objects.requireNonNull(JsonUtil.toJson(new ResultDTO(200,"sign out success",""))));
        } catch (IOException e) {
            log.error("logout error, cause by {}, the message is {}",e.getCause(), e.getMessage());
        }
    }
}
