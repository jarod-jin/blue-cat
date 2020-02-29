package cn.jarod.bluecat.resource.model.bo;

import cn.jarod.bluecat.core.model.TreeModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author jarod.jin 2019/10/10
 */
@Getter
@Setter
@ToString
public class QueryResourceTreeBO extends TreeModel {

    /**名称*/
    private String resourceName;

    /**图标*/
    private String resourceIcon;

    /**资源类型*/
    private String resourceType;

    /**资源地址*/
    private String resourceRoute;

    /**备注说明*/
    private String memo;

    /**显示顺序*/
    private Integer sortOrder;

    /**是否拥有权限*/
    private Boolean access;

}
