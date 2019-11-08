package cn.jarod.bluecat.core.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @auther jarod.jin 2019/9/4
 */
@Getter
@Setter
public class TreeBO extends BaseBO {

    @NotBlank
    private String node;

    @NotNull
    private String pNode;

    private List<? extends TreeBO> children;
}
