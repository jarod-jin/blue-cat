package cn.jarod.bluecat.auth.entity;

import cn.jarod.bluecat.core.entity.RdsEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * @author jarod.jin 2019/9/9
 */
@Entity
@Getter
@Setter
@ToString
@DynamicInsert
@DynamicUpdate
@EqualsAndHashCode(callSuper=true)
@Table(name = "cred_history", indexes = {@Index(columnList ="username", name="UsernameIndex")})
public class CredHistoryDO extends RdsEntity {

    /**用户唯一标识*/
    @Column(nullable = false, columnDefinition=("varchar(50) comment '用户唯一标识'"))
    private String username;

    /**密码验证串*/
    @Column(nullable = false, columnDefinition=("varchar(250) default '' comment '密码验证串'"))
    private String password;

    public CredHistoryDO (){}

    public CredHistoryDO (String username, String password,String operator){
        this.username = username;
        this.password = password;
        super.setCreator(operator);
        super.setModifier(operator);
    }

}
