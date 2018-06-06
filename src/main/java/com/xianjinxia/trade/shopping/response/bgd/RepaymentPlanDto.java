package com.xianjinxia.trade.shopping.response.bgd;

import java.math.BigDecimal;

public class RepaymentPlanDto {

    private Long id;

    private BigDecimal repaymentTotalAmount;

    private BigDecimal repaymentIncomeAmount;

    private BigDecimal repaymentWaitRepayAmount;

    private Long repaymentPlanTime;

    private Long repaymentRealTime;

    private Long updatedTime;

    private Integer period;

    private Integer status;

    private String statusText;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getRepaymentTotalAmount() {
        return repaymentTotalAmount;
    }

    public void setRepaymentTotalAmount(BigDecimal repaymentTotalAmount) {
        this.repaymentTotalAmount = repaymentTotalAmount;
    }

    public BigDecimal getRepaymentIncomeAmount() {
        return repaymentIncomeAmount;
    }

    public void setRepaymentIncomeAmount(BigDecimal repaymentIncomeAmount) {
        this.repaymentIncomeAmount = repaymentIncomeAmount;
    }


    public BigDecimal getRepaymentWaitRepayAmount() {
        return repaymentWaitRepayAmount;
    }

    public void setRepaymentWaitRepayAmount(BigDecimal repaymentWaitRepayAmount) {
        this.repaymentWaitRepayAmount = repaymentWaitRepayAmount;
    }

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    public Long getRepaymentPlanTime() {
        return repaymentPlanTime;
    }

    public void setRepaymentPlanTime(Long repaymentPlanTime) {
        this.repaymentPlanTime = repaymentPlanTime;
    }

    public Long getRepaymentRealTime() {
        return repaymentRealTime;
    }

    public void setRepaymentRealTime(Long repaymentRealTime) {
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

    public Long getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Long updatedTime) {
        this.updatedTime = updatedTime;
    }
}