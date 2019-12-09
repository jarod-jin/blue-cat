package cn.jarod.bluecat.analyst.procedure;

import cn.jarod.bluecat.analyst.entity.DocumentTextDO;
import cn.jarod.bluecat.analyst.service.IDocumentTextService;
import cn.jarod.bluecat.core.enums.ReturnCode;
import cn.jarod.bluecat.core.exception.BaseException;
import cn.jarod.bluecat.core.model.ResultDTO;
import cn.jarod.bluecat.core.model.auth.UserDetailDTO;
import cn.jarod.bluecat.core.utils.PoiUtil;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
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

    private static final String RESUME = "resume";
    @Autowired
    private IDocumentTextService documentTextService;

    public ResultDTO uploadResumeFile(MultipartFile file, UserDetailDTO userDTO){
        if (file.isEmpty())
            throw new BaseException(ReturnCode.NOT_ACCEPTABLE);
        String filename = file.getOriginalFilename()!=null?file.getOriginalFilename():file.getName();
        try {
            List<String> contextList;
            if (filename.endsWith(".doc")) {
                contextList = PoiUtil.readWord(file.getInputStream());
            }else if(filename.endsWith(".docx")){
                contextList = PoiUtil.readWordX(file.getInputStream());
            }else{
                throw new BaseException(ReturnCode.NOT_ACCEPTABLE.getCode(),"无法识别此文件");
            }
            DocumentTextDO document = new DocumentTextDO(ObjectId.get());
            document.setCreator(userDTO.getUsername());
            document.setSubject(filename);
            document.setContextList(contextList);
            documentTextService.save(document, RESUME);
            log.info("上传成功");
            return new ResultDTO(ReturnCode.SAVE_SUCCESS);
        } catch (IOException e) {
            log.error(e.toString(), e);
        }
        return new ResultDTO(ReturnCode.NOT_ACCEPTABLE);
    }
}
