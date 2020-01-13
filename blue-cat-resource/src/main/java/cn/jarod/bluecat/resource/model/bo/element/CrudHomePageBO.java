package cn.jarod.bluecat.resource.model.bo.element;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.bson.types.ObjectId;

import javax.validation.constraints.NotNull;


/**
 * @author jarod.jin 2019/11/13
 */
@Getter
@Setter
@ToString
public class CrudHomePageBO extends CrudPageBO {

    @NotNull
    protected ObjectId id;

    /**终端标识*/
    private String terminalVersion;

    /**关联系统编号*/
    private String belongTo;


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
