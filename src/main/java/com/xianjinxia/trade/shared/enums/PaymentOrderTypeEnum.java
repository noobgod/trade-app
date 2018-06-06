package com.xianjinxia.trade.shared.enums;

public enum PaymentOrderTypeEnum {
        TRADE(1, "放款");

        private int		code;
        private String	value;

        PaymentOrderTypeEnum(int code, String value) {
            this.code = code;
            this.value = value;
        }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}