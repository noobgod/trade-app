package com.xianjinxia.trade.shared.enums;

public enum LoanOrderSourceEnum {

    XJX("01", "贷款"),
    LOANARK("02", "贷款");

    private String code;
    private String value;

    LoanOrderSourceEnum(String code, String value) {
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