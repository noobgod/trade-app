package com.xianjinxia.trade.shared.enums;

public enum ProductCategoryEnum{

    SMALLAMOUNT(1),
    BIGAMOUNT(2),
    PRODUCT_CATEGORY_SHOPPING(3);//3表示商城订单

    ProductCategoryEnum(Integer code){
        this.code=code;
    }

    private Integer code;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}