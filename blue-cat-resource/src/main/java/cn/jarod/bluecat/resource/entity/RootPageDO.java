package cn.jarod.bluecat.resource.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;


/**
 * @author jarod.jin 2019/10/15
 */
@Getter
@Setter
@ToString
@Document("root_page")
public class RootPageDO extends PageDO{

    /**终端标识*/
    @Field("terminalVersion")
    private String terminalVersion;

    /**关联系统编号*/
    @Field("belongTo")
    private String belongTo;

}
