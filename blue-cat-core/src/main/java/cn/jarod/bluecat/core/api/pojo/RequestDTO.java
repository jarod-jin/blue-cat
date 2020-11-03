package cn.jarod.bluecat.core.api.pojo;

import cn.jarod.bluecat.core.security.pojo.DataConditionDO;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

/**
 * @Author: jarod jin
 * @Date: 2020/11/3 13:21
 */
@Setter
@Getter
public class RequestDTO {

    private List<DataConditionDO> conditionList;

}
