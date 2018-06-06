package com.xianjinxia.trade.shopping.response.bgd;

import javax.validation.constraints.NotNull;

public class ShoppingLoanOrderDto {
    private Long id;
    private String userName;
    private String userPhone;
    private Integer orderAmount;
    private Integer term;
    private String termUnit;
    private String status;
    private Long createdTime;
    private Long updatedTime;
    @NotNull(message = "pageNum不能为空")
    private Integer pageNum;
    @NotNull(message = "pageSize不能为空")
    private Integer pageSize;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public Integer getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(Integer orderAmount) {
        this.orderAmount = orderAmount;
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

    public Long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Long createdTime) {
        this.createdTime = createdTime;
    }

    public Long getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Long updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Integer getPageNum() { return pageNum; }

    public void setPageNum(Integer pageNum) { this.pageNum = pageNum; }

    public Integer getPageSize() { return pageSize; }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public String toString() {
        return "ShoppingLoanOrderDto{" + "id=" + id + ", userName='" + userName + '\'' + ", userPhone='" + userPhone + '\'' + ", orderAmount=" + orderAmount + ", term=" + term + ", termUnit='" + termUnit + '\'' + ", status='" + status + '\'' + ", createdTime=" + createdTime + ", updatedTime=" + updatedTime + '}';
    }
}