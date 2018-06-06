package com.xianjinxia.trade.pet.dto;

/**
 * Created by Myron on 2018/2/28.
 */
public class SettlePetReqDto {
    private String frozenNo;
    private Long originUserId;
    private Long targetUserId;

    public String getFrozenNo() {
        return frozenNo;
    }

    public void setFrozenNo(String frozenNo) {
        this.frozenNo = frozenNo;
    }

    public Long getOriginUserId() {
        return originUserId;
    }

    public void setOriginUserId(Long originUserId) {
        this.originUserId = originUserId;
    }

    public Long getTargetUserId() {
        return targetUserId;
    }

    public void setTargetUserId(Long targetUserId) {
        this.targetUserId = targetUserId;
    }
}
