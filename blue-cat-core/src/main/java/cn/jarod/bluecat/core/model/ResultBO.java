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
public class ResultBO implements Serializable {
    private static final long serialVersionUID = -5288702993752277282L;

    private String resultCode;
    private String resultMessage;
    private Object data;

    public ResultBO() {
    }

    public ResultBO(String resultCode, String resultMessage, Object data) {
        this.resultCode = resultCode;
        this.resultMessage = resultMessage;
        this.data = data;
    }


    public ResultBO(String code, String msg) {
        this.resultCode = code;
        this.resultMessage = msg;
    }

    public ResultBO(ReturnCode returnCode) {
        this.resultCode = returnCode.getCode();
        this.resultMessage = returnCode.getMsg();
    }

    public ResultBO(ReturnCode returnCode, Object data) {
        this.resultCode = returnCode.getCode();
        this.resultMessage = returnCode.getMsg();
        this.data = data;
    }

}
