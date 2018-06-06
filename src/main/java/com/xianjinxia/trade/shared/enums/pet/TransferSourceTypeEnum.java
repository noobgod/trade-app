package com.xianjinxia.trade.shared.enums.pet;

public enum TransferSourceTypeEnum {
    TRD_SETTLE_RECORD("trd_settle_record", "宠物交割单");

    private String code;
    private String value;

    TransferSourceTypeEnum(String code, String value) {
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