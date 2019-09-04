package cn.jarod.bluecat.core.enums;

import lombok.Getter;

/**
 * @auther jarod.jin 2019/9/3
 */
public enum ReturnCode {

    Q200("200","查询成功"),

    Q400("400","查询参数错误"),

    Q401("400","没有找到相关数据"),

    Q402("400","验证码已过期"),

    Q403("400","验证码输入有误"),

    S200("200","保存成功"),

    S400("400","保存数据有误"),

    S401("400","请勿重复提交"),

    S402("400","此电话已经被注册"),

    D200("200","删除成功"),

    D400("400","删除条件有误"),

    R500("500","服务器内部错误"),

    R503("503","服务器连接超时，请稍后重试");

    @Getter
    String code;

    @Getter
    String msg;

    ReturnCode(String code,String msg){
        this.code = code;
        this.msg = msg;
    }
}
