package cn.jarod.bluecat.resource.entity;

import cn.jarod.bluecat.core.entity.NoSqlEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @author jarod.jin 2019/10/14
 */
@Getter
@Setter
@ToString
@Document("file")
public class FileDO extends NoSqlEntity {

    /**绝对路径*/
    @Field("absolutePath")
    private String absolutePath;

    /**相对路径*/
    @Field("relativePath")
    private String relativePath;

    /**文件名*/
    @Field("fileName")
    private String fileName;

    /**原始文件名*/
    @Field("originalName")
    private String originalName ;

    /**后缀*/
    @Field("suffix")
    private String suffix;

    /**关联系统编号*/
    @Field("belongTo")
    private String belongTo;

}
