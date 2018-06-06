package com.xianjinxia.trade.shared.enums.pet;

public enum PetSettleOrderTypeEnum {
    TYPE_PET_SALE("pet_sale", "宠物出售");

    private String code;
    private String value;

    PetSettleOrderTypeEnum(String code, String value) {
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