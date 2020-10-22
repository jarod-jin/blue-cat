package cn.jarod.bluecat.core.oauth.pojo;

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
public class DataPermissions {

    private String objectPerm;

    private List<DataShareRules> shareRules;

    public DataPermissions(String objectPerm){
        this.objectPerm = objectPerm;
    }

}
