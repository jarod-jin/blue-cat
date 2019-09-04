package cn.jarod.bluecat.core.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @auther jarod.jin 2019/9/4
 */
@Getter
@Setter
public class TreeVO extends BaseVO{

    private Long parentId;

    private List<? extends TreeVO > children;
}
