package com.xianjinxia.trade.platform.status;

import org.apache.commons.lang3.StringUtils;

/**
 * db中的字段长度为32位，在定义的时候注意不要超过32位
 */
public enum PlatformTraceOrderEventEnum {

    PLATFORM_LOAN_APPLY_SUCCESS("PLATFORM_LOAN_APPLY_SUCCESS", "申请提交成功"),
    PLATFORM_BIND_CARD_FAIL("PLATFORM_BIND_CARD_FAIL", "绑卡失败"),
    PLATFORM_BIND_CARD_SUCCESS("PLATFORM_BIND_CARD_SUCCESS", "审批授信"),
    PLATFORM_LOAN_AUDIT("PLATFORM_LOAN_AUDIT","审核中"),
    PLATFORM_AUDIT_FAIL("PLATFORM_RISK_DATA_FAIL", "审核不通过"),
    PLATFORM_RISK_DATA_PASS("PLATFORM_RISK_DATA_PASS", "放款中"),
    PLATFORM_PAYMENT_SUCCESS("PLATFORM_PAYMENT_SUCCESS", "放款成功"),
    PLATFORM_PAYMENT_FAIL("PLATFORM_PAYMENT_FAIL", "放款失败"),
    PLATFORM_CANCEL("PLATFORM_CANCEL", "已取消"),
	PLATFORM_LOAN_FAIL("PLATFORM_LOAN_FAIL","借款失败");

    

    PlatformTraceOrderEventEnum(String code, String text) {
        this.code = code;
        this.text = text;
    }

    private String code;

    private String text;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    
    public static PlatformTraceOrderEventEnum getEnumByCode(String code) {
    	PlatformTraceOrderEventEnum[] values = PlatformTraceOrderEventEnum.values();
		for(PlatformTraceOrderEventEnum v:values) {
			if(StringUtils.equals(code, v.getCode())) {
				return v;
			}
		}
		return null;
	}
}