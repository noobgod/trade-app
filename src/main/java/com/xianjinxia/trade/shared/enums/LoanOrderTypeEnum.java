package com.xianjinxia.trade.shared.enums;

public enum LoanOrderTypeEnum {

    LOAN("0", "贷款");

    private String code;
    private String value;

    LoanOrderTypeEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public String getCode() {
        return this.code;
    }

    public String getValue() {
        return this.value;
    }
}