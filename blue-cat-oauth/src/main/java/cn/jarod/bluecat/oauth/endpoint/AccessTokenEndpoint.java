package cn.jarod.bluecat.oauth.endpoint;

import cn.jarod.bluecat.core.base.model.ResultDTO;
import cn.jarod.bluecat.core.service.SecurityService;
import cn.jarod.bluecat.core.utils.ApiResultUtil;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.web.annotation.WebEndpoint;

/**
 * @author Jarod.jin
 */
@WebEndpoint(id = "/access-token")
public class AccessTokenEndpoint {

    private final SecurityService securityService;

    public AccessTokenEndpoint(SecurityService securityService) {
        this.securityService = securityService;
    }

    @ReadOperation()
    public ResultDTO takeToken() {
        return ApiResultUtil.getSuccess(securityService.createToken());
    }


}
