package com.xianjinxia.trade.shopping.response.bgd;

import java.util.Date;
import java.util.List;

public class ReceiveLoanOrderDetailResponse {

    //订单信息
    private Long id;
    private Integer orderAmount;
    private Integer feeAmount;
    private Integer interestAmount;
    private Integer term;
    private String termUnit;
    private String status;
    private String statusText;
    private Long autoReceiveTime;
    private Long userId;
    private String userPhone;
    private String userName;
    private Date createdAt;
    private Long createdAtLongVal;

    //收货信息
    private String receiveAddr;
    private String receiveUsername;
    private String receiveMobile;

    //默认收货天数
    private Integer defaultReceivedMaxDayCount;

    //商品信息（聚合查询）
    private String productName;
    private Integer productCount;
    private Integer productPrice;

    //订单类型
    private Integer kindId;

    private List<CardInfoDto> cards;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        this.termUnit = termUnit;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Long getCreatedAtLongVal() {
        return createdAtLongVal;
    }

    public void setCreatedAtLongVal(Long createdAtLongVal) {
        this.createdAtLongVal = createdAtLongVal;
    }

    public String getReceiveAddr() {
        return receiveAddr;
    }

    public void setReceiveAddr(String receiveAddr) {
        this.receiveAddr = receiveAddr;
    }

    public String getReceiveUsername() {
        return receiveUsername;
    }

    public void setReceiveUsername(String receiveUsername) {
        this.receiveUsername = receiveUsername;
    }

    public String getReceiveMobile() {
        return receiveMobile;
    }

    public void setReceiveMobile(String receiveMobile) {
        this.receiveMobile = receiveMobile;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getProductCount() {
        return productCount;
    }

    public void setProductCount(Integer productCount) {
        this.productCount = productCount;
    }

    public Integer getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Integer productPrice) {
        this.productPrice = productPrice;
    }

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    public Long getAutoReceiveTime() {
        return autoReceiveTime;
    }

    public void setAutoReceiveTime(Long autoReceiveTime) {
        this.autoReceiveTime = autoReceiveTime;
    }

    public Integer getDefaultReceivedMaxDayCount() {
        return defaultReceivedMaxDayCount;
    }

    public void setDefaultReceivedMaxDayCount(Integer defaultReceivedMaxDayCount) {
        this.defaultReceivedMaxDayCount = defaultReceivedMaxDayCount;
    }

    public List<CardInfoDto> getCards() {
        return cards;
    }

    public void setCards(List<CardInfoDto> cards) {
        this.cards = cards;
    }

    public Integer getKindId() {
        return kindId;
    }

    public void setKindId(Integer kindId) {
        this.kindId = kindId;
    }
}