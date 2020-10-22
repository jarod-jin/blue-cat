package cn.jarod.bluecat.core.common.enums;

import lombok.Getter;

/**
 * @author Jarod Jin E-mail:kira277@163.com
 * @version 创建时间：2020/1/8
 */
public enum EntityType {
    /**未删除*/
    NOT_DEL(0),
    /**已删除*/
    DEL(1);

    @Getter
    private Integer type;

    EntityType(int type){
        this.type = type;
    }
}
