package com.xianjinxia.trade.shared.enums.pet;

public enum PetSettleSourceTypeEnum {
    TRD_SALE_ORDER_REQUEST("trd_sale_order_request", "宠物出售单");

    private String code;
    private String value;

    PetSettleSourceTypeEnum(String code, String value) {
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