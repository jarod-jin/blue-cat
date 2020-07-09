package cn.jarod.bluecat.access.user.pojo;

import cn.jarod.bluecat.auth.model.dto.UpdateCredDTO;
import cn.jarod.bluecat.core.utils.EncryptUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author jarod.jin 2019/9/23
 */
@Getter
@Setter
@ToString
public class UpdateCredBO {

    private String username;

    private String currentPassword;

    private String modifiedPassword;

    public UpdateCredBO(UpdateCredDTO updateCredDTO){
        this.username = updateCredDTO.getUsername();
        this.currentPassword = EncryptUtil.stringEncodeSHA256(updateCredDTO.getCurrentPassword());
        this.modifiedPassword = EncryptUtil.stringEncodeSHA256(updateCredDTO.getModifiedPassword());
    }
}
