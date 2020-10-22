package cn.jarod.bluecat.resource.model.bo;

import cn.jarod.bluecat.core.api.pojo.MysqlDO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author jarod.jin 2019/10/10
 */
@Getter
@Setter
@ToString
public class LinkResourceShareBO extends MysqlDO {

    private String resourceCode;

    private String shareCode;

    private Integer shareType;

}
