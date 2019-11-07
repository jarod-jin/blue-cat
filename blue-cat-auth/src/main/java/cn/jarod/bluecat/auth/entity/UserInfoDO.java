package cn.jarod.bluecat.auth.entity;

import cn.jarod.bluecat.core.entity.DataBase;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * @auther jarod.jin 2019/9/9
 */
@Entity
@Getter
@Setter
@DynamicInsert
@DynamicUpdate
@EqualsAndHashCode(callSuper=true)
@Table(name = "user_info",
        indexes = {@Index(columnList ="username", name="UsernameIndex" , unique = true),
        @Index(columnList ="tel", name="TelIndex", unique = true),
        @Index(columnList ="email", name="EmailIndex", unique = true)})
public class UserInfoDO extends DataBase {

    //用户唯一标识
    @Column(nullable = false, columnDefinition=("varchar(50) comment '用户唯一标识'"))
    private String username;

    //用户名
    @Column(nullable = false, columnDefinition=("varchar(50) default '' comment '用户名'"))
    private String nickname;

    //性别
    @Column(nullable = false, columnDefinition=("varchar(6) default 'male' comment '性别'"))
    private String gender;

    //照片
    @Column(nullable = false, columnDefinition=("varchar(300) default '' comment '照片'"))
    private String photoUrl;

    //电话
    @Column(nullable = false, columnDefinition=("varchar(50) default '' comment '电话'"))
    private String tel;

    //邮箱
    @Column(nullable = false, columnDefinition=("varchar(50) default '' comment '邮箱'"))
    private String email;

    //账号类型
    @Column(nullable = false, columnDefinition=("tinyint default 0 comment '是否删除, 0个人级用户，1系统级用户'"))
    private Integer credentialType;

    //说明
    @Column(nullable = false, columnDefinition=("varchar(500) default '' comment '说明'"))
    private String memo;

    public UserInfoDO (){
        super();
    }

    public UserInfoDO (String username){
        super();
        this.username = username;
    }


}

