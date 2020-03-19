package cn.jarod.bluecat.auth.authorization;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Arrays;
import java.util.Map;

/**
 * @author Jarod Jin E-mail:kira277@163.com
 * @version 创建时间：2020/3/16
 */
@Setter
@Getter
@ToString
public class IntegrationAuthentication {

    private String authType;

    private String username;

    private Map<String, String[]> authParameters;

    public String getAuthParameter(String parameter){
        if (authParameters.containsKey(parameter)){
            return Arrays.stream(authParameters.get(parameter)).findFirst().orElse("");
        }
        return null;
    }

}
