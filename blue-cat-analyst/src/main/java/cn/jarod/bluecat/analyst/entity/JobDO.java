package cn.jarod.bluecat.analyst.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @auther jarod.jin 2019/12/9
 */
@Getter
@Setter
public class JobDO {

    private String companyName;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private Integer jobAge;

    private String jobName;

}
