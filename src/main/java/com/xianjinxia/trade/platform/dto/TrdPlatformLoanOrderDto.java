package com.xianjinxia.trade.platform.dto;

import java.math.BigDecimal;

public class TrdPlatformLoanOrderDto {

    // 用户信息
    private Long userId;
//    private String username;
//    private String userIdcardType;
//    private String userIdcardNo;
//    private String mobile;

    // 第三方的结果，由外部传参
    private Boolean isReloan;

    // 产品信息
    private String productCode;
//    private String serviceCompany;

    // 订单信息
    private BigDecimal orderAmount;
    private BigDecimal feeAmount;
    private BigDecimal paymentAmount;
    private BigDecimal repaymentAmount;
    private Integer term;
    private Integer termUnit;

    // 客户端设备
    private String terminal;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Boolean getIsReloan() {
        return isReloan;
    }

    public void setIsReloan(Boolean isReloan) {
        this.isReloan = isReloan;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
    }

    public BigDecimal getFeeAmount() {
        return feeAmount;
    }

    public void setFeeAmount(BigDecimal feeAmount) {
        this.feeAmount = feeAmount;
    }

    public BigDecimal getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(BigDecimal paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public BigDecimal getRepaymentAmount() {
        return repaymentAmount;
    }

    public void setRepaymentAmount(BigDecimal repaymentAmount) {
        this.repaymentAmount = repaymentAmount;
    }

    public Integer getTerm() {
        return term;
    }

    public void setTerm(Integer term) {
        this.term = term;
    }

    public Integer getTermUnit() {
        return termUnit;
    }

    public void setTermUnit(Integer termUnit) {
        this.termUnit = termUnit;
    }

    public String getTerminal() {
        return terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }
}


