package com.xianjinxia.trade.shared.remote.dto;

public class CashmanUserInfoDto {

    private Long userId;
    private String userPhone;
    private String realname;
    private String idNumber;
    private Integer availableAmt;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public Integer getAvailableAmt() {
        return availableAmt;
    }

    public void setAvailableAmt(Integer availableAmt) {
        this.availableAmt = availableAmt;
    }
}
