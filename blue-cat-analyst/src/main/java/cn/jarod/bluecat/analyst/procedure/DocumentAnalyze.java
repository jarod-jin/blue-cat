package cn.jarod.bluecat.analyst.procedure;

import cn.jarod.bluecat.core.enums.ReturnCode;
import cn.jarod.bluecat.core.exception.BaseException;
import cn.jarod.bluecat.core.model.ResultDTO;
import cn.jarod.bluecat.core.model.auth.UserDetailDTO;
import cn.jarod.bluecat.core.utils.PoiUtil;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @auther jarod.jin 2019/12/6
 */
@Slf4j
@Service
public class DocumentAnalyze {

    public ResultDTO uploadAnalyzeFile(MultipartFile file, UserDetailDTO userDTO){
        if (file.isEmpty()) {
            throw new BaseException(ReturnCode.NOT_ACCEPTABLE);
        }
        List<String> contextList = Lists.newArrayList();
        String filename = file.getOriginalFilename()!=null?file.getOriginalFilename():file.getName();
        try {
            if (filename.endsWith(".doc")) {
                contextList = PoiUtil.readWord(file.getInputStream());
            }else if(filename.endsWith(".docx")){
                contextList = PoiUtil.readWordX(file.getInputStream());
            }else{
                throw new BaseException(ReturnCode.NOT_ACCEPTABLE.getCode(),"无法识别此文件");
            }
            log.info("上传成功");
            return new ResultDTO(ReturnCode.SAVE_SUCCESS);
        } catch (IOException e) {
            log.error(e.toString(), e);
        }
        return new ResultDTO(ReturnCode.NOT_ACCEPTABLE);
    }
}
