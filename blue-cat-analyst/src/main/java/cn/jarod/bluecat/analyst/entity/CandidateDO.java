package cn.jarod.bluecat.analyst.entity;

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
public class CandidateDO {

    @Id
    private ObjectId id;

    @Field("name")
    private String name;

    @Field("gander")
    private String gander;

    @Field("age")
    private Integer age;

    @Field("workAge")
    private Integer workAge;

    @Field("skills")
    private List<String> skills;

    @Field("educations")
    private List<EducationDO> educationList;

    @Field("jobs")
    private List<JobDO> jobList;

    @Field("projects")
    private List<WorkProjectDO> workProjectList;

    public CandidateDO(ObjectId objectId) {
        this.id = objectId;
    }
}
