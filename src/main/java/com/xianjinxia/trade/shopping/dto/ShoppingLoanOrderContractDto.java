package com.xianjinxia.trade.shopping.dto;

import java.util.Date;

/**
 * Created by liquan on 2017/11/25.
 */
public class ShoppingLoanOrderContractDto {
    /**
     * trdLoanOrderId
     */
    private Long id;

    private Long userId;

    private Integer orderAmount;

    private Integer feeAmount;

    private Integer periods;

    private Long productId;

    private Integer interestAmount;

    private Date createdTime;

    private String status;

    private String loanUsage;

    private String source;

    private String productName;

    private String merchantNo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(Integer orderAmount) {
        this.orderAmount = orderAmount;
    }

    public Integer getFeeAmount() {
        return feeAmount;
    }

    public void setFeeAmount(Integer feeAmount) {
        this.feeAmount = feeAmount;
    }

    public Integer getPeriods() {
        return periods;
    }

    public void setPeriods(Integer periods) {
        this.periods = periods;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getInterestAmount() {
        return interestAmount;
    }

    public void setInterestAmount(Integer interestAmount) {
        this.interestAmount = interestAmount;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLoanUsage() {
        return loanUsage;
    }

    public void setLoanUsage(String loanUsage) {
        this.loanUsage = loanUsage;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getMerchantNo() {
        return merchantNo;
    }

    public void setMerchantNo(String merchantNo) {
        this.merchantNo = merchantNo;
    }

    @Override
    public String toString() {
        return "ShoppingLoanOrderContractDto{" +
                "id=" + id +
                ", userId=" + userId +
                ", orderAmount=" + orderAmount +
                ", feeAmount=" + feeAmount +
                ", periods=" + periods +
                ", productId=" + productId +
                ", interestAmount=" + interestAmount +
                ", createdTime=" + createdTime +
                ", status='" + status + '\'' +
                ", loanUsage='" + loanUsage + '\'' +
                ", source='" + source + '\'' +
                ", productName='" + productName + '\'' +
                ", merchantNo='" + merchantNo + '\'' +
                '}';
    }

    public ShoppingLoanOrderContractDto(Long id, Long userId, Integer orderAmount, Integer feeAmount, Integer periods, Long productId, Integer interestAmount, Date createdTime, String status, String loanUsage, String source, String productName, String merchantNo) {
        this.id = id;
        this.userId = userId;
        this.orderAmount = orderAmount;
        this.feeAmount = feeAmount;
        this.periods = periods;
        this.productId = productId;
        this.interestAmount = interestAmount;
        this.createdTime = createdTime;
        this.status = status;
        this.loanUsage = loanUsage;
        this.source = source;
        this.productName = productName;
        this.merchantNo = merchantNo;
    }
}

