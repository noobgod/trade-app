package com.xianjinxia.trade.shared.enums.pet;

/**
 * Created wangwei
 */
public enum PetStatusEnum {


    HOLD("HOLD","持有中"),
    SETTLED("SETTLED","已被交割"),
    DEATH("DEATH","死亡");

    public String code;
    public String description;

    PetStatusEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
