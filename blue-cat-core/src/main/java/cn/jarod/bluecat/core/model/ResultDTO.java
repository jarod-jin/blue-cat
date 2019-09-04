package cn.jarod.bluecat.core.model;

import cn.jarod.bluecat.core.enums.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @auther jarod.jin 2019/9/3
 */
@Setter
@Getter
public class ResultDTO<T extends BaseDTO> implements Serializable {
    private static final long serialVersionUID = -5288702993752277282L;

    private String resultCode;
    private String resultMessage;
    private T data;

    public ResultDTO() {
    }

    public ResultDTO(String resultCode, String resultMessage, T data) {
        this.resultCode = resultCode;
        this.resultMessage = resultMessage;
        this.data = data;
    }


    public ResultDTO(String code, String msg) {
        this.resultCode = code;
        this.resultMessage = msg;
    }

    public ResultDTO(ReturnCode returnCode) {
        this.resultCode = returnCode.getCode();
        this.resultMessage = returnCode.getMsg();
    }

}
