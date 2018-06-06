package com.xianjinxia.trade.pet.request;

/**
 * Created by Myron on 2018/2/27.
 */
public class UserSaleOrdersReq extends PetPageReq {
    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
