package cn.jarod.bluecat.core.exception;

import cn.jarod.bluecat.core.enums.ReturnCode;
import lombok.Getter;

/**
 * @author Jarod Jin E-mail:kira277@163.com
 * @version 创建时间：2020/1/9
 */
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
