package com.xianjinxia.trade.pet.request;

import java.math.BigDecimal;

/**
 * Created by Myron on 2018/2/27.
 */
public class BuyPetReq {
    private Long userId;
    private String saleNo;
    private BigDecimal price;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getSaleNo() {
        return saleNo;
    }

    public void setSaleNo(String saleNo) {
        this.saleNo = saleNo;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
