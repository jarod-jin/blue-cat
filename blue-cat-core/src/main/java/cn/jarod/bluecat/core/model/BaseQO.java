package cn.jarod.bluecat.core.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * @auther jarod.jin 2019/9/4
 */
@Setter
@Getter
public class BaseQO {

    @NotNull
    private Integer pageNum;

    @NotNull
    private Integer pageCount;

}
