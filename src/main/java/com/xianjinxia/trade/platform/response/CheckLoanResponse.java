package com.xianjinxia.trade.platform.response;

import java.util.Date;

public class CheckLoanResponse{
    public CheckLoanResponse(){}

    public CheckLoanResponse(Boolean allowLoan){
        this.allowLoan=allowLoan;
    }

    public CheckLoanResponse(String orderNo, Boolean allowLoan, String status, Date createdTime,
                             Long userBankCardId) {
        this.orderNo = orderNo;
        this.allowLoan = allowLoan;
        this.status = status;
        this.createdTime = createdTime;
        this.userBankCardId=userBankCardId;
    }

    private String orderNo;

    private Boolean allowLoan;

    private String status;

    private Date createdTime;


    private Long userBankCardId;

    public Long getUserBankCardId() {
        return userBankCardId;
    }

    public void setUserBankCardId(Long userBankCardId) {
        this.userBankCardId = userBankCardId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Boolean getAllowLoan() {
        return allowLoan;
    }

    public void setAllowLoan(Boolean allowLoan) {
        this.allowLoan = allowLoan;
    }
}
