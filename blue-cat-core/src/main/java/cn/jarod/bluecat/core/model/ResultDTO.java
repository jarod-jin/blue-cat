package cn.jarod.bluecat.core.model;

import cn.jarod.bluecat.core.common.ReturnCode;
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
public class ResultDTO implements Serializable {
    private static final long serialVersionUID = -5288702993752277282L;

    private Integer code;
    private String msg;
    private Object data;

    public ResultDTO() {
    }

    public ResultDTO(Object data) {
        this.code = ReturnCode.GET_SUCCESS.getCode();
        this.msg = ReturnCode.GET_SUCCESS.getMsg();
        this.data = data;
    }

    public ResultDTO(Integer code, String resultMessage, Object data) {
        this.code = code;
        this.msg = resultMessage;
        this.data = data;
    }

    public boolean isSuccessful(){
        return this.code !=null && this.code < 300 && this.code > 199;
    }
}
