package com.xianjinxia.trade.shared.enums.pet;

public enum TransferStatusEnum {
    REQUEST_APPLYING("applying", "请求中"),
    REQUEST_COMPLETE("complete", "请求完成");

    private String code;
    private String value;

    TransferStatusEnum(String code, String value) {
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