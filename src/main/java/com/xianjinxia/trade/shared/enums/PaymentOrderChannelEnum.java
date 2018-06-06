package com.xianjinxia.trade.shared.enums;

public enum PaymentOrderChannelEnum {
        PAYMENT_CENTER("01");


        PaymentOrderChannelEnum(String code) {
            this.code = code;
        }

        private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}