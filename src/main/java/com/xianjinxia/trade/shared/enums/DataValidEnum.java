package com.xianjinxia.trade.shared.enums;

/**
 * 数据有效性
 * @author liuzhifang
 *
	2017年9月8日
 */
public enum DataValidEnum {

	DATA_VALID_YES(Boolean.TRUE,"有效"),
	DATA_VALID_NO(Boolean.FALSE,"无效");

	    private Boolean code;
	    private String value;

	    DataValidEnum(Boolean code, String value) {
	        this.code = code;
	        this.value = value;
	    }

	    public Boolean getCode() {
	        return code;
	    }

	    public void setCode(Boolean code) {
	        this.code = code;
	    }

	    public String getValue() {
	        return value;
	    }

	    public void setValue(String value) {
	        this.value = value;
	    }
}
