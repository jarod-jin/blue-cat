package cn.jarod.bluecat.auth.model.bo;

import cn.jarod.bluecat.core.model.BaseModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author jarod.jin 2019/11/13
 */
@Getter
@Setter
@ToString
public class CrudResourceBO extends BaseModel {

    /**资源编码*/
    private String resourceCode;

    /**资源名称*/
    private String resourceName;

    /**上级编码*/
    private String parentCode;

    /**显示顺序*/
    private Integer disOrder;

    /**资源类型*/
    private String resourceType;

    /**资源地址*/
    private String resourceRoute;

    /**资源图标*/
    private String resourceIcon;

    /**说明*/
    private String memo;

    /**关联系统编号*/
    private String sysCode;
}
