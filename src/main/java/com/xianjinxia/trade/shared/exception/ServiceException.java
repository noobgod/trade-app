package com.xianjinxia.trade.shared.exception;

public class ServiceException  extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private String code;

	private String msg;
	
	public ServiceException(String msg) {
		super(msg);
		this.msg = msg;
	}

	public ServiceException(String code, String msg) {
		super(msg);
		this.code = code;
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}

	@Override
	public String toString() {
		return "ServiceException{" +
				"code='" + code + '\'' +
				", msg='" + msg + '\'' +
				'}';
	}
}
