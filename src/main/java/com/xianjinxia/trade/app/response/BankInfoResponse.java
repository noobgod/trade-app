package com.xianjinxia.trade.app.response;

import java.io.Serializable;

/**
 * 获取银行信息响应类
 * @author liuzhifang
 *
	2017年9月7日
 */
public class BankInfoResponse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String RESPONSE_CODE_SUCC="00";
	/**
	 * 银行行号
	 */
	private String bankNumber;
	/***
	 * 银行卡号
	 */
	private String cardNo;
	/**
	 * 用户名
	 */
	private String openName;
	/**
	 * 银行名称
	 */
	private String bankName;
	
	/**
	 * 持卡人电话
	 */
	private String phone;
	
	
	public String getBankNumber() {
		return bankNumber;
	}
	public void setBankNumber(String bankNumber) {
		this.bankNumber = bankNumber;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getOpenName() {
		return openName;
	}
	public void setOpenName(String openName) {
		this.openName = openName;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}

}
