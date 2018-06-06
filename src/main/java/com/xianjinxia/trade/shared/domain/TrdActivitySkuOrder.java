package com.xianjinxia.trade.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

public class TrdActivitySkuOrder {
    private Long id;

    private String bizSeqNo;

    private Long userId;

    private String userPhone;

    private String userName;

    private Integer productId;

    private String productName;

    private String specification;

    private BigDecimal productPrice;

    private String receiveUsername;

    private String receivePhone;

    private String receiveCity;

    private String receiveAddress;

    private String logisticsCompany;

    private String logisticsNo;

    private String venderName;

    private Date sendTime;
    private String status;
    private Boolean dataValid;
    private Date createdAt;
    private Date updatedAt;


    public TrdActivitySkuOrder() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBizSeqNo() {
        return bizSeqNo;
    }

    public void setBizSeqNo(String bizSeqNo) {
        this.bizSeqNo = bizSeqNo;
    }

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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    public String getReceiveUsername() {
        return receiveUsername;
    }

    public void setReceiveUsername(String receiveUsername) {
        this.receiveUsername = receiveUsername;
    }

    public String getReceivePhone() {
        return receivePhone;
    }

    public void setReceivePhone(String receivePhone) {
        this.receivePhone = receivePhone;
    }

    public String getReceiveCity() {
        return receiveCity;
    }

    public void setReceiveCity(String receiveCity) {
        this.receiveCity = receiveCity;
    }

    public String getReceiveAddress() {
        return receiveAddress;
    }

    public void setReceiveAddress(String receiveAddress) {
        this.receiveAddress = receiveAddress;
    }

    public String getLogisticsCompany() {
        return logisticsCompany;
    }

    public void setLogisticsCompany(String logisticsCompany) {
        this.logisticsCompany = logisticsCompany;
    }

    public String getVenderName() {
        return venderName;
    }

    public void setVenderName(String venderName) {
        this.venderName = venderName;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getDataValid() {
        return dataValid;
    }

    public void setDataValid(Boolean dataValid) {
        this.dataValid = dataValid;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getLogisticsNo() {
        return logisticsNo;
    }

    public void setLogisticsNo(String logisticsNo) {
        this.logisticsNo = logisticsNo;
    }

    public TrdActivitySkuOrder(String bizSeqNo, Long userId, String userPhone, String userName, Integer productId, String productName, String specification, BigDecimal productPrice, String receiveUsername, String receivePhone, String receiveCity, String receiveAddress, String status) {
        this.bizSeqNo = bizSeqNo;
        this.userId = userId;
        this.userPhone = userPhone;
        this.userName = userName;
        this.productId = productId;
        this.productName = productName;
        this.specification = specification;
        this.productPrice = productPrice;
        this.receiveUsername = receiveUsername;
        this.receivePhone = receivePhone;
        this.receiveCity = receiveCity;
        this.receiveAddress = receiveAddress;
        this.status = status;
    }

    @Override
    public String toString() {
        return "TrdActivitySkuOrder{" +
                "id=" + id +
                ", bizSeqNo='" + bizSeqNo + '\'' +
                ", userId=" + userId +
                ", userPhone='" + userPhone + '\'' +
                ", userName='" + userName + '\'' +
                ", productId=" + productId +
                ", productName='" + productName + '\'' +
                ", specification='" + specification + '\'' +
                ", productPrice=" + productPrice +
                ", receiveUsername='" + receiveUsername + '\'' +
                ", receivePhone='" + receivePhone + '\'' +
                ", receiveCity='" + receiveCity + '\'' +
                ", receiveAddress='" + receiveAddress + '\'' +
                ", logisticsCompany='" + logisticsCompany + '\'' +
                ", venderName='" + venderName + '\'' +
                ", sendTime=" + sendTime +
                ", status='" + status + '\'' +
                ", dataValid=" + dataValid +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}