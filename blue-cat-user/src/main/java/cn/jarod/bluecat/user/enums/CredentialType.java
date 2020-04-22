package cn.jarod.bluecat.user.enums;

import java.util.Arrays;

/**
 * @author jarod.jin 2019/9/9
 */
public enum CredentialType {
    /**普通用户*/
    normal(0,"个人级用户"),

    /**系统用户*/
    server(1,"系统级用户");

    private int code;

    private String memo;

    CredentialType(int code, String memo){
        this.code = code;
        this.memo = memo;
    }

    public int getCode(){
        return code;
    }

    public String getMemo(){
        return memo;
    }

    public static String findMemo(int code){
        return Arrays.stream(CredentialType.values())
                .filter(e->code==e.code)
                .findFirst()
                .orElse(normal)
                .memo;
    }

}
