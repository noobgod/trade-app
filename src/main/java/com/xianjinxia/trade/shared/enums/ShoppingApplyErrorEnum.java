package com.xianjinxia.trade.shared.enums;

public enum ShoppingApplyErrorEnum {
	
	SUCCESS("00", "成功"),

	CHECK_BASIC_AUTH("01", "您当前需完成基础和高级认证方可下单"),

	CHECK_REJECTORDER("02", "授信进行中,无法下单");

	/**
	 * 名称
	 */
	private final String code;

	/**
	 * 值
	 */
	private final String value;

	ShoppingApplyErrorEnum(String code, String value) {
		this.code = code;
		this.value = value;
	}

	/**
	 * 获取名称
	 * 
	 * @return
	 */
	public String getCode() {
		return code;
	}

	/**
	 * 获取值
	 * 
	 * @return
	 */
	public String getValue() {

		return value;
	}

}
