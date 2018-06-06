package com.xianjinxia.trade.platform.status;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by liquan on 2017/11/23.
 */
public enum LoanOrderStatus {

    NEW("NEW", "新申请", "新申请订单，未推送第三方", "", null,null),
    NEW_PUSH_SUCCESS("NEW_PUSH_SUCCESS", "推送成功", "申请订单推送成功，待用户确认订单", "", null,Arrays.asList(new LoanOrderStatus[] {NEW})),
    CONFIRM_PRE("CONFIRM_PRE", "待确认", "绑卡成功，订单处于用户待确认状态", "", null,null),
    CONFIRM_PUSHING("CONFIRM_PUSHING", "确认推送中", "用户确认待推送确认信息与第三方", "", null,null),
    CONFIRM_PUSH_SUCCESS("CONFIRM_PUSH_SUCCESS", "确认推送成功", "推送确认订单成功，待第三方审核", "140", PlatformTraceOrderEventEnum.PLATFORM_LOAN_AUDIT,Arrays.asList(new LoanOrderStatus[] {CONFIRM_PUSHING})),
    REFUSED("REFUSED", "审核拒绝", "订单被拒，终态", "159", PlatformTraceOrderEventEnum.PLATFORM_AUDIT_FAIL,Arrays.asList(new LoanOrderStatus[] {CONFIRM_PUSH_SUCCESS})),
    APPROVED("APPROVED", "审核通过", "订单审核通过，放款中", "150", PlatformTraceOrderEventEnum.PLATFORM_RISK_DATA_PASS,Arrays.asList(new LoanOrderStatus[] {CONFIRM_PUSH_SUCCESS})),
    WITHDRAW_FAIL("WITHDRAW_FAIL", "放款失败", "放款失败", "169", PlatformTraceOrderEventEnum.PLATFORM_PAYMENT_FAIL,Arrays.asList(new LoanOrderStatus[] {CONFIRM_PUSH_SUCCESS,APPROVED})),
    WITHHOLDING("WITHHOLDING", "待还款", "放款成功，待还款", "170", PlatformTraceOrderEventEnum.PLATFORM_PAYMENT_SUCCESS,Arrays.asList(new LoanOrderStatus[] {CONFIRM_PUSH_SUCCESS,APPROVED})),
    REPAYMENT("REPAYMENT", "还款中", "用户有在途还款", "175", null,Arrays.asList(new LoanOrderStatus[] {WITHHOLDING})), 
    OVERDUE("OVERDUE", "逾期", "逾期", "180", null,Arrays.asList(new LoanOrderStatus[] {WITHHOLDING,REPAYMENT})),
    SETTLED("SETTLED", "结清", "已还清，终态", "200", null,Arrays.asList(new LoanOrderStatus[] {CONFIRM_PUSH_SUCCESS,APPROVED,WITHHOLDING,REPAYMENT,OVERDUE})),
    CANCEL("CANCEL", "取消", "终态", "161", PlatformTraceOrderEventEnum.PLATFORM_CANCEL,Arrays.asList(new LoanOrderStatus[] {NEW,NEW_PUSH_SUCCESS,CONFIRM_PRE})),
    PUSH_FAIL("PUSH_FAIL", "推送失败", "订单推送多次给第三方仍失败", "", null,null),
	CHECK_FAIL("CHECK_FAIL", "第三方业务校验失败", "第三方对于订单信息的校验失败，用户需要重新下单", "", null,Arrays.asList(new LoanOrderStatus[] {NEW})),
    FAIL("FAIL", "异常失败", "异常失败，终态", "", PlatformTraceOrderEventEnum.PLATFORM_BIND_CARD_FAIL,null);

    private String value;
    private String desc;
    private String remark;
    private String partnerOrderStatus;//第三方订单状态
    private PlatformTraceOrderEventEnum traceStatus; //对应trace枚举
    private List<LoanOrderStatus> preStatus;//前置状态的枚举列表

    
	private LoanOrderStatus(String value, String desc, String remark, String partnerOrderStatus,
			PlatformTraceOrderEventEnum traceStatus, List<LoanOrderStatus> preStatus) {
		Arrays.asList(new String[] {});
		this.value = value;
		this.desc = desc;
		this.remark = remark;
		this.partnerOrderStatus = partnerOrderStatus;
		this.traceStatus = traceStatus;
		this.preStatus = preStatus;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getPartnerOrderStatus() {
		return partnerOrderStatus;
	}

	public void setPartnerOrderStatus(String partnerOrderStatus) {
		this.partnerOrderStatus = partnerOrderStatus;
	}

	public PlatformTraceOrderEventEnum getTraceStatus() {
		return traceStatus;
	}

	public void setTraceStatus(PlatformTraceOrderEventEnum traceStatus) {
		this.traceStatus = traceStatus;
	}

	public List<LoanOrderStatus> getPreStatus() {
		return preStatus;
	}

	public void setPreStatus(List<LoanOrderStatus> preStatus) {
		this.preStatus = preStatus;
	}

	public static LoanOrderStatus getEnumByPartnerOrderStatus(String partnerOrderStatus) {
        LoanOrderStatus[] values = LoanOrderStatus.values();
        for (LoanOrderStatus v : values) {
            if (StringUtils.equals(partnerOrderStatus, v.getPartnerOrderStatus())) {
                return v;
            }
        }
        return null;
    }

    public static LoanOrderStatus getEnumByValue(String value) {
        LoanOrderStatus[] values = LoanOrderStatus.values();
        for (LoanOrderStatus loanOrderStatus : values) {
            if (StringUtils.equals(value, loanOrderStatus.getValue())) {
                return loanOrderStatus;
            }
        }
        return null;
    }

    public static boolean isNeedRecordTrace(String value) {
    	LoanOrderStatus[] needRecordTraceArray = {CONFIRM_PUSH_SUCCESS, REFUSED, APPROVED,WITHDRAW_FAIL,WITHHOLDING,CANCEL};
        for(LoanOrderStatus loanOrderStatus:needRecordTraceArray) {
        	if(loanOrderStatus.getValue().equals(value)) {
				return true;
			}
        }
        return false;
    }

	public static boolean isCancelStatus(String value) {
		LoanOrderStatus[] cancelStatusArray = {NEW,NEW_PUSH_SUCCESS,CONFIRM_PRE};
		for(LoanOrderStatus loanOrderStatus:cancelStatusArray) {
			if(loanOrderStatus.getValue().equals(value)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isAllowApplyStatus(String value) {
		LoanOrderStatus[] cancelStatusArray = {NEW,NEW_PUSH_SUCCESS,CONFIRM_PRE,CONFIRM_PUSHING,APPROVED,WITHHOLDING,REPAYMENT,OVERDUE};
		for(LoanOrderStatus loanOrderStatus:cancelStatusArray) {
			if(loanOrderStatus.getValue().equals(value)) {
				return false;
			}
		}
		return true;
	}
	
	public static boolean noNeedGetStatus(String value) {
		LoanOrderStatus[] noNeedGetStatus = {NEW,NEW_PUSH_SUCCESS,CONFIRM_PRE,CONFIRM_PUSHING};
		for(LoanOrderStatus loanOrderStatus:noNeedGetStatus) {
			if(loanOrderStatus.getValue().equals(value)) {
				return true;
			}
		}
		return false;
	}
   
}
