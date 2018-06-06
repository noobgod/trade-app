package com.xianjinxia.trade.shopping.response.soouu;

public class SoouuFailResponse {

	private String messageCode;
	private String messageInfo;

	public String getMessageCode() {
		return messageCode;
	}

	public void setMessageCode(String messageCode) {
		this.messageCode = messageCode;
	}

	public String getMessageInfo() {
		return messageInfo;
	}

	public void setMessageInfo(String messageInfo) {
		this.messageInfo = messageInfo;
	}

	@Override
	public String toString() {
		return "SoouuFailResponse{" + "messageCode=" + messageCode + ", messageInfo='" + messageInfo + '}';
	}
}
