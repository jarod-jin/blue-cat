package cn.jarod.bluecat.core.enums;

/**
 * @auther jarod.jin 2019/9/4
 */
public enum ReturnCode {

/*
            200 OK - [GET]：服务器成功返回用户请求的数据，该操作是幂等的（Idempotent）。
            201 CREATED - [POST/PUT/PATCH]：用户新建或修改数据成功。
            202 Accepted - [*]：表示一个请求已经进入后台排队（异步任务）
            204 NO CONTENT - [DELETE]：用户删除数据成功。
            400 INVALID REQUEST - [POST/PUT/PATCH]：用户发出的请求有错误，服务器没有进行新建或修改数据的操作，该操作是幂等的。
            401 Unauthorized - [*]：表示用户没有权限（令牌、用户名、密码错误）。
            403 Forbidden - [*] 表示用户得到授权（与401错误相对），但是访问是被禁止的。
            404 NOT FOUND - [*]：用户发出的请求针对的是不存在的记录，服务器没有进行操作，该操作是幂等的。
            406 Not Acceptable - [GET]：用户请求的格式不可得（比如用户请求JSON格式，但是只有XML格式）。
            410 Gone -[GET]：用户请求的资源被永久删除，且不会再得到的。
            422 Unprocessable entity - [POST/PUT/PATCH] 当创建一个对象时，发生一个验证错误。
            500 INTERNAL SERVER ERROR - [*]：服务器发生错误，用户将无法判断发出的请求是否成功。
*/

    GET_SUCCESS(200,"查询成功"),

    SAVE_SUCCESS(201,"保存成功"),

    REQUEST_ACCEPT(202,"请求已接受"),

    DEL_SUCCESS(204,"删除成功"),

    INVALID_REQUEST(400,"请求错误"),

    UNAUTHORIZED(401,"用户没有权限"),

    FORBIDDEN (403,"请求被禁止"),

    NOT_FOUND(404,"没有找到相关数据"),

    NOT_ACCEPTABLE(406,"请求的格式错误"),

    GONE (410,"资源被永久删除"),

    UNPROCESSABLE_ENTITY(422,"参数验证错误"),

    SERVER_ERROR(500,"服务器内部错误，请联系管理员"),

    SERVER_TIMEOUT(503,"服务器连接超时，请稍后重试");

    private Integer code;

    private String msg;

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
