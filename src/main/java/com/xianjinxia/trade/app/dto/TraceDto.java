package com.xianjinxia.trade.app.dto;

/**
 * @author zhangjiayuan
 * @desc
 * @create 2017-09-17 13:55
 **/
public class TraceDto {

    private Long userId;

    private Long loanOrderId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getLoanOrderId() {
        return loanOrderId;
    }

    public void setLoanOrderId(Long loanOrderId) {
        this.loanOrderId = loanOrderId;
    }
}
