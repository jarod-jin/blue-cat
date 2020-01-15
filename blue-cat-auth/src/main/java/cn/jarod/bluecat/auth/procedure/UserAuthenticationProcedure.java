package cn.jarod.bluecat.auth.procedure;

import cn.jarod.bluecat.auth.enums.SignType;
import cn.jarod.bluecat.auth.model.bo.CrudUserBO;
import cn.jarod.bluecat.auth.model.dto.SignUpDTO;
import cn.jarod.bluecat.auth.service.CredentialService;
import cn.jarod.bluecat.core.common.ReturnCode;
import cn.jarod.bluecat.core.model.ResultDTO;
import cn.jarod.bluecat.core.utils.ApiResultUtil;
import cn.jarod.bluecat.core.utils.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author jarod.jin 2019/9/10
 */
@Slf4j
@Service
public class UserAuthenticationProcedure {


    private final CredentialService credentialService;

    public UserAuthenticationProcedure(CredentialService credentialService) {
        this.credentialService = credentialService;
    }

    /**
     * 注册前校验
     * @return ResultDTO
     */
    public ResultDTO validAuthority(String typeStr,String text) {
        return ApiResultUtil.getSuccess(credentialService.validSignUp(SignType.findSignType(typeStr),text));
    }


    /**
     * 注册用户
     * @param upDTO 新用户
     * @return ResultDTO
     */
    public ResultDTO signUp(SignUpDTO upDTO) {
        CrudUserBO crudUserBO = new CrudUserBO();
        if (upDTO.getSignType()==0){
            if (CommonUtil.validTel(upDTO.getSignName())){
                throw ApiResultUtil.fail4BadParameter(ReturnCode.NOT_ACCEPTABLE,"请输入一个正确的手机号"); }
            if (credentialService.validSignUp(SignType.tel,upDTO.getSignName())){
                throw ApiResultUtil.fail4BadParameter(ReturnCode.NOT_ACCEPTABLE,"该手机已被注册"); }
            crudUserBO.setTel(upDTO.getSignName());
        }else if (upDTO.getSignType()==1){
            if (CommonUtil.validEmail(upDTO.getSignName())){
                throw ApiResultUtil.fail4BadParameter(ReturnCode.NOT_ACCEPTABLE,"请输入一个正确的邮箱地址"); }
            if (credentialService.validSignUp(SignType.email,upDTO.getSignName())){
                throw ApiResultUtil.fail4BadParameter(ReturnCode.NOT_ACCEPTABLE,"该邮箱已被注册"); }
            crudUserBO.setEmail(upDTO.getSignName());
        }else{
            throw ApiResultUtil.fail4BadParameter(ReturnCode.NOT_ACCEPTABLE);
        }
        String username = credentialService.bookUsername(crudUserBO);
        credentialService.setSignInfo2Redis(upDTO.getSignName());
        credentialService.setSignInfo2Redis(username);
        crudUserBO.setCredentialType(upDTO.getCredentialType());
        return ApiResultUtil.getSuccess(credentialService.registerUser(crudUserBO,upDTO.getPassword()));
    }
}
