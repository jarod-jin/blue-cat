package cn.jarod.bluecat.core.security.pojo;

import cn.jarod.bluecat.core.common.enums.Constant;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;

/**
 * @author jarod.jin 2019/11/8
 */
@Getter
@Setter
public class UserAuthorityDO implements GrantedAuthority, Serializable {

    private static final long serialVersionUID = 8394858769801484423L;

    /**范围类型 SYS/COMPANY/DEPARTMENT/PROJECT 等 */
    private String scopeType;

    /**范围值 ALL/ClientId 等*/
    private String scopeValue;

    /**角色Code*/
    private String roleCode;

    @Override
    public String getAuthority() {
        return roleCode + Constant.Symbol.UNDERLINE + scopeType + Constant.Symbol.UNDERLINE + scopeValue;
    }
}
