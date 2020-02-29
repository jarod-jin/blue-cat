package cn.jarod.bluecat.resource.model.bo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Jarod Jin E-mail:kira277@163.com
 * @version 创建时间：2020/1/13
 */
@Getter
@Setter
@ToString
public class CrudAuthorizationBO {

    /**权限ID*/
    private String authorizationId;

    /**描述*/
    private String description;

    /**读取*/
    private Boolean read;

    /**创建*/
    private Boolean create;

    /**修改*/
    private Boolean update;

    /**删除*/
    private Boolean delete;

    /**执行*/
    private Boolean execute;

    /**是否完全控制*/
    public Boolean getAll(){
        return read && create && update && delete && execute;
    }

    /**完全控制设置*/
    public void setAll(Boolean mark){
        read = create = update = delete = execute = mark;
    }


    @Override
    public int hashCode() {
        int result = 17;
        result = result * 31 + authorizationId.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this){
            return true;
        }
        if (!(other instanceof CrudAuthorizationBO)) {
            return false;
        }
        CrudAuthorizationBO o = (CrudAuthorizationBO) other;
        return o.authorizationId.equals(this.authorizationId);
    }
}
