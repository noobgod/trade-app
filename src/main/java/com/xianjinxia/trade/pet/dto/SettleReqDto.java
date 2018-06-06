package com.xianjinxia.trade.pet.dto;

/**
 * Created by Myron on 2018/2/28.
 */
public class SettleReqDto {
    private SettlePetReqDto pet;
    private SettleCoinReqDto point;

    public SettlePetReqDto getPet() {
        return pet;
    }

    public void setPet(SettlePetReqDto pet) {
        this.pet = pet;
    }

    public SettleCoinReqDto getPoint() {
        return point;
    }

    public void setPoint(SettleCoinReqDto point) {
        this.point = point;
    }
}
