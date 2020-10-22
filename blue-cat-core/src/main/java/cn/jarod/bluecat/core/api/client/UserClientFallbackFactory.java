package cn.jarod.bluecat.core.api.client;

import cn.jarod.bluecat.core.api.pojo.ResultDTO;
import cn.jarod.bluecat.core.api.util.ApiResultUtil;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author jarod.jin
 */
@Slf4j
@Component
public class UserClientFallbackFactory implements FallbackFactory<BlueCatUserClient> {

    @Override
    public BlueCatUserClient create(Throwable throwable) {
        return new BlueCatUserClient() {

            @Override
            public ResultDTO findUserByName(String name) {
                return ApiResultUtil.fail4ServerDown();
            }

            @Override
            public ResultDTO findUserByTel(String tel) {
                return ApiResultUtil.fail4ServerDown();
            }
        };
    }
}
