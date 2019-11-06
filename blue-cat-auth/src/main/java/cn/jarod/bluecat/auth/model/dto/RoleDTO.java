package cn.jarod.bluecat.auth.model.dto;

import cn.jarod.bluecat.core.model.BaseDTO;
import lombok.Getter;
import lombok.Setter;

/**
 * @auther jarod.jin 2019/11/4
 */
@Getter
@Setter
public class RoleDTO extends BaseDTO {

    private String roleCode;

    private String roleName;

    private Integer disOrder;

    private String memo;

}
