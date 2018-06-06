package com.xianjinxia.trade.pet.request;

import java.math.BigDecimal;

/**
 * Created by Myron on 2018/2/27.
 */
public class SalePetReq {
    private Long userId;
    private Long petId;
    private String userName;
    private BigDecimal price;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPetId() {
        return petId;
    }

    public void setPetId(Long petId) {
        this.petId = petId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
