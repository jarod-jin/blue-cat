package cn.jarod.bluecat.core.exception;

import cn.jarod.bluecat.core.enums.*;
import lombok.Getter;

public class BaseException extends RuntimeException {

	private static final long serialVersionUID = -1686070930470829087L;
	@Getter
	private String errorCode;
	@Getter
	private String errorMessage;

	public BaseException(String errorCode, String errorMessage) {
		super(errorMessage);
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	public BaseException(ReturnCode returnCode) {
		super(returnCode.getMsg());
		this.errorCode = returnCode.getCode();
		this.errorMessage = returnCode.getMsg();
	}
}
