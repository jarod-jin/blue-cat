package cn.jarod.bluecat.general.entity;

import cn.jarod.bluecat.core.entity.BaseEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import java.util.Map;

/**
 * @author jarod.jin 2019/10/14
 */
@Entity
@Getter
@Setter
@ToString
@DynamicInsert
@DynamicUpdate
@EqualsAndHashCode(callSuper=true)
@Table(name = "dict_entry", indexes = {@Index(columnList ="dictCode", name="DictCodeIndex", unique = true)})
public class DictEntryDO extends BaseEntity {

    /**字典类别*/
    @Column(nullable = false, columnDefinition=("varchar(20) comment '字典类别编码'"))
    private String dictCode;

    /**字典键-值*/
    @Type(type = "json")
    @Column(nullable = false, columnDefinition=("json comment '字典键-值'"))
    private Map<String,Object> entryJson;

    /**显示顺序*/
    @Column(nullable = false, columnDefinition=("smallint(5) default 99 comment '显示顺序'"))
    private Integer disOrder;

    /**描述*/
    @Column(nullable = false, columnDefinition=("varchar(500) default '' comment '描述'"))
    private String memo;

    /**关联系统编号*/
    @Column(nullable = false, columnDefinition=("varchar(30) default 'root' comment '关联系统编号'"))
    private String sysCode;

}
