package com.xianjinxia.trade.shared.enums;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 
 * @author chunliny on 2018/1/17
 *
 */
public enum ShoppingLoanOrderStatusEnum {


//	NEW("10", "新申请", false, Arrays.asList(new LoanOrderStatusEnum[]{})),
//	NEW_PUSH_SUCCESS("0", "推送订单给集团风控,推送成功", false, Arrays.asList(new LoanOrderStatusEnum[]{NEW}),TraceOrderEventEnum.LOAN_CONFIRM),
//	MANUAL_REVIEWING("30", "推送订单给集团风控,待人工审核", false, Arrays.asList(new LoanOrderStatusEnum[]{NEW,NEW_PUSH_SUCCESS}),TraceOrderEventEnum.MANUAL_REVIEWING),
//	REFUSED("-3", "推送订单给集团风控,审核拒绝", true, Arrays.asList(new LoanOrderStatusEnum[]{NEW,NEW_PUSH_SUCCESS,MANUAL_REVIEWING}),TraceOrderEventEnum.RISK_DATA_FAIL),
//	APPROVED("11", "推送订单给集团风控,审核通过", false, Arrays.asList(new LoanOrderStatusEnum[]{NEW,NEW_PUSH_SUCCESS,MANUAL_REVIEWING}),TraceOrderEventEnum.PAYMENTING),
//	MANUAL_APPROVED("31", "人工审核订单结果, 人工审核通过", false, Arrays.asList(new LoanOrderStatusEnum[]{MANUAL_REVIEWING}),TraceOrderEventEnum.PAYMENTING),
//	MANUAL_REFUSED("-30", "人工审核订单结果, 人工审核拒绝", false, Arrays.asList(new LoanOrderStatusEnum[]{MANUAL_REVIEWING}),TraceOrderEventEnum.MANUALREVIEW_FAILURE),
//	LOANING("22", "推送订单至支付中心放款,放款中", false, Arrays.asList(new LoanOrderStatusEnum[]{APPROVED,MANUAL_APPROVED})),
//	LOAN_SUCCESS("21", "支付中心通知放款结果,放款成功", false, Arrays.asList(new LoanOrderStatusEnum[]{LOANING}),TraceOrderEventEnum.PAYMENT_SUCCESS),
//	LOAN_FAIL("-10", "支付中心通知放款结果,放款失败", false, Arrays.asList(new LoanOrderStatusEnum[]{LOANING}),TraceOrderEventEnum.PAYMENT_FAIL),
//	SETTLED("50", "还款结果,结清", true, Arrays.asList(new LoanOrderStatusEnum[]{LOAN_SUCCESS})),
//	OVERDUE("40", "还款结果,逾期", false, Arrays.asList(new LoanOrderStatusEnum[]{LOAN_SUCCESS})),
//	CANCEL("-22", "取消", true, Arrays.asList(new LoanOrderStatusEnum[]{})),
//	PUSH_FAIL("-12", "风控,推送失败", false,Arrays.asList(new LoanOrderStatusEnum[]{NEW})),
//	FAIL("-11", "异常,失败", false, Arrays.asList(new LoanOrderStatusEnum[]{}));


	// 新增的初始化状态
	APPLY("9", "立即申请", false, Arrays.asList(new ShoppingLoanOrderStatusEnum[]{})),

	NEW(LoanOrderStatusEnum.NEW.getCode(), LoanOrderStatusEnum.NEW.getValue(), LoanOrderStatusEnum.NEW.isFinalStatus(), Arrays.asList(new ShoppingLoanOrderStatusEnum[]{APPLY})),
	NEW_PUSH_SUCCESS(LoanOrderStatusEnum.NEW_PUSH_SUCCESS.getCode(), LoanOrderStatusEnum.NEW_PUSH_SUCCESS.getValue(), LoanOrderStatusEnum.NEW_PUSH_SUCCESS.isFinalStatus(), Arrays.asList(new ShoppingLoanOrderStatusEnum[]{NEW})),
	MANUAL_REVIEWING(LoanOrderStatusEnum.MANUAL_REVIEWING.getCode(), LoanOrderStatusEnum.MANUAL_REVIEWING.getValue(), LoanOrderStatusEnum.MANUAL_REVIEWING.isFinalStatus(), Arrays.asList(new ShoppingLoanOrderStatusEnum[]{NEW,NEW_PUSH_SUCCESS})),
	REFUSED(LoanOrderStatusEnum.REFUSED.getCode(), LoanOrderStatusEnum.REFUSED.getValue(), LoanOrderStatusEnum.REFUSED.isFinalStatus(), Arrays.asList(new ShoppingLoanOrderStatusEnum[]{NEW,NEW_PUSH_SUCCESS,MANUAL_REVIEWING})),
	APPROVED(LoanOrderStatusEnum.APPROVED.getCode(), "待发货", LoanOrderStatusEnum.APPROVED.isFinalStatus(), Arrays.asList(new ShoppingLoanOrderStatusEnum[]{NEW,NEW_PUSH_SUCCESS,MANUAL_REVIEWING})),
	MANUAL_APPROVED(LoanOrderStatusEnum.MANUAL_APPROVED.getCode(), "待发货", LoanOrderStatusEnum.MANUAL_APPROVED.isFinalStatus(), Arrays.asList(new ShoppingLoanOrderStatusEnum[]{MANUAL_REVIEWING})),
	MANUAL_REFUSED(LoanOrderStatusEnum.MANUAL_REFUSED.getCode(), LoanOrderStatusEnum.MANUAL_REFUSED.getValue(), LoanOrderStatusEnum.MANUAL_REFUSED.isFinalStatus(), Arrays.asList(new ShoppingLoanOrderStatusEnum[]{MANUAL_REVIEWING})),

//	SENDED("23", "已发货", false, Arrays.asList(new ShoppingLoanOrderStatusEnum[]{MANUAL_APPROVED})),
	// 对应LoanOrderStatusEnum的LOANING("22", "推送订单至支付中心放款,放款中", false, Arrays.asList(new LoanOrderStatusEnum[]{APPROVED,MANUAL_APPROVED})),
	SENDING("24", "配送中", false, Arrays.asList(new ShoppingLoanOrderStatusEnum[]{MANUAL_APPROVED, APPROVED})),
	// 对应LOAN_SUCCESS("21", "支付中心通知放款结果,放款成功", false, Arrays.asList(new LoanOrderStatusEnum[]{LOANING}),TraceOrderEventEnum.PAYMENT_SUCCESS),
	THIRD_ORDERED("60", "待发货",false, Arrays.asList(new ShoppingLoanOrderStatusEnum[]{APPROVED})),
	RECEIVED("25", "交易完成", false, Arrays.asList(new ShoppingLoanOrderStatusEnum[]{SENDING,THIRD_ORDERED})),
	THIRD_ORDERED_FAIL("-60", "下单失败",true, Arrays.asList(new ShoppingLoanOrderStatusEnum[]{APPROVED,THIRD_ORDERED})),

	OVERDUE(LoanOrderStatusEnum.OVERDUE.getCode(), LoanOrderStatusEnum.OVERDUE.getValue(), LoanOrderStatusEnum.OVERDUE.isFinalStatus(), Arrays.asList(new ShoppingLoanOrderStatusEnum[]{RECEIVED})),
	SETTLED(LoanOrderStatusEnum.SETTLED.getCode(), LoanOrderStatusEnum.SETTLED.getValue(), LoanOrderStatusEnum.SETTLED.isFinalStatus(), Arrays.asList(new ShoppingLoanOrderStatusEnum[]{RECEIVED,OVERDUE})),
	CANCEL(LoanOrderStatusEnum.CANCEL.getCode(), LoanOrderStatusEnum.CANCEL.getValue(), LoanOrderStatusEnum.CANCEL.isFinalStatus(), Arrays.asList(new ShoppingLoanOrderStatusEnum[]{})),
	PUSH_FAIL(LoanOrderStatusEnum.PUSH_FAIL.getCode(), LoanOrderStatusEnum.PUSH_FAIL.getValue(), LoanOrderStatusEnum.PUSH_FAIL.isFinalStatus(),Arrays.asList(new ShoppingLoanOrderStatusEnum[]{NEW})),
	FAIL(LoanOrderStatusEnum.FAIL.getCode(), LoanOrderStatusEnum.FAIL.getValue(), LoanOrderStatusEnum.FAIL.isFinalStatus(), Arrays.asList(new ShoppingLoanOrderStatusEnum[]{}));


	private String code;
	private String value;
	private boolean isFinalStatus;
	private List<ShoppingLoanOrderStatusEnum> preStatusList;


	ShoppingLoanOrderStatusEnum(String code, String value, boolean isFinalStatus,List<ShoppingLoanOrderStatusEnum> preStatusList) {
		this.code = code;
		this.value = value;
		this.isFinalStatus = isFinalStatus;
		this.preStatusList = preStatusList;
	}


	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}


	public boolean isFinalStatus() {
		return isFinalStatus;
	}

	public void setFinalStatus(boolean finalStatus) {
		isFinalStatus = finalStatus;
	}

	public List<ShoppingLoanOrderStatusEnum> getPreStatusList() {
		return preStatusList;
	}

	public void setPreStatusList(List<ShoppingLoanOrderStatusEnum> preStatusList) {
		this.preStatusList = preStatusList;
	}

	public static Set<String> getByFinalStatus(boolean isFinalStatus){
		ShoppingLoanOrderStatusEnum[] loanOrderStatusEnums = ShoppingLoanOrderStatusEnum.values();
		Set<String> codeSet = new HashSet<>();

		for (int i = 0; i < loanOrderStatusEnums.length; i++) {
			ShoppingLoanOrderStatusEnum enumLoanOrderStatus = loanOrderStatusEnums[i];
			if (enumLoanOrderStatus.isFinalStatus() == isFinalStatus){
				codeSet.add(enumLoanOrderStatus.getCode());
			}
		}
		return codeSet;
	}
	
	public static Set<String> getCanApplyStatus(){
		Set<String> finalStatus = getByFinalStatus(false);
		finalStatus.remove(APPLY.getCode());
		return finalStatus;
	}

	public static ShoppingLoanOrderStatusEnum getByCode(String code){
		for(ShoppingLoanOrderStatusEnum e:ShoppingLoanOrderStatusEnum.values()){
			if(StringUtils.equals(e.getCode(),code)){
				return e;
			}
		}
		return null;
	}

	public static boolean canUpdate(String newStatus,String oldStatus){
		ShoppingLoanOrderStatusEnum orderStatusEnum = getByCode(newStatus);
		List<String> preStatus = new ArrayList<String>();
		for(ShoppingLoanOrderStatusEnum e:orderStatusEnum.getPreStatusList()){
			preStatus.add(e.getCode());
		}
		if(ArrayUtils.contains(preStatus.toArray(),oldStatus)){
			return true;
		}
		return false;
	}
}
