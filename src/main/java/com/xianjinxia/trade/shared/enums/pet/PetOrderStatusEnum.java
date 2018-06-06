package com.xianjinxia.trade.shared.enums.pet;

public enum PetOrderStatusEnum {
    SELLING("selling", "出售中"),
    PAYING("paying", "支付中"),
    SELLING_CANCEL("selling_cancel", "取消出售"),
    SUCCESS("success", "出售成功");

    private String code;
    private String value;

    PetOrderStatusEnum(String code, String value) {
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