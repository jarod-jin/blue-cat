package cn.jarod.bluecat.resource.model.bo;

import cn.jarod.bluecat.resource.entity.ReleaseDO;
import cn.jarod.bluecat.resource.entity.ResourceDO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;


/**
 * @author jarod.jin 2019/10/14
 */
@Getter
@Setter
@ToString
public class CrudApplicationBO extends CurdResourceBO {

    /**描述*/
    private String description;

    /**备注说明*/
    private String memo;

    /**web端版本更新记录*/
    private List<ReleaseDO> webReleases;

    /**iOs端版本更新记录*/
    private List<ReleaseDO> iOsReleases;

    /**android端版本更新记录*/
    private List<ReleaseDO> androidReleases;

}
