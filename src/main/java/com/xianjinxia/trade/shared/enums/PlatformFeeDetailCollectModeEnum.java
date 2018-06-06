package com.xianjinxia.trade.shared.enums;

public enum PlatformFeeDetailCollectModeEnum {
    BEFORE_PAYMENT("before_payment","放款前收取"),
    AFTER_PAYMENT("after_payment","放款后收取");

    private String code;
    private String text;

    PlatformFeeDetailCollectModeEnum(String code, String text) {
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
