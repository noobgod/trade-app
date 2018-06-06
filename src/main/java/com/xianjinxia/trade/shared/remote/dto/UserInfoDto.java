package com.xianjinxia.trade.shared.remote.dto;

public class UserInfoDto {

    private Long userId;
    private String username;
    private String userIdcardType;
    private String userIdcardNo;
    private String mobile;

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
}
