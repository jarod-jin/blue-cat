package cn.jarod.bluecat.analyst.entity;

import cn.jarod.bluecat.core.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

/**
 * @auther jarod.jin 2019/12/9
 */
@Document
@Getter
@Setter
public class ResumeDO extends BaseEntity {

    @Id
    private ObjectId id;

    @Field("candidate")
    private String candidate;

    @Field("gander")
    private String gander;

    @Field("workAge")
    private Integer workAge;

    @Field("skill")
    private String skill;

    @Field("education")
    private EducationDO education;

    @Field("job")
    private List<JobDO> jobList;

    @Field("project")
    private List<WorkProjectDO> workProjectList;

}
