package com.xianjinxia.trade.shared.enums.pet;

/**
 * Created wangwei
 */
public enum PetTxAbletStatusEnum {

    TRANSFERABLE("TRANSFERABLE","可转让"),
    FROZENED("FROZENED","冻结"),
    ONGOING("ONGOING","上链中");

    public String code;
    public String description;

    PetTxAbletStatusEnum(String code, String description) {
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
