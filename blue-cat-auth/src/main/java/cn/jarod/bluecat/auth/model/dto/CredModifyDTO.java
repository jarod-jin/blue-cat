package cn.jarod.bluecat.auth.model.dto;

import cn.jarod.bluecat.auth.model.bo.CredModifyBO;
import cn.jarod.bluecat.core.utils.EncryptUtil;
import lombok.Getter;
import lombok.Setter;

/**
 * @auther jarod.jin 2019/9/23
 */
@Getter
@Setter
public class CredModifyDTO {

    private String authority;

    private String currentPassword;

    private String modifiedPassword;

    public CredModifyDTO(CredModifyBO credBO){
        this.authority = credBO.getAuthority();
        this.currentPassword = EncryptUtil.stringEncodeSHA256(credBO.getCurrentPassword());
        this.modifiedPassword = EncryptUtil.stringEncodeSHA256(credBO.getModifiedPassword());
    }
}
