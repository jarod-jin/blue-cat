package cn.jarod.bluecat.general.model.bo;

import cn.jarod.bluecat.core.model.BaseModel;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * @auther jarod.jin 2019/11/19
 */
@Getter
@Setter
public class CrudDictEntryBO extends BaseModel {

    //字典类别
    private String dictCode;

    //角色名称
    private Map<String,Object> entryJson;

    //显示顺序
    private Integer disOrder;

    //描述
    private String memo;
}
