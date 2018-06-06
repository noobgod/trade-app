package com.xianjinxia.trade.platform.dto;

public class TrdPlatformLoanApplyDto {

    private String orderNo;
    private Long orderTime;
    private Integer applicationTerm;
    private Integer applicationUnit;
    private Integer applicationAmount;
    private String productCode;
    private String merchantCode;
    private Long userId;
    private Boolean isReloan;


    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Long getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Long orderTime) {
        this.orderTime = orderTime;
    }

    public Integer getApplicationTerm() {
        return applicationTerm;
    }

    public void setApplicationTerm(Integer applicationTerm) {
        this.applicationTerm = applicationTerm;
    }

    public Integer getApplicationUnit() {
        return applicationUnit;
    }

    public void setApplicationUnit(Integer applicationUnit) {
        this.applicationUnit = applicationUnit;
    }

    public Integer getApplicationAmount() {
        return applicationAmount;
    }

    public void setApplicationAmount(Integer applicationAmount) {
        this.applicationAmount = applicationAmount;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
    }

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

    public TrdPlatformLoanApplyDto() {
    }

    public TrdPlatformLoanApplyDto(String orderNo, Long orderTime, Integer applicationTerm, Integer applicationUnit, Integer applicationAmount, String productCode, String merchantCode, Long userId, Boolean isReloan) {
        this.orderNo = orderNo;
        this.orderTime = orderTime;
        this.applicationTerm = applicationTerm;
        this.applicationUnit = applicationUnit;
        this.applicationAmount = applicationAmount;
        this.productCode = productCode;
        this.merchantCode = merchantCode;
        this.userId = userId;
        this.isReloan = isReloan;
    }
}
