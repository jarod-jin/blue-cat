package cn.jarod.bluecat.auth.model.bo;

import cn.jarod.bluecat.auth.model.dto.UpdateCredDTO;
import cn.jarod.bluecat.core.utils.EncryptUtil;
import lombok.Getter;
import lombok.Setter;

/**
 * @auther jarod.jin 2019/9/23
 */
@Getter
@Setter
public class UpdateCredBO {

    private String authority;

    private String currentPassword;

    private String modifiedPassword;

    public UpdateCredBO(UpdateCredDTO updateCredDTO){
        this.authority = updateCredDTO.getUsername();
        this.currentPassword = EncryptUtil.stringEncodeSHA256(updateCredDTO.getCurrentPassword());
        this.modifiedPassword = EncryptUtil.stringEncodeSHA256(updateCredDTO.getModifiedPassword());
    }
}
