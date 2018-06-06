package com.xianjinxia.trade.pet.request;

/**
 * Created by Myron on 2018/2/27.
 */
public class UnSalePetReq {
    private Long userId;
    private Long petId;

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
}
