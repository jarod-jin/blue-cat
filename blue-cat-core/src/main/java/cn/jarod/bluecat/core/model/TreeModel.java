package cn.jarod.bluecat.core.model;

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
public class TreeModel extends BaseModel {

    @NotBlank
    private String node;

    @NotNull
    private String pNode;

    private List<? extends TreeModel> children;
}
