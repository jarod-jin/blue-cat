package cn.jarod.bluecat.analyst.procedure;

import cn.jarod.bluecat.analyst.entity.CandidateDO;
import cn.jarod.bluecat.analyst.entity.DocumentTextDO;
import cn.jarod.bluecat.analyst.repository.DocumentTextRepository;

import cn.jarod.bluecat.analyst.utils.CandidateUtil;
import cn.jarod.bluecat.core.common.ReturnCode;
import cn.jarod.bluecat.core.exception.BaseException;
import cn.jarod.bluecat.core.model.ResultDTO;
import cn.jarod.bluecat.core.model.auth.UserDetailDTO;
import cn.jarod.bluecat.core.utils.ApiResultUtil;
import cn.jarod.bluecat.core.utils.PoiUtil;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * @author jarod.jin 2019/12/6
 */
@Slf4j
@Service
public class DocumentAnalyze {

    public static final String DOCX = ".docx";

    public static final String DOC = ".doc";

    private final DocumentTextRepository documentTextRepository;


    @Autowired
    public DocumentAnalyze(DocumentTextRepository documentTextRepository) {
        this.documentTextRepository = documentTextRepository;
    }

    public ResultDTO uploadResumeFile(MultipartFile file, UserDetailDTO userDTO){
        if (file.isEmpty()){
            throw ApiResultUtil.fail4BadParameter(ReturnCode.NOT_ACCEPTABLE);
        }
        String filename = file.getOriginalFilename()!=null?file.getOriginalFilename():file.getName();
        try {
            List<String> contextList;
            if (filename.endsWith(DOC)) {
                contextList = PoiUtil.readWord(file.getInputStream());
            }else if(filename.endsWith(DOCX)){
                contextList = PoiUtil.readWordX(file.getInputStream());
            }else{
                throw new BaseException(ReturnCode.NOT_ACCEPTABLE.getCode(),"无法识别此文件");
            }
            DocumentTextDO document = new DocumentTextDO(ObjectId.get());
            document.setCreator(userDTO.getUsername());
            document.setSubject(filename);
            document.setContextList(contextList);
            documentTextRepository.save(document);
            log.info("上传成功");
            return ApiResultUtil.saveSuccess(document.getId());
        } catch (IOException e) {
            log.error(e.toString(), e);
        }
        throw new BaseException(ReturnCode.NOT_ACCEPTABLE);
    }

    public void createCandidateBySubject(String subject){
        Optional<DocumentTextDO> optionalDocumentTextDO = documentTextRepository.findOneBySubject(subject);
        CandidateDO candiDO = new CandidateDO(ObjectId.get());
        candiDO.setName(subject);
        //获取性别
        String gander = CandidateUtil.findGander(optionalDocumentTextDO.get().getContextList());
        candiDO.setGander(gander);
        //获取年龄
        int age = CandidateUtil.findAge(optionalDocumentTextDO.get().getContextList());
        candiDO.setAge(age);

    }
}
