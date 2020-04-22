package cn.jarod.bluecat.auth.client;


import cn.jarod.bluecat.core.common.Constant;
import cn.jarod.bluecat.core.model.ResultDTO;
import cn.jarod.bluecat.core.model.auth.UserDetailDTO;
import cn.jarod.bluecat.core.utils.MapBeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.map.LinkedMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;
import java.util.Map;


/**
 * @author Jarod Jin E-mail:jin_yibing@dahuatech.com
 * @version 创建时间：2020/4/15
 */
@Slf4j
@Service
public class UserDetailsClient {

    @Value("${sys-route.user.detail-info}")
    private String useInfoPath;

    private final RestTemplate restTemplate;

    public UserDetailsClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public UserDetailDTO loadUserByUsername(String username) throws UsernameNotFoundException {
        ResponseEntity<ResultDTO> responseEntity = restTemplate.getForEntity(useInfoPath + Constant.Symbol.SLASH + username, ResultDTO.class);
    /*
        if (!responseEntity.getStatusCode().is2xxSuccessful()){
            log.error(responseEntity.toString());
            throw new UsernameNotFoundException("用户服务异常");
        }
        */
        Assert.isTrue(!responseEntity.getStatusCode().is2xxSuccessful(), "用户服务异常");
        ResultDTO resultDTO = responseEntity.getBody();
        if (resultDTO !=null && resultDTO.isSuccessful() && resultDTO.getData() instanceof Map){
            Assert.isNull(resultDTO.getData(), "用户名不存在");
            @SuppressWarnings("unchecked")
            Map<String,Object> objectMap = (Map<String, Object>)resultDTO.getData();
            return MapBeanUtil.map2Bean(objectMap,UserDetailDTO.class);
        }
        throw new UsernameNotFoundException("用户名不存在");
    }
}
