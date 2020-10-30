package cn.jarod.bluecat.resource.controller;

import cn.jarod.bluecat.core.api.pojo.ResultDTO;
import cn.jarod.bluecat.core.api.util.ApiResultUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: jarod jin
 * @Date: 2020/10/29 9:48
 */
@Slf4j
@RequestMapping("/test")
@RestController
public class TestController {
    @ApiOperation(value = "用户登录测试接口", notes = "用户登录试接口000")
    @GetMapping("/demo")
    public ResultDTO demo(@PathVariable String name){
        return ApiResultUtil.getSuccess("你好!"+name);
    }
}
