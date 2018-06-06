package com.xianjinxia.trade.shared.domain;

import com.xianjinxia.trade.platform.status.LoanOrderStatus;
import com.xianjinxia.trade.shared.enums.LoanOrderSourceEnum;
import com.xianjinxia.trade.shared.enums.LoanOrderTypeEnum;

import java.util.Date;

public class TrdPlatformLoanOrder {
    private Long id;

    private Long userId;

    private String orderNo;

    private String ukToken;

    private String orderType;

    private Integer orderAmount;

    private Date loanTime;

    private Integer feeAmount;

    private Integer interestAmount;

    private Integer paymentAmount;

    private Integer repaymentAmount;

    private Integer term;

    private String productName;

    private Integer productCategory;

    private Long userBankCardId;

    private String bankName;

    private String bankCardNoLastFour;

    private String remark;

    private String userPhone;

    private String userName;

    private String status;

    private String source;

    private String terminal;

    private Date createdTime;

    private String createdUser;

    private Date updatedTime;

    private Boolean dataValid;

    private String traceNo;

    private String serviceCompany;

    private Boolean isReloan;

    private Integer retryTimes;

    private Date nextRetryTime;

    private String termUnit;

    private String productCode;

    private String userIdcardType;

    private String userIdcardNo;

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

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public String getUkToken() {
        return ukToken;
    }

    public void setUkToken(String ukToken) {
        this.ukToken = ukToken == null ? null : ukToken.trim();
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType == null ? null : orderType.trim();
    }

    public Integer getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(Integer orderAmount) {
        this.orderAmount = orderAmount;
    }

    public Date getLoanTime() {
        return loanTime;
    }

    public void setLoanTime(Date loanTime) {
        this.loanTime = loanTime;
    }

    public Integer getFeeAmount() {
        return feeAmount;
    }

    public void setFeeAmount(Integer feeAmount) {
        this.feeAmount = feeAmount;
    }

    public Integer getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(Integer paymentAmount) {
        this.paymentAmount = paymentAmount;
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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(Integer productCategory) {
        this.productCategory = productCategory;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
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

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(String createdUser) {
        this.createdUser = createdUser == null ? null : createdUser.trim();
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Boolean getDataValid() {
        return dataValid;
    }

    public void setDataValid(Boolean dataValid) {
        this.dataValid = dataValid;
    }

    public String getTraceNo() {
        return traceNo;
    }

    public void setTraceNo(String traceNo) {
        this.traceNo = traceNo == null ? null : traceNo.trim();
    }

    public String getServiceCompany() {
        return serviceCompany;
    }

    public void setServiceCompany(String serviceCompany) {
        this.serviceCompany = serviceCompany == null ? null : serviceCompany.trim();
    }

    public Boolean getIsReloan() {
        return isReloan;
    }

    public void setIsReloan(Boolean isReloan) {
        this.isReloan = isReloan;
    }

    public Integer getRetryTimes() {
        return retryTimes;
    }

    public void setRetryTimes(Integer retryTimes) {
        this.retryTimes = retryTimes;
    }

    public String getTermUnit() {
        return termUnit;
    }

    public void setTermUnit(String termUnit) {
        this.termUnit = termUnit == null ? null : termUnit.trim();
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode == null ? null : productCode.trim();
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

    public Date getNextRetryTime() {
        return nextRetryTime;
    }

    public void setNextRetryTime(Date nextRetryTime) {
        this.nextRetryTime = nextRetryTime;
    }

    public Integer getInterestAmount() {
        return interestAmount;
    }

    public void setInterestAmount(Integer interestAmount) {
        this.interestAmount = interestAmount;
    }

    public TrdPlatformLoanOrder(Long id, Long userId, String orderNo, String ukToken, String orderType, Integer orderAmount, Date loanTime, Integer feeAmount, Integer interestAmount, Integer paymentAmount, Integer repaymentAmount, Integer term, String productName, Integer productCategory, Long userBankCardId, String bankName, String bankCardNoLastFour, String remark, String userPhone, String userName, String status, String source, String terminal, Date createdTime, String createdUser, Date updatedTime, Boolean dataValid, String traceNo, String serviceCompany, Boolean isReloan, Integer retryTimes, Date nextRetryTime, String termUnit, String productCode, String userIdcardType, String userIdcardNo) {
        this.id = id;
        this.userId = userId;
        this.orderNo = orderNo;
        this.ukToken = ukToken;
        this.orderType = orderType;
        this.orderAmount = orderAmount;
        this.loanTime = loanTime;
        this.feeAmount = feeAmount;
        this.interestAmount = interestAmount;
        this.paymentAmount = paymentAmount;
        this.repaymentAmount = repaymentAmount;
        this.term = term;
        this.productName = productName;
        this.productCategory = productCategory;
        this.userBankCardId = userBankCardId;
        this.bankName = bankName;
        this.bankCardNoLastFour = bankCardNoLastFour;
        this.remark = remark;
        this.userPhone = userPhone;
        this.userName = userName;
        this.status = status;
        this.source = source;
        this.terminal = terminal;
        this.createdTime = createdTime;
        this.createdUser = createdUser;
        this.updatedTime = updatedTime;
        this.dataValid = dataValid;
        this.traceNo = traceNo;
        this.serviceCompany = serviceCompany;
        this.isReloan = isReloan;
        this.retryTimes = retryTimes;
        this.nextRetryTime = nextRetryTime;
        this.termUnit = termUnit;
        this.productCode = productCode;
        this.userIdcardType = userIdcardType;
        this.userIdcardNo = userIdcardNo;
    }

    public TrdPlatformLoanOrder(Long userId, String orderNo, Integer orderAmount, Integer feeAmount, Integer interestAmount, Integer paymentAmount, Integer repaymentAmount, Integer term, String productName, String userPhone, String userName, String terminal, Date createdTime, String traceNo, String serviceCompany, Boolean isReloan, String termUnit, String productCode, String userIdcardNo) {
        this.userId = userId;
        this.orderNo = orderNo;
        this.ukToken = orderNo;
        this.orderType = LoanOrderTypeEnum.LOAN.getCode();
        this.orderAmount = orderAmount;
        this.feeAmount = feeAmount;
        this.interestAmount = interestAmount;
        this.paymentAmount = paymentAmount;
        this.repaymentAmount = repaymentAmount;
        this.term = term;
        this.productName = productName;
        this.userPhone = userPhone;
        this.userName = userName;
        this.status = LoanOrderStatus.NEW.getValue();
        this.source = LoanOrderSourceEnum.LOANARK.getCode();
        this.terminal = terminal;
        this.createdTime = createdTime;
        this.updatedTime = new Date();
        this.dataValid = true;
        this.traceNo = traceNo;
        this.serviceCompany = serviceCompany;
        this.isReloan = isReloan;
        this.retryTimes = 0;
        this.termUnit = termUnit;
        this.productCode = productCode;
        this.userIdcardType = "IDCARD";
        this.userIdcardNo = userIdcardNo;
    }

    public TrdPlatformLoanOrder() {
    }
}