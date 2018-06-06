package com.xianjinxia.trade.shared.domain;

import java.util.Date;

public class TrdShoppingLoanCardInfo {
    private Long id;

    private Long shoppingProductOrderId;

    private String thirdOrderId;

    private String productName;

    private Integer productUnitPrice;

    private String cardNo;

    private String cardPassword;

    private String cardDeadline;

    private String recycle;

    private Date recycleTime;

    private Date createdAt;

    private String createdUser;

    private Date updatedAt;

    private String updatedUser;

    private Boolean dataValid;


    public TrdShoppingLoanCardInfo() {

    }

    public TrdShoppingLoanCardInfo(Long id, Long shoppingProductOrderId, String thirdOrderId, String productName, String cardNo, String cardPassword, String recycle, Date recycleTime, Date createdAt, String createdUser, Date updatedAt, String updatedUser, Boolean dataValid, String cardDeadline, Integer productUnitPrice) {
        super();
        this.id = id;
        this.shoppingProductOrderId = shoppingProductOrderId;
        this.thirdOrderId = thirdOrderId;
        this.productName = productName;
        this.cardNo = cardNo;
        this.cardPassword = cardPassword;
        this.recycle = recycle;
        this.recycleTime = recycleTime;
        this.createdAt = createdAt;
        this.createdUser = createdUser;
        this.updatedAt = updatedAt;
        this.updatedUser = updatedUser;
        this.dataValid = dataValid;
        this.cardDeadline = cardDeadline;
        this.productUnitPrice = productUnitPrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getShoppingProductOrderId() {
        return shoppingProductOrderId;
    }

    public void setShoppingProductOrderId(Long shoppingProductOrderId) {
        this.shoppingProductOrderId = shoppingProductOrderId;
    }

    public String getThirdOrderId() {
        return thirdOrderId;
    }

    public void setThirdOrderId(String thirdOrderId) {
        this.thirdOrderId = thirdOrderId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getCardPassword() {
        return cardPassword;
    }

    public void setCardPassword(String cardPassword) {
        this.cardPassword = cardPassword;
    }

    public String getRecycle() {
        return recycle;
    }

    public void setRecycle(String recycle) {
        this.recycle = recycle;
    }

    public Date getRecycleTime() {
        return recycleTime;
    }

    public void setRecycleTime(Date recycleTime) {
        this.recycleTime = recycleTime;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(String createdUser) {
        this.createdUser = createdUser;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUpdatedUser() {
        return updatedUser;
    }

    public void setUpdatedUser(String updatedUser) {
        this.updatedUser = updatedUser;
    }

    public Boolean getDataValid() {
        return dataValid;
    }

    public void setDataValid(Boolean dataValid) {
        this.dataValid = dataValid;
    }

    public String getCardDeadline() {
        return cardDeadline;
    }

    public void setCardDeadline(String cardDeadline) {
        this.cardDeadline = cardDeadline;
    }

    public Integer getProductUnitPrice() {
        return productUnitPrice;
    }

    public void setProductUnitPrice(Integer productUnitPrice) {
        this.productUnitPrice = productUnitPrice;
    }

    @Override
    public String toString() {
        return "TrdShoppingLoanCardInfo{" + "id=" + id + ",shoppingProductOrderId=" + shoppingProductOrderId + ",thirdOrderId=" + thirdOrderId + ",productName=" + productName + ",cardNo=" + cardNo + ",cardPassword=" + cardPassword + ",recycle=" + recycle + ",recycleTime=" + recycleTime + ",createdAt=" + createdAt + ",createdUser=" + createdUser + ",updatedAt=" + updatedAt + ",updatedUser=" + updatedUser + ",dataValid=" + dataValid + ",cardDeadline=" + cardDeadline + ",productUnitPrice=" + productUnitPrice +'}';
    }
}