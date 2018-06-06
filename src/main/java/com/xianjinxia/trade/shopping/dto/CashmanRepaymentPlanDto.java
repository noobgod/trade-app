package com.xianjinxia.trade.shopping.dto;

import java.util.Date;

public class CashmanRepaymentPlanDto {
    private Long id;

    private Long productId;

    private Integer orderType;

    private Long userId;

    private Long loanOrderId;

    private Integer repaymentTotalAmount;

    private Integer repaymentOriginAmount;

    private Integer repaymentIncomeAmount;

    private Integer repaymentWaitingAmount;

    private Integer repaymentPrincipalAmount;

    private Integer repaymentInterestAmount;

    private Date repaymentPlanTime;

    private Date repaymentRealTime;

    private Integer period;

    private Integer status;

    private Boolean isCollection;

    private Boolean isOverdue;

    private Integer overdueFeeAmount;

    private Integer overdueDayCount;

    private String operationFlag;

    private Integer renewalCount;

    private Integer badLevel;

    private Date createdTime;

    private Date updatedTime;

    private String remark;

    private Boolean dataValid;

    private Integer version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

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

    public Integer getRepaymentTotalAmount() {
        return repaymentTotalAmount;
    }

    public void setRepaymentTotalAmount(Integer repaymentTotalAmount) {
        this.repaymentTotalAmount = repaymentTotalAmount;
    }

    public Integer getRepaymentOriginAmount() {
        return repaymentOriginAmount;
    }

    public void setRepaymentOriginAmount(Integer repaymentOriginAmount) {
        this.repaymentOriginAmount = repaymentOriginAmount;
    }

    public Integer getRepaymentIncomeAmount() {
        return repaymentIncomeAmount;
    }

    public void setRepaymentIncomeAmount(Integer repaymentIncomeAmount) {
        this.repaymentIncomeAmount = repaymentIncomeAmount;
    }

    public Integer getRepaymentWaitingAmount() {
        return repaymentWaitingAmount;
    }

    public void setRepaymentWaitingAmount(Integer repaymentWaitingAmount) {
        this.repaymentWaitingAmount = repaymentWaitingAmount;
    }

    public Integer getRepaymentPrincipalAmount() {
        return repaymentPrincipalAmount;
    }

    public void setRepaymentPrincipalAmount(Integer repaymentPrincipalAmount) {
        this.repaymentPrincipalAmount = repaymentPrincipalAmount;
    }

    public Integer getRepaymentInterestAmount() {
        return repaymentInterestAmount;
    }

    public void setRepaymentInterestAmount(Integer repaymentInterestAmount) {
        this.repaymentInterestAmount = repaymentInterestAmount;
    }

    public Date getRepaymentPlanTime() {
        return repaymentPlanTime;
    }

    public void setRepaymentPlanTime(Date repaymentPlanTime) {
        this.repaymentPlanTime = repaymentPlanTime;
    }

    public Date getRepaymentRealTime() {
        return repaymentRealTime;
    }

    public void setRepaymentRealTime(Date repaymentRealTime) {
        this.repaymentRealTime = repaymentRealTime;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Boolean getIsCollection() {
        return isCollection;
    }

    public void setIsCollection(Boolean isCollection) {
        this.isCollection = isCollection;
    }

    public Boolean getIsOverdue() {
        return isOverdue;
    }

    public void setIsOverdue(Boolean isOverdue) {
        this.isOverdue = isOverdue;
    }

    public Integer getOverdueFeeAmount() {
        return overdueFeeAmount;
    }

    public void setOverdueFeeAmount(Integer overdueFeeAmount) {
        this.overdueFeeAmount = overdueFeeAmount;
    }

    public Integer getOverdueDayCount() {
        return overdueDayCount;
    }

    public void setOverdueDayCount(Integer overdueDayCount) {
        this.overdueDayCount = overdueDayCount;
    }

    public String getOperationFlag() {
        return operationFlag;
    }

    public void setOperationFlag(String operationFlag) {
        this.operationFlag = operationFlag;
    }

    public Integer getRenewalCount() {
        return renewalCount;
    }

    public void setRenewalCount(Integer renewalCount) {
        this.renewalCount = renewalCount;
    }

    public Integer getBadLevel() {
        return badLevel;
    }

    public void setBadLevel(Integer badLevel) {
        this.badLevel = badLevel;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
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

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }


}
