package cn.jarod.bluecat.analyst.controller;

import cn.jarod.bluecat.core.controller.BaseController;
import cn.jarod.bluecat.core.enums.ReturnCode;
import cn.jarod.bluecat.core.exception.BaseException;
import cn.jarod.bluecat.core.model.ResultDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @auther jarod.jin 2019/12/2
 */
@Slf4j
@RestController
public class DocumentUploadController extends BaseController {

    @PostMapping("/upload")
    @ResponseBody
    public ResultDTO upload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            throw new BaseException(ReturnCode.S400);
        }

        String fileName = file.getOriginalFilename();
        String filePath = "/Users/itinypocket/workspace/temp/";
        File dest = new File(filePath + fileName);
        try {
            file.transferTo(dest);
            log.info("上传成功");
            return new ResultDTO(ReturnCode.S200);
        } catch (IOException e) {
            log.error(e.toString(), e);
        }
        return new ResultDTO(ReturnCode.S400);
    }
}
