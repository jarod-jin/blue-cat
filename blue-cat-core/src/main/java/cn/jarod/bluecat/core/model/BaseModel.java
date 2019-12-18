package cn.jarod.bluecat.core.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * @author jarod.jin 2019/9/4
 */
@Setter
@Getter
public class BaseModel {

    //唯一标识
    private Long id;
    //修改版本号
    @NotNull
    private Integer version;
    //是否删除(默认为0,1表示删除)
    private Integer isDel;

    private String modifier;

    public boolean isNew(){
        return id == null;
    }

    public void reset(){
        this.id = null;
        this.version = 0;
    }
}
