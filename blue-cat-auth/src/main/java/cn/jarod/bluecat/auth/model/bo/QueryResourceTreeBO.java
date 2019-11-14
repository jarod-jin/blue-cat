package cn.jarod.bluecat.auth.model.bo;

import cn.jarod.bluecat.core.model.TreeBO;
import lombok.Getter;
import lombok.Setter;

/**
 * @auther jarod.jin 2019/10/10
 */
@Getter
@Setter
public class QueryResourceTreeBO extends TreeBO {

    private String resourceCode;

    private String resourceName;

}
