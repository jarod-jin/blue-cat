package cn.jarod.bluecat.object.model.dto;

import cn.jarod.bluecat.core.model.BaseQuery;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author jarod.jin 2019/11/20
 */
@Getter
@Setter
@ToString
public class HomePageQuery extends BaseQuery {

    /**终端类别*/
    private String terminalType;

    /**关联系统编号*/
    private String belongTo;


}
