package cn.jarod.bluecat.analyst.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @auther jarod.jin 2019/12/9
 */
@Getter
@Setter
public class WorkProjectDO {

    private String companyName;

    private String projectName;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private List<String> skillsInProject;

    private String chargeOfJob;
}
