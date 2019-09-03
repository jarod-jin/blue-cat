package cn.jarod.bluecat.core.model;

import cn.jarod.bluecat.core.enums.ReturnCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @auther jarod.jin 2019/9/3
 */
@Setter
@Getter
public class ResultVO implements Serializable {
    private static final long serialVersionUID = -5288702993752277282L;

    private String resultCode;
    private String resultMessage;
    private Object data;

    public ResultVO() {
    }

    public ResultVO(String resultCode, String resultMessage, Object data) {
        this.resultCode = resultCode;
        this.resultMessage = resultMessage;
        this.data = data;
    }


    public ResultVO(String code, String msg) {
        this.resultCode = code;
        this.resultMessage = msg;
    }

    public ResultVO(ReturnCode returnCode) {
        this.resultCode = returnCode.getCode();
        this.resultMessage = returnCode.getMsg();
    }

    /**
     * 保存成功
     * @return
     */
    public static ResultVO saveSuccess(Object data){
        return new ResultVO(ReturnCode.Q200.getCode(),ReturnCode.Q200.getMsg(),data);
    }
    /**
     * 操作成功
     */
    public static  ResultVO handleSuccess(){
        return new ResultVO(ReturnCode.S200.getCode(),ReturnCode.S200.getMsg());
    }

    /**
     * 操作成功
     */
    public static ResultVO querySuccess(Object data){
        return new ResultVO(ReturnCode.S200.getCode(), ReturnCode.S200.getMsg(), data);
    }

}
