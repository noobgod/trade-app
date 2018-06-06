package com.xianjinxia.trade.shared.enums.pet;

public enum PetSettleStatusEnum {
    BUYING("buying", "购买中"),
    PAYING("paying", "支付中"),
    SUCCESS("success", "购买成功"),
    FAIL("fail", "购买失败");

    private String code;
    private String value;

    PetSettleStatusEnum(String code, String value) {
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