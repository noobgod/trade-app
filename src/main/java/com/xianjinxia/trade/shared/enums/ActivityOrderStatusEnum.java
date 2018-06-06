package com.xianjinxia.trade.shared.enums;


import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

public enum ActivityOrderStatusEnum {

    NEW("1", "待付款", false, Arrays.asList(new ActivityOrderStatusEnum[]{})),
    PAYING("2", "支付中", false, Arrays.asList(new ActivityOrderStatusEnum[]{NEW})),
    TO_DELIVER("3", "待发货", false, Arrays.asList(new ActivityOrderStatusEnum[]{PAYING})),
    FAIL("4", "付款失败", true, Arrays.asList(new ActivityOrderStatusEnum[]{})),
    SEND("5", "已发货", true, Arrays.asList(new ActivityOrderStatusEnum[]{TO_DELIVER}));

    private String code;
    private String value;
    private boolean isFinalStatus;
    private List<ActivityOrderStatusEnum> preStatusList;
    private TraceOrderEventEnum traceOrderEventEnum;



    ActivityOrderStatusEnum(String code, String value, boolean isFinalStatus, List<ActivityOrderStatusEnum> preStatusList) {
        this.code = code;
        this.value = value;
        this.isFinalStatus = isFinalStatus;
        this.preStatusList = preStatusList;
    }


    ActivityOrderStatusEnum(String code, String value, boolean isFinalStatus, List<ActivityOrderStatusEnum> preStatusList, TraceOrderEventEnum traceOrderEventEnum) {
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

    public List<ActivityOrderStatusEnum> getPreStatusList() {
        return preStatusList;
    }

    public void setPreStatusList(List<ActivityOrderStatusEnum> preStatusList) {
        this.preStatusList = preStatusList;
    }

    public TraceOrderEventEnum getTraceOrderEventEnum() {
        return traceOrderEventEnum;
    }

    public void setTraceOrderEventEnum(TraceOrderEventEnum traceOrderEventEnum) {
        this.traceOrderEventEnum = traceOrderEventEnum;
    }

    public static String getText(int code){
        ActivityOrderStatusEnum[] values = ActivityOrderStatusEnum.values();
        for (int i = 0; i < values.length; i++) {
            ActivityOrderStatusEnum enumLoanOrderStatus = values[i];
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
        ActivityOrderStatusEnum[] loanOrderStatusEnums = ActivityOrderStatusEnum.values();
        Set<String> codeSet = new HashSet<>();

        for (int i = 0; i < loanOrderStatusEnums.length; i++) {
            ActivityOrderStatusEnum enumLoanOrderStatus = loanOrderStatusEnums[i];
            if (enumLoanOrderStatus.isFinalStatus() == isFinalStatus){
                codeSet.add(enumLoanOrderStatus.getCode());
            }
        }
        return codeSet;
    }

    public static ActivityOrderStatusEnum getByCode(String code){
        for(ActivityOrderStatusEnum e: ActivityOrderStatusEnum.values()){
            if(StringUtils.equals(e.getCode(),code)){
                return e;
            }
        }
        return null;
    }

    public static boolean canUpdate(String newStatus,String oldStatus){
        ActivityOrderStatusEnum orderStatusEnum = getByCode(newStatus);
        List<String> preStatus = new ArrayList<String>();
        for(ActivityOrderStatusEnum e:orderStatusEnum.getPreStatusList()){
            preStatus.add(e.getCode());
        }
        if(ArrayUtils.contains(preStatus.toArray(),oldStatus)){
            return true;
        }
        return false;
    }
}