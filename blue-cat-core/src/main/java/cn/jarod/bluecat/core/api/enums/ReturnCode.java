package cn.jarod.bluecat.core.api.enums;

/**
 * @author jarod.jin 2019/9/4
 */
public enum ReturnCode {

    /**成功返回
     * OK - [GET]：服务器成功返回用户请求的数据，该操作是幂等的（Idempotent）
     */
    REQUEST_SUCCESS(20000,"请求返回成功"),

    /**Accepted - [*]：表示一个请求已经进入后台排队（异步任务）*/
    REQUEST_ACCEPT(20010,"请求已接受"),

    /**请求问题返回
     * Unauthorized - [*]：表示用户没有权限（令牌、用户名、密码错误）。
     */
    UNAUTHORIZED(40010,"表示用户没有权限（令牌、用户名、密码错误）"),

    /**Forbidden - [*] 表示用户得到授权（与401错误相对），但是访问是被禁止的。*/
    FORBIDDEN (40030,"用户未被授权该访问，请求被禁止"),

    /**INVALID REQUEST - [POST/PUT/PATCH]：用户发出的请求有错误，服务器没有进行新建或修改数据的操作，该操作是幂等的。*/
    NOT_ACCEPTABLE(40001,"请求方式错误，请注意API的请求方式，比如[POST/PUT/PATCH]"),

    /**Not Acceptable - [GET]：用户请求的格式不可得（比如用户请求JSON格式，但是只有XML格式）*/
    INVALID_REQUEST(40006,"用户请求的格式错误,请注意请求的Content-type"),

    /**ENTITY EXISTED - 当创建一个对象时，数据已存在。*/
    ENTITY_EXISTED(40021,"数据已存在"),

    /**Unprocessable entity - [POST/PUT/PATCH] 当创建一个对象时，发生一个验证错误。*/
    UNPROCESSABLE_ENTITY(40022,"请求参数验证错误"),

    /**NOT FOUND - [*]：用户发出的请求针对的是不存在的记录，服务器没有进行操作，该操作是幂等的。*/
    NOT_FOUND(40040,"没有找到相关数据"),

    /**Gone -[GET]：用户请求的资源被永久删除，且不会再得到的。*/
    GONE (40041,"资源被永久删除"),


    /**INTERNAL SERVER ERROR - [*]：服务器发生错误，用户将无法判断发出的请求是否成功。*/
    SERVER_ERROR(50010,"服务器内部错误，请联系管理员"),

    SERVER_DOWN(50020,"服务器离线，请联系管理员"),

    SERVER_TIMEOUT(50030,"服务器连接超时，请稍后重试");

    private final Integer code;

    private final String msg;

    ReturnCode(Integer code,String msg){
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode(){
        return code;
    }

    public String getMsg(){
        return msg;
    }

}
