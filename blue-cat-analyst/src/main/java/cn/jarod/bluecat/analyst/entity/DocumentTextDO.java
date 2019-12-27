package cn.jarod.bluecat.analyst.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author jarod.jin 2019/12/5
 */
@Document
@Getter
@Setter
@ToString
public class DocumentTextDO {

    @Id
    private ObjectId id;

    @Field("subject")
    private String subject;

    @Field("context")
    private List<String> contextList;

    @Field("creator")
    private String creator;

    @Field("createTime")
    private LocalDateTime createTime;

    public DocumentTextDO(ObjectId id){
        this.id = id;
        this.createTime = LocalDateTime.now();
    }
}
