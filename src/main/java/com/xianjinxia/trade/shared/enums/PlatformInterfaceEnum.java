package com.xianjinxia.trade.shared.enums;

public enum PlatformInterfaceEnum {
    CONFIRM_LOAN("200080","订单确认接口"),
    GET_ORDER_STATUS("200110","获取订单状态");


    private String code;

    private String text;

    PlatformInterfaceEnum(String code, String text) {
        this.code = code;
        this.text = text;
    }

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
}
