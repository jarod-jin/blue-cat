package cn.jarod.bluecat.resource.controller;

import cn.jarod.bluecat.core.api.BlueCatAuthClient;
import cn.jarod.bluecat.core.controller.BaseController;
import cn.jarod.bluecat.core.model.auth.UserDetailDTO;
import cn.jarod.bluecat.resource.model.bo.CrudReleaseBO;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author jarod.jin 2019/9/5
 */
@Slf4j
@RestController
@RequestMapping("/release")
public class ReleaseNoteController extends BaseController {

    private final BlueCatAuthClient authService;

    public ReleaseNoteController(BlueCatAuthClient authService) {
        this.authService = authService;
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello, this is test interface";
    }

    @RequestMapping("/info/{qq}")
    public CrudReleaseBO info(@PathVariable("qq") String qq){
        CrudReleaseBO crudReleaseBO = new CrudReleaseBO();
        crudReleaseBO.setAppId(new ObjectId(qq));
        return crudReleaseBO;
    }

    @RequestMapping("fans/{qq}")
    public List<CrudReleaseBO> fans(@PathVariable("qq") String qq){
        CrudReleaseBO crudReleaseBO = new CrudReleaseBO();
        crudReleaseBO.setAppId(new ObjectId(qq));
        return Lists.newArrayList(crudReleaseBO);
    }


}
