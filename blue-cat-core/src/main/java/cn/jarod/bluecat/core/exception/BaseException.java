package cn.jarod.bluecat.core.exception;

import cn.jarod.bluecat.core.enums.ReturnCode;
import lombok.Getter;

public class BaseException extends RuntimeException {

	private static final long serialVersionUID = -1686070930470829087L;
	@Getter
	private Integer code;
	@Getter
	private String msg;

	public BaseException(Integer errorCode, String errorMessage) {
		super(errorMessage);
		this.code = errorCode;
		this.msg = errorMessage;
	}

	public BaseException(ReturnCode returnCode) {
		super(returnCode.getMsg());
		this.code = returnCode.getCode();
		this.msg = returnCode.getMsg();
	}


}
