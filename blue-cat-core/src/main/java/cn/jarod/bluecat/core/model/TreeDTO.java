package cn.jarod.bluecat.core.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @auther jarod.jin 2019/9/4
 */
@Getter
@Setter
public class TreeDTO extends BaseDTO{

    private String node;

    private String pNode;

    private List<? extends TreeDTO> children;
}
