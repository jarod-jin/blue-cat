package cn.jarod.bluecat.core.model.auth;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Jarod Jin E-mail:kira277@163.com
 * @version 创建时间：2020/4/2
 */
@Setter
@Getter
@ToString
public class ConditionAnalysis {

    public static final String AN_OBJECT = "1";

    private String analysisString;

    private boolean read;

    private boolean create;

    private boolean update;

    private boolean delete;

    private boolean readAll;

    private boolean updateAll;

    public ConditionAnalysis(String analysisString){
        this.analysisString = analysisString;
        this.read = analysisString.substring(0,1).equals(AN_OBJECT);
        this.create = analysisString.substring(1,2).equals(AN_OBJECT);
        this.update = analysisString.substring(2,3).equals(AN_OBJECT);
        this.delete = analysisString.substring(3,4).equals(AN_OBJECT);
        this.readAll = analysisString.substring(4,5).equals(AN_OBJECT);
        this.updateAll = analysisString.substring(5,6).equals(AN_OBJECT);
    }

}
