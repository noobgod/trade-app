package com.xianjinxia.trade.platform.request;

import com.xianjinxia.trade.app.request.BaseRequest;
import com.xianjinxia.trade.shared.idempotent.IdempotentKey;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class PlatformConfirmLoanRequest extends BaseRequest{

    @NotNull(message = "orderNo no value")
    @IdempotentKey(order=1)
    private String orderNo;

    @NotNull(message = "userId no value")
    private Long userId;

    private BigDecimal orderAmount;

    private Integer term;

    private Long bankCardId;

    private Boolean isReloan;

    public PlatformConfirmLoanRequest() {}

    public PlatformConfirmLoanRequest(String orderNo, Long userId) {
        this.orderNo = orderNo;
        this.userId = userId;
    }

    public PlatformConfirmLoanRequest(String orderNo, Long userId, BigDecimal orderAmount, Integer term, Long bankCardId, Boolean isReloan) {
        this.orderNo = orderNo;
        this.userId = userId;
        this.orderAmount = orderAmount;
        this.term = term;
        this.bankCardId = bankCardId;
        this.isReloan = isReloan;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
    }

    public Integer getTerm() {
        return term;
    }

    public void setTerm(Integer term) {
        this.term = term;
    }

    public Long getBankCardId() {
        return bankCardId;
    }

    public void setBankCardId(Long bankCardId) {
        this.bankCardId = bankCardId;
    }

    public Boolean getReloan() {
        return isReloan;
    }

    public void setReloan(Boolean reloan) {
        isReloan = reloan;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
