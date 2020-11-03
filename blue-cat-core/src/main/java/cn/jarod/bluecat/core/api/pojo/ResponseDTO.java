package cn.jarod.bluecat.core.api.pojo;

import cn.jarod.bluecat.core.api.enums.ReturnCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author jarod.jin 2019/9/3
 */
@Setter
@Getter
@ToString
public class ResponseDTO<T> implements Serializable {
    private static final long serialVersionUID = -5288702993752277282L;

    private Integer code;
    private String msg;
    private T data;

    private ResponseDTO(T data) {
        this.code = ReturnCode.REQUEST_SUCCESS.getCode();
        this.msg = ReturnCode.REQUEST_SUCCESS.getMsg();
        this.data = data;
    }

    private ResponseDTO(ReturnCode returnCode) {
        if(returnCode == null){
            return;
        }
        this.code = returnCode.getCode();
        this.msg = returnCode.getMsg();
    }

    private ResponseDTO(ReturnCode returnCode, String msg) {
        if(returnCode == null){
            return;
        }
        this.code = returnCode.getCode();
        this.msg = returnCode.getMsg()+"--"+msg;
    }
    /**
     * 成功时候的调用
     * @return
     */
    public static <T> ResponseDTO<T> success(T data){
        return new ResponseDTO<T>(data);
    }
    /**
     * 成功，不需要传入参数
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> ResponseDTO<T> success(){
        return (ResponseDTO<T>) success("");
    }

    /**
     * 成功时候的调用
     * @return
     */
    public static <T> ResponseDTO<T> accept(T data){
        ResponseDTO<T> responseDTO = new ResponseDTO(ReturnCode.REQUEST_ACCEPT);
        responseDTO.setData(data);
        return responseDTO;
    }
    /**
     * 失败时候的调用
     * @return
     */
    public static <T> ResponseDTO<T> error(ReturnCode returnCode){
        return new ResponseDTO<T>(returnCode);
    }
    /**
     * 失败时候的调用,扩展消息参数
     * @param returnCode
     * @param msg
     * @return
     */
    public static <T> ResponseDTO<T> error(ReturnCode returnCode, String msg){
        return new ResponseDTO<T>(returnCode, msg);
    }

    public boolean isSuccessful(){
        return this.code !=null && this.code < 300 && this.code > 199;
    }
}
