package cn.jarod.bluecat.object.model.bo;

import cn.jarod.bluecat.core.model.RdsModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author jarod.jin 2019/10/10
 */
@Getter
@Setter
@ToString
public class LinkResourceShareBO extends RdsModel {

    private String resourceCode;

    private String shareCode;

    private Integer shareType;

}
