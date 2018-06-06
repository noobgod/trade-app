package com.xianjinxia.trade.shared.enums;

import org.apache.commons.lang3.StringUtils;

public enum TraceOrderEventEnum {

//    PLATFORM_LOAN_APPLY_INIT("PLATFORM_LOAN_APPLY_INIT", "借款申请初始化"),
//    PLATFORM_LOAN_APPLY_RECEIVED("PLATFORM_LOAN_APPLY_RECEIVED", "借款申请合作机构已收到"),
//    PLATFORM_BIND_CARD_FAIL("PLATFORM_BIND_CARD_FAIL", "绑卡失败"),
//    PLATFORM_BIND_CARD_SUCCESS("PLATFORM_BIND_CARD_SUCCESS", "绑卡成功"),
//    PLATFORM_RISK_DATA_PASS("PLATFORM_RISK_DATA_PASS", "订单审核通过，放款中"),
//    PLATFORM_RISK_DATA_FAIL("PLATFORM_RISK_DATA_FAIL", "订单审核拒绝"),
//    PLATFORM_SETTLED("PLATFORM_SETTLED", "已结清"),
//    PLATFORM_OVERDUE("PLATFORM_OVERDUE", "已逾期"),
//    PLATFORM_CANCEL("PLATFORM_CANCEL", "已取消"),
//    PLATFORM_REPAYING("PLATFORM_REPAYING", "还款中"),
	CREATE_TRACE("CREATE_TRACE_NO", "创建追踪号"),
    LOAN_LOGISTICS_SHIPMENT("LOAN_LOGISTICS_SHIPMENT", "订单发货,待处理"),
    LOAN_APPLY("LOAN_APPLY", "申请提交成功"),
    LOAN_CONFIRM("LOAN_CONFIRM", "审核中"),
    RISK_DATA_FAIL("RISK_DATA_FAIL", "审核失败"),
    MANUAL_REVIEWING("MANUAL_REVIEWING", "人工审核中"),
    MANUALREVIEW_FAILURE("MANUALREVIEW_FAILURE", "人工审核失败"),
    PAYMENTING("PAYMENTING", "放款中"),
    PAYMENT_SUCCESS("PAYMENT_SUCCESS", "放款成功"),
    PAYMENT_FAIL("PAYMENT_FAIL", "放款失败"),
    THIRD_APPLY_SUCCESS("THIRD_APPLY_SUCCESS", "购买成功"),
    THIRD_APPLY_FAIL("THIRD_APPLY_FAIL", "购买失败"),
    ;

    TraceOrderEventEnum(String code, String text) {
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
    
    public static TraceOrderEventEnum getEnumByCode(String code) {
    	TraceOrderEventEnum[] values = TraceOrderEventEnum.values();
		for(TraceOrderEventEnum v:values) {
			if(StringUtils.equals(code, v.getCode())) {
				return v;
			}
		}
		return null;
	}
}