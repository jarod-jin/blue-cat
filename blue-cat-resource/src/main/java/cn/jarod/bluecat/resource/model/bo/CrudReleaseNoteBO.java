package cn.jarod.bluecat.resource.model.bo;

import cn.jarod.bluecat.core.model.NoSqlModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author jarod.jin 2019/11/20
 */
@Getter
@Setter
@ToString
public class CrudReleaseNoteBO extends NoSqlModel {

    /**版本号*/
    private String releaseVersion;

    /**字典键-值*/
    private List<String> releaseNote;

    /**打包版本序号号*/
    private BigDecimal buildNo;

}
