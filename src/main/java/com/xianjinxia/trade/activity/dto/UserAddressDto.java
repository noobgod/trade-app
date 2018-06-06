package com.xianjinxia.trade.activity.dto;

/** 用户的收货地址DTO @author ganminghui*/
public class UserAddressDto {
    /* 收货人姓名 */private String receiveUsername;
    /* 收货人电话 */private String receivePhone;
    /* 收货城市 */  private String receiveCity;
    /* 收货详细地址 */private String receiveAddress;
    /* 业务流水号 */private String seqBizNo;

    public String getReceiveCity() { return receiveCity; }

    public void setReceiveCity(String receiveCity) { this.receiveCity = receiveCity; }

    public String getReceiveAddress() { return receiveAddress; }

    public void setReceiveAddress(String receiveAddress) { this.receiveAddress = receiveAddress; }

    public String getSeqBizNo() { return seqBizNo; }

    public void setSeqBizNo(String seqBizNo) { this.seqBizNo = seqBizNo; }

    public String getReceiveUsername() { return receiveUsername; }

    public void setReceiveUsername(String receiveUsername) { this.receiveUsername = receiveUsername; }

    public String getReceivePhone() { return receivePhone; }

    public void setReceivePhone(String receivePhone) { this.receivePhone = receivePhone; }

    public UserAddressDto(String receiveCity, String receiveAddress, String seqBizNo) {  this.receiveCity = receiveCity;    this.receiveAddress = receiveAddress;   this.seqBizNo = seqBizNo; }

    public UserAddressDto() { }

    @Override
    public String toString() {
        return "UserAddressDto{" +
                "receiveUsername='" + receiveUsername + '\'' +
                ", receivePhone='" + receivePhone + '\'' +
                ", receiveCity='" + receiveCity + '\'' +
                ", receiveAddress='" + receiveAddress + '\'' +
                ", seqBizNo='" + seqBizNo + '\'' +
                '}';
    }
}