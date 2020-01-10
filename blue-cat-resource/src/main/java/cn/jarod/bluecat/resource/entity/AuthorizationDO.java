package cn.jarod.bluecat.resource.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @author Jarod Jin E-mail:kira277@163.com
 * @version 创建时间：2020/1/10
 */
@Getter
@Setter
@ToString
public class AuthorizationDO {

    /**角色ID*/
    @Field("authorizationId")
    private String authorizationId;

    /**描述*/
    @Field("description")
    private String description;

    /**读取*/
    @Field("read")
    private Boolean read;

    /**创建*/
    @Field("create")
    private Boolean create;

    /**修改*/
    @Field("read")
    private Boolean update;

    /**删除*/
    @Field("delete")
    private Boolean delete;

    /**执行*/
    @Field("execute")
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
        if (!(other instanceof AuthorizationDO)) {
            return false;
        }
        AuthorizationDO o = (AuthorizationDO) other;
        return o.authorizationId.equals(this.authorizationId);

    }
}
