package com.xianjinxia.trade.pet.dto;

import java.math.BigDecimal;

/**
 * Created by Myron on 2018/2/28.
 */
public class FrozenCoinDto {
    private Long userId;
    private BigDecimal share;
    private BigDecimal gasShare;

    public FrozenCoinDto(Long userId, BigDecimal share, BigDecimal gasShare) {
        this.userId = userId;
        this.share = share;
        this.gasShare = gasShare;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getShare() {
        return share;
    }

    public void setShare(BigDecimal share) {
        this.share = share;
    }

    public BigDecimal getGasShare() {
        return gasShare;
    }

    public void setGasShare(BigDecimal gasShare) {
        this.gasShare = gasShare;
    }
}
