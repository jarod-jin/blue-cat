package cn.jarod.bluecat.resource.entity;

import cn.jarod.bluecat.core.entity.BaseEntity;
import cn.jarod.bluecat.core.entity.NoSqlEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.Id;

import javax.persistence.*;
import java.util.List;

/**
 * @author jarod.jin 2019/10/15
 */
@Getter
@Setter
public class ResourceDO extends NoSqlEntity {

    /**资源编码*/
    @Column(nullable = false, columnDefinition=("varchar(32) comment '资源编码'"))
    private String resourceCode;

    /**资源名称*/
    @Column(nullable = false, columnDefinition=("varchar(250) default '' comment '资源名称'"))
    private String resourceName;

    /**上级资源编码*/
    private ObjectId parentId;

    /**资源类型*/
    @Column(nullable = false, columnDefinition=("varchar(10) default '' comment '资源类型'"))
    private String resourceType;

    /**说明*/
    @Column(nullable = false, columnDefinition=("varchar(500) default '' comment '说明'"))
    private List<String> memo;

}
