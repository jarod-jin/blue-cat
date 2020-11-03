package cn.jarod.bluecat.core.api.client;

import cn.jarod.bluecat.core.api.pojo.ResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author jarod.jin 2019/9/5
 */
@FeignClient(value="blue-cat-user")
public interface BlueCatUserClient {
    /**
     * 根据Name查询User
     * @param name 用户名
     * @return ResultDTO
     */
    @RequestMapping(value = "/info/username/{name}", method = RequestMethod.GET)
    ResponseDTO findUserByName(@PathVariable("name") String name);


    /**
     * 根据Tel查询User
     * @param tel  电话
     * @return ResultDTO
     */
    @RequestMapping(value ="/info/tel/{tel}", method = RequestMethod.GET)
    ResponseDTO findUserByTel(@PathVariable("tel") String tel);
}
