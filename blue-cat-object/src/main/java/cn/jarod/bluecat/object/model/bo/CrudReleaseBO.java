package cn.jarod.bluecat.object.model.bo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.bson.types.ObjectId;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author jarod.jin 2019/11/20
 */
@Getter
@Setter
@ToString
public class CrudReleaseBO {
    /**AppId*/
    private ObjectId appId;

    /**版本号*/
    private Integer version;

    /**类型*/
    private String releaseType;

    /**版本号*/
    private String releaseVersion;

    /**字典键-值*/
    private List<String> notes;

    /**打包版本序号号*/
    private BigDecimal buildNo;

}
