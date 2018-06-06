package com.xianjinxia.trade.platform.request;

import com.xianjinxia.trade.app.request.BaseRequest;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

public class LoanApplyRequest extends BaseRequest {

    // 用户信息
    @NotNull(message = "userId no value")
    private Long userId;

    private String username;

    private String userIdcardType;

    private String userIdcardNo;

    private String mobile;

    @NotNull(message = "isReloan no value")
    private Boolean isReloan;

    // 产品信息
    @NotNull(message = "productCode no value")
    private String productCode;

    @NotNull(message = "merchantCode no value")
    private String merchantCode;

    // 订单信息
    @NotNull(message = "orderAmount no value")
    private BigDecimal orderAmount;

    @NotNull(message = "feeAmount no value")
    private BigDecimal feeAmount;

    @NotNull(message = "interestAmount no value")
    private BigDecimal interestAmount;

    @NotNull(message = "paymentAmount no value")
    private BigDecimal paymentAmount;

    @NotNull(message = "repaymentAmount no value")
    private BigDecimal repaymentAmount;

    @NotNull(message = "applyTerm no value")
    private Integer applyTerm;

    @NotNull(message = "applyTermUnit no value")
    private Integer applyTermUnit;

    // 客户端设备
    @NotNull(message = "terminal no value")
    private String terminal;

    private List<LoanFeeDetailRequest> feeDetails;


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserIdcardType() {
        return userIdcardType;
    }

    public void setUserIdcardType(String userIdcardType) {
        this.userIdcardType = userIdcardType;
    }

    public String getUserIdcardNo() {
        return userIdcardNo;
    }

    public void setUserIdcardNo(String userIdcardNo) {
        this.userIdcardNo = userIdcardNo;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
    }

    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
    }

    public Boolean getReloan() {
        return isReloan;
    }

    public void setReloan(Boolean reloan) {
        isReloan = reloan;
    }

    public Integer getApplyTerm() {
        return applyTerm;
    }

    public void setApplyTerm(Integer applyTerm) {
        this.applyTerm = applyTerm;
    }

    public Integer getApplyTermUnit() {
        return applyTermUnit;
    }

    public void setApplyTermUnit(Integer applyTermUnit) {
        this.applyTermUnit = applyTermUnit;
    }

    public String getTerminal() {
        return terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
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

    public BigDecimal getInterestAmount() {
        return interestAmount;
    }

    public void setInterestAmount(BigDecimal interestAmount) {
        this.interestAmount = interestAmount;
    }

    public List<LoanFeeDetailRequest> getFeeDetails() {
        return feeDetails;
    }

    public void setFeeDetails(List<LoanFeeDetailRequest> feeDetails) {
        this.feeDetails = feeDetails;
    }

}
