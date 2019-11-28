package cn.jarod.bluecat.core.enums;

/**
 * @auther jarod.jin 2019/9/4
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

    S402("400","已经被注册"),

    S405("400","必填数据不能为空"),

    D200("200","删除成功"),

    D400("400","删除条件有误"),

    R500("500","服务器内部错误，请联系管理员"),

    R501("501","服务器内部参数错误，请检查数据配置"),

    R503("503","服务器连接超时，请稍后重试");

    private String code;

    private String msg;

    ReturnCode(String code,String msg){
        this.code = code;
        this.msg = msg;
    }

    public String getCode(){
        return code;
    }

    public String getMsg(){
        return msg;
    }

}
