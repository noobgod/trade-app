package com.xianjinxia.trade.shared.domain;

import com.xianjinxia.trade.shared.enums.PlatformFeeDetailCollectModeEnum;

import java.util.Date;

public class PlatformFeeDetail {
    private Long id;

    private String feeName;

    private String feeType;

    private String collectMode;

    private Integer feeAmount;

    private Long loanOrderId;

    private String createdUser;

    private Date createdTime;

    private Date updatedTime;

    public PlatformFeeDetail(String feeName, String feeType, String collectMode,Integer feeAmount, Long loanOrderId) {
        this.feeName = feeName;
        this.feeType = feeType;
        this.feeAmount = feeAmount;
        this.loanOrderId = loanOrderId;
        this.collectMode = collectMode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFeeName() {
        return feeName;
    }

    public void setFeeName(String feeName) {
        this.feeName = feeName == null ? null : feeName.trim();
    }

    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType == null ? null : feeType.trim();
    }

    public String getCollectMode() {
        return collectMode;
    }

    public void setCollectMode(String collectMode) {
        this.collectMode = collectMode == null ? null : collectMode.trim();
    }

    public Integer getFeeAmount() {
        return feeAmount;
    }

    public void setFeeAmount(Integer feeAmount) {
        this.feeAmount = feeAmount;
    }

    public Long getLoanOrderId() {
        return loanOrderId;
    }

    public void setLoanOrderId(Long loanOrderId) {
        this.loanOrderId = loanOrderId;
    }

    public String getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(String createdUser) {
        this.createdUser = createdUser == null ? null : createdUser.trim();
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
}