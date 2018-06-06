package com.xianjinxia.trade.shared.enums;


import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

public enum LoanOrderStatusEnum {

    NEW("10", "新申请", false, Arrays.asList(new LoanOrderStatusEnum[]{})),
    NEW_PUSH_SUCCESS("0", "推送订单给集团风控,推送成功", false, Arrays.asList(new LoanOrderStatusEnum[]{NEW}),TraceOrderEventEnum.LOAN_CONFIRM),
    MANUAL_REVIEWING("30", "推送订单给集团风控,待人工审核", false, Arrays.asList(new LoanOrderStatusEnum[]{NEW,NEW_PUSH_SUCCESS}),TraceOrderEventEnum.MANUAL_REVIEWING),
    REFUSED("-3", "推送订单给集团风控,审核拒绝", true, Arrays.asList(new LoanOrderStatusEnum[]{NEW,NEW_PUSH_SUCCESS,MANUAL_REVIEWING}),TraceOrderEventEnum.RISK_DATA_FAIL),
    APPROVED("11", "推送订单给集团风控,审核通过", false, Arrays.asList(new LoanOrderStatusEnum[]{NEW,NEW_PUSH_SUCCESS,MANUAL_REVIEWING}),TraceOrderEventEnum.PAYMENTING),
    MANUAL_APPROVED("31", "人工审核订单结果, 人工审核通过", false, Arrays.asList(new LoanOrderStatusEnum[]{MANUAL_REVIEWING}),TraceOrderEventEnum.PAYMENTING),
    MANUAL_REFUSED("-30", "人工审核订单结果, 人工审核拒绝", true, Arrays.asList(new LoanOrderStatusEnum[]{MANUAL_REVIEWING}),TraceOrderEventEnum.MANUALREVIEW_FAILURE),
    LOANING("22", "推送订单至支付中心放款,放款中", false, Arrays.asList(new LoanOrderStatusEnum[]{APPROVED,MANUAL_APPROVED})),
    LOAN_SUCCESS("21", "支付中心通知放款结果,放款成功", false, Arrays.asList(new LoanOrderStatusEnum[]{LOANING}),TraceOrderEventEnum.PAYMENT_SUCCESS),
    LOAN_FAIL("-10", "支付中心通知放款结果,放款失败", true, Arrays.asList(new LoanOrderStatusEnum[]{LOANING,APPROVED}),TraceOrderEventEnum.PAYMENT_FAIL),
    OVERDUE("40", "还款结果,逾期", false, Arrays.asList(new LoanOrderStatusEnum[]{LOAN_SUCCESS})),
    SETTLED("50", "还款结果,结清", true, Arrays.asList(new LoanOrderStatusEnum[]{LOAN_SUCCESS, OVERDUE})),
    CANCEL("-22", "取消", true, Arrays.asList(new LoanOrderStatusEnum[]{})),
    PUSH_FAIL("-12", "风控,推送失败", true,Arrays.asList(new LoanOrderStatusEnum[]{NEW})),
    FAIL("-11", "异常,失败", true, Arrays.asList(new LoanOrderStatusEnum[]{}));


    /** 下面两个是老系统的属性，老系统没有还款表，新系统将还款抽取出来了，所以目前用不到以下两个字段
     STATUS_KKZ("12", "扣款中"),
     STATUS_KKSB("-7", "扣款失败");*/

    private String code;
    private String value;
    private boolean isFinalStatus;
    private List<LoanOrderStatusEnum> preStatusList;
    private TraceOrderEventEnum traceOrderEventEnum;



    LoanOrderStatusEnum(String code, String value, boolean isFinalStatus,List<LoanOrderStatusEnum> preStatusList) {
        this.code = code;
        this.value = value;
        this.isFinalStatus = isFinalStatus;
        this.preStatusList = preStatusList;
    }


    LoanOrderStatusEnum(String code, String value, boolean isFinalStatus,List<LoanOrderStatusEnum> preStatusList,TraceOrderEventEnum traceOrderEventEnum) {
        this.code = code;
        this.value = value;
        this.isFinalStatus = isFinalStatus;
        this.preStatusList = preStatusList;
        this.traceOrderEventEnum = traceOrderEventEnum;
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

    public List<LoanOrderStatusEnum> getPreStatusList() {
        return preStatusList;
    }

    public void setPreStatusList(List<LoanOrderStatusEnum> preStatusList) {
        this.preStatusList = preStatusList;
    }

    public TraceOrderEventEnum getTraceOrderEventEnum() {
        return traceOrderEventEnum;
    }

    public void setTraceOrderEventEnum(TraceOrderEventEnum traceOrderEventEnum) {
        this.traceOrderEventEnum = traceOrderEventEnum;
    }

    public static String getText(int code){
        LoanOrderStatusEnum[] values = LoanOrderStatusEnum.values();
        for (int i = 0; i < values.length; i++) {
            LoanOrderStatusEnum enumLoanOrderStatus = values[i];
            if (enumLoanOrderStatus.getCode().equals(code)){
                return enumLoanOrderStatus.getValue();
            }
        }

        return null;
    }

    public boolean isFinalStatus() {
        return isFinalStatus;
    }

    public void setFinalStatus(boolean finalStatus) {
        isFinalStatus = finalStatus;
    }

    public static Set<String> getByFinalStatus(boolean isFinalStatus){
        LoanOrderStatusEnum[] loanOrderStatusEnums = LoanOrderStatusEnum.values();
        Set<String> codeSet = new HashSet<>();

        for (int i = 0; i < loanOrderStatusEnums.length; i++) {
            LoanOrderStatusEnum enumLoanOrderStatus = loanOrderStatusEnums[i];
            if (enumLoanOrderStatus.isFinalStatus() == isFinalStatus){
                codeSet.add(enumLoanOrderStatus.getCode());
            }
        }
        return codeSet;
    }

    public static LoanOrderStatusEnum getByCode(String code){
        for(LoanOrderStatusEnum e:LoanOrderStatusEnum.values()){
            if(StringUtils.equals(e.getCode(),code)){
                return e;
            }
        }
        return null;
    }

    public static boolean canUpdate(String newStatus,String oldStatus){
        LoanOrderStatusEnum orderStatusEnum = getByCode(newStatus);
        List<String> preStatus = new ArrayList<String>();
        for(LoanOrderStatusEnum e:orderStatusEnum.getPreStatusList()){
            preStatus.add(e.getCode());
        }
        if(ArrayUtils.contains(preStatus.toArray(),oldStatus)){
            return true;
        }
        return false;
    }

    //
//    APPROVALING("0", "审核中"),
//    APPROVAL_FAILURE("-3", "审核失败"),
//    MANUALREVIEWING("30", "人工审核中"),
//    MANUALREVIEW_FAILURE("-30", "人工审核失败"),
//    MANUALREVIEW_SUCCESS("31","人工审核通过"),
//    LOANING("22", "放款中"),
//    LOAN_FAILURE("-10", "放款失败"),
//    LOAN_SUCCESS("21", "放款成功"),
//    LOAN_YHQ("11", "已还清");
//
//
//    /** 下面两个是老系统的属性，老系统没有还款表，新系统将还款抽取出来了，所以目前用不到以下两个字段
//    STATUS_KKZ("12", "扣款中"),
//    STATUS_KKSB("-7", "扣款失败");*/
//
//    private String code;
//    private String value;
//
//    LoanOrderStatusEnum(String code, String value) {
//        this.code = code;
//        this.value = value;
//    }
//
//    public String getCode() {
//        return this.code;
//    }
//
//    public String getValue() {
//        return this.value;
//    }
//
}