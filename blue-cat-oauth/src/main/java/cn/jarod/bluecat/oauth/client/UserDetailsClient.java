package cn.jarod.bluecat.oauth.client;


import cn.jarod.bluecat.core.api.client.BlueCatUserClient;
import cn.jarod.bluecat.core.api.pojo.ResponseDTO;
import cn.jarod.bluecat.core.security.pojo.UserDetailDO;
import cn.jarod.bluecat.core.common.utils.MapBeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Map;


/**
 * @author Jarod Jin E-mail:kira277@163.com
 * @version 创建时间：2020/4/15
 */
@Slf4j
@Service
public class UserDetailsClient {

    private final BlueCatUserClient blueCatUserClient;
    public UserDetailsClient(BlueCatUserClient blueCatUserClient) {
        this.blueCatUserClient = blueCatUserClient;
    }

    public UserDetailDO loadUserByUsername(String username) throws UsernameNotFoundException {
        return loadUserDetail(blueCatUserClient.findUserByName(username));
    }

    public UserDetailDO loadUserByTel(String tel) throws UsernameNotFoundException {
        return loadUserDetail(blueCatUserClient.findUserByTel(tel));
    }

    private UserDetailDO loadUserDetail(ResponseDTO responseDTO) {
        if (responseDTO != null && responseDTO.isSuccessful() && responseDTO.getData() instanceof Map) {
            Assert.isNull(responseDTO.getData(), "用户名不存在");
            @SuppressWarnings("unchecked")
            Map<String, Object> objectMap = (Map<String, Object>) responseDTO.getData();
            return MapBeanUtil.map2Bean(objectMap, UserDetailDO.class);
        }
        throw new UsernameNotFoundException("用户名不存在");
    }
}
