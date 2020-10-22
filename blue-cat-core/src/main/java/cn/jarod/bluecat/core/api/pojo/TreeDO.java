package cn.jarod.bluecat.core.api.pojo;

import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author jarod.jin 2019/9/4
 */
@Getter
@Setter
public class TreeDO extends MysqlDO {

    @NotBlank
    private String nodeId;

    @NotNull
    private String parentId;

    private List<? extends TreeDO> children;
}
