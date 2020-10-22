package cn.jarod.bluecat.core.security.pojo;

import lombok.*;

import java.util.List;

/**
 * @author Jarod Jin E-mail:kira277@163.com
 * @version 创建时间：2020/3/21
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DataPermissionDO {

    private static final char AN_OBJECT = '1';

    private String name;

    private String memo;

    private boolean read;

    private boolean create;

    private boolean update;

    private boolean delete;

    private boolean readAll;

    private boolean updateAll;

    private List<DataShareRuleDO> shareRules;

    public DataPermissionDO(String analysisString, List<DataShareRuleDO> shareRules){
        char[] arr = analysisString.toCharArray();
        this.read = arr[0]==AN_OBJECT;
        this.create = arr[1]==AN_OBJECT;
        this.update = arr[2]==AN_OBJECT;
        this.delete = arr[3]==AN_OBJECT;
        this.readAll = arr[4]==AN_OBJECT;
        this.updateAll = arr[5]==AN_OBJECT;
        this.shareRules = shareRules;
    }

}
