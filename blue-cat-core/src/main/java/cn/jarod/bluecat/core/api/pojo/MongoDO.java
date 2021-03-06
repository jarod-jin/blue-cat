package cn.jarod.bluecat.core.api.pojo;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

import javax.validation.constraints.NotNull;

/**
 * @author Jarod Jin E-mail:kira277@163.com
 * @version 创建时间：2019/12/31
 */
@Getter
@Setter
public abstract class MongoDO {

    @NotNull
    protected ObjectId id;

    /**版本号,乐观锁*/
    private Integer version ;

    /**修改者*/
    private String modifier;

    public boolean isNew(){
        return id == null;
    }

    public void reset(){
        this.id = null;
        this.version = 0;
    }
}
