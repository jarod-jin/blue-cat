package cn.jarod.bluecat.core.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @auther jarod.jin 2019/9/4
 */
@Setter
@Getter
public class BaseVO {

    //唯一标识
    private Long id;
    //修改版本号
    private Integer version;
    //是否删除(默认为0,1表示删除)
    private Integer isDel;

    private String operator;

    public boolean isNew(){
        return id == null;
    }

    public void clearVersion(){
        this.version = 0;
    }

    public void clearId(){
        this.id = null;
    }
}
