package com.xianjinxia.trade.shared.enums.pet;

public enum TransferTypeEnum {
    REQUEST_ALL("request_all", "请求");

    private String code;
    private String value;

    TransferTypeEnum(String code, String value) {
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