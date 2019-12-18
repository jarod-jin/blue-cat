package cn.jarod.bluecat.core.model;

import cn.jarod.bluecat.core.enums.*;
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
public class ResultDTO<T> implements Serializable {
    private static final long serialVersionUID = -5288702993752277282L;

    private Integer code;
    private String msg;
    private T data;

    public ResultDTO() {
    }

    public ResultDTO(Integer code, String resultMessage, T data) {
        this.code = code;
        this.msg = resultMessage;
        this.data = data;
    }

    public ResultDTO(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResultDTO(ReturnCode returnCode) {
        this.code = returnCode.getCode();
        this.msg = returnCode.getMsg();
    }

    public ResultDTO(ReturnCode returnCode, T data) {
        this.code = returnCode.getCode();
        this.msg = returnCode.getMsg();
        this.data = data;
    }

}
