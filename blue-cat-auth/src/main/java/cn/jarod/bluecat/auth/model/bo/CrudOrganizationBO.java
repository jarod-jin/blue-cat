package cn.jarod.bluecat.auth.model.bo;

import cn.jarod.bluecat.core.model.TreeModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * @author jarod.jin 2019/10/18
 */
@Getter
@Setter
@ToString
public class CrudOrganizationBO extends TreeModel {

    /**组织名称*/
    private String orgName;

    /**全组织编码*/
    private String fullCode;

    /**全组织名称*/
    private String fullName;

    /**显示顺序*/
    private Integer sortOrder;

    /**组织类型(0 虚拟组织 1实际组织 )*/
    private Integer orgType;

    /**版本号*/
    @NotNull
    private Integer version;

    /**关联编号*/
    private String businessCode;
}
