package com.xianjinxia.trade.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

public class TrdShoppingLoanOrder {
    private Long id;

    private String productFreezeNo;

    private Integer orderAmount;

    private Integer feeAmount;

    private Integer interestAmount;

    private Integer paymentAmount;

    private Integer repaymentAmount;

    private Integer term;

    private String termUnit;

    private BigDecimal termRate;

    private Long productId;

    private Integer productCategory;

    private Integer productKind;

    private String isDepository;

    private Boolean userType;

    private String status;

    private String traceNo;

    private Long userId;

    private String userIdcardType;

    private String userIdcardNo;

    private Long userBankCardId;

    private String bankName;

    private String bankCardNoLastFour;

    private String userPhone;

    private String userName;

    private String source;

    private String terminal;

    private String ukToken;

    private Date createdAt;

    private String createdUser;

    private Date updatedAt;

    private String updatedUser;

	private String specificationJson;
    
    private String specificationDesc;
    
    private String remark;

    private Boolean dataValid;

    private Date thirdOrderTime;


    public TrdShoppingLoanOrder() {

    }

    public TrdShoppingLoanOrder(Long id, String productFreezeNo, Integer orderAmount, Integer feeAmount, Integer interestAmount, Integer paymentAmount, Integer repaymentAmount, Integer term, String termUnit, BigDecimal termRate, Long productId, Integer productCategory, String isDepository, Boolean userType, String status, String traceNo, Long userId, String userIdcardType, String userIdcardNo, Long userBankCardId, String bankName, String bankCardNoLastFour, String userPhone, String userName, String source, String terminal, String ukToken, Date createdAt, String createdUser, Date updatedAt, String updatedUser,String specificationJson, String specificationDesc,  String remark, Boolean dataValid, Integer productKind) {
        super();
        this.id = id;
        this.productFreezeNo = productFreezeNo;
        this.orderAmount = orderAmount;
        this.feeAmount = feeAmount;
        this.interestAmount = interestAmount;
        this.paymentAmount = paymentAmount;
        this.repaymentAmount = repaymentAmount;
        this.term = term;
        this.termUnit = termUnit;
        this.termRate = termRate;
        this.status = status;
        this.productId = productId;
        this.productCategory = productCategory;
        this.productKind = productKind;
        this.isDepository = isDepository;
        this.userType = userType;
        this.traceNo = traceNo;
        this.userId = userId;
        this.userIdcardType = userIdcardType;
        this.userIdcardNo = userIdcardNo;
        this.userBankCardId = userBankCardId;
        this.bankName = bankName;
        this.bankCardNoLastFour = bankCardNoLastFour;
        this.userPhone = userPhone;
        this.userName = userName;
        this.source = source;
        this.terminal = terminal;
        this.ukToken = ukToken;
        this.createdAt = createdAt;
        this.createdUser = createdUser;
        this.updatedAt = updatedAt;
        this.updatedUser = updatedUser;
        this.specificationJson = specificationJson;
        this.specificationDesc = specificationDesc;
        this.remark = remark;
        this.dataValid = dataValid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductFreezeNo() {
        return productFreezeNo;
    }

    public void setProductFreezeNo(String productFreezeNo) {
        this.productFreezeNo = productFreezeNo == null ? null : productFreezeNo.trim();
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

    public Integer getInterestAmount() {
        return interestAmount;
    }

    public void setInterestAmount(Integer interestAmount) {
        this.interestAmount = interestAmount;
    }

    public Integer getRepaymentAmount() {
        return repaymentAmount;
    }

    public void setRepaymentAmount(Integer repaymentAmount) {
        this.repaymentAmount = repaymentAmount;
    }

    public Integer getTerm() {
        return term;
    }

    public void setTerm(Integer term) {
        this.term = term;
    }

    public String getTermUnit() {
        return termUnit;
    }

    public void setTermUnit(String termUnit) {
        this.termUnit = termUnit == null ? null : termUnit.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getTraceNo() {
        return traceNo;
    }

    public void setTraceNo(String traceNo) {
        this.traceNo = traceNo == null ? null : traceNo.trim();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserIdcardType() {
        return userIdcardType;
    }

    public void setUserIdcardType(String userIdcardType) {
        this.userIdcardType = userIdcardType == null ? null : userIdcardType.trim();
    }

    public String getUserIdcardNo() {
        return userIdcardNo;
    }

    public void setUserIdcardNo(String userIdcardNo) {
        this.userIdcardNo = userIdcardNo == null ? null : userIdcardNo.trim();
    }

    public Long getUserBankCardId() {
        return userBankCardId;
    }

    public void setUserBankCardId(Long userBankCardId) {
        this.userBankCardId = userBankCardId;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName == null ? null : bankName.trim();
    }

    public String getBankCardNoLastFour() {
        return bankCardNoLastFour;
    }

    public void setBankCardNoLastFour(String bankCardNoLastFour) {
        this.bankCardNoLastFour = bankCardNoLastFour == null ? null : bankCardNoLastFour.trim();
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone == null ? null : userPhone.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source == null ? null : source.trim();
    }

    public String getTerminal() {
        return terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal == null ? null : terminal.trim();
    }

    public String getUkToken() {
        return ukToken;
    }

    public void setUkToken(String ukToken) {
        this.ukToken = ukToken == null ? null : ukToken.trim();
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(String createdUser) {
        this.createdUser = createdUser == null ? null : createdUser.trim();
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUpdatedUser() {
        return updatedUser;
    }

    public void setUpdatedUser(String updatedUser) {
        this.updatedUser = updatedUser == null ? null : updatedUser.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Boolean getDataValid() {
        return dataValid;
    }

    public void setDataValid(Boolean dataValid) {
        this.dataValid = dataValid;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(Integer productCategory) {
        this.productCategory = productCategory;
    }

    public Integer getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(Integer paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public String getIsDepository() {
        return isDepository;
    }

    public void setIsDepository(String isDepository) {
        this.isDepository = isDepository == null ? null : isDepository.trim();
    }

    public Boolean getUserType() {
        return userType;
    }

    public void setUserType(Boolean userType) {
        this.userType = userType;
    }

    public BigDecimal getTermRate() {
        return termRate;
    }

    public void setTermRate(BigDecimal termRate) {
        this.termRate = termRate;
    }
    
    public String getSpecificationJson() {
		return specificationJson;
	}

	public void setSpecificationJson(String specificationJson) {
		this.specificationJson = specificationJson;
	}

	public String getSpecificationDesc() {
		return specificationDesc;
	}

	public void setSpecificationDesc(String specificationDesc) {
		this.specificationDesc = specificationDesc;
	}

    public Integer getProductKind() {
        return productKind;
    }

    public void setProductKind(Integer productKind) {
        this.productKind = productKind;
    }

    public Date getThirdOrderTime() {
        return thirdOrderTime;
    }

    public void setThirdOrderTime(Date thirdOrderTime) {
        this.thirdOrderTime = thirdOrderTime;
    }

    @Override
    public String toString() {
        return "TrdShoppingLoanOrder{" + "id=" + id + ", productFreezeNo='" + productFreezeNo + '\'' + ", orderAmount=" + orderAmount + ", feeAmount=" + feeAmount + ", interestAmount=" + interestAmount + ", paymentAmount=" + paymentAmount + ", repaymentAmount=" + repaymentAmount + ", term=" + term + ", termUnit='" + termUnit + '\'' + ", termRate=" + termRate + ", productId=" + productId + ", productCategory=" + productCategory + ", productKind" + productKind + ", isDepository='" + isDepository + '\'' + ", userType=" + userType + ", status='" + status + '\'' + ", traceNo='" + traceNo + '\'' + ", userId=" + userId + ", userIdcardType='" + userIdcardType + '\'' + ", userIdcardNo='" + userIdcardNo + '\'' + ", userBankCardId=" + userBankCardId + ", bankName='" + bankName + '\'' + ", bankCardNoLastFour='" + bankCardNoLastFour + '\'' + ", userPhone='" + userPhone + '\'' + ", userName='" + userName + '\'' + ", source='" + source + '\'' + ", terminal='" + terminal + '\'' + ", ukToken='" + ukToken + '\'' + ", createdAt=" + createdAt + ", createdUser='" + createdUser + '\'' + ", updatedAt=" + updatedAt + ", updatedUser='" + updatedUser + '\'' + ", specificationJson='" + specificationJson + '\'' + ", specificationDesc='" + specificationDesc + '\'' + ", remark='" + remark + '\'' + ", dataValid=" + dataValid + '}';
    }
}