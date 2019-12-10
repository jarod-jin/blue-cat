package cn.jarod.bluecat.analyst.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * @auther jarod.jin 2019/12/9
 */
@Getter
@Setter
public class EducationDO {

    private String schoolName;

    private LocalDate startDate;

    private LocalDate endDate;

    private String eduType;

    public EducationDO(String schoolName, LocalDate startDate, LocalDate endDate, String eduType){
        this.schoolName = schoolName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.eduType = eduType;
    }

}
