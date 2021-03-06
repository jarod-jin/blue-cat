package cn.jarod.bluecat.analyst.controller;

import cn.jarod.bluecat.analyst.procedure.DocumentAnalyze;
import cn.jarod.bluecat.core.api.pojo.ResponseDTO;
import cn.jarod.bluecat.core.security.pojo.UserDetailDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author jarod.jin 2019/12/2
 */
@Slf4j
@RestController
public class DocumentUploadController {

    @Autowired
    private DocumentAnalyze documentAnalyze;

    @PostMapping("/upload")
    @ResponseBody
    public ResponseDTO upload(@RequestParam("file") MultipartFile file) {
        return documentAnalyze.uploadResumeFile(file, new UserDetailDO());
    }
}
