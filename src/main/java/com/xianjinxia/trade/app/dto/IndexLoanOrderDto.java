package com.xianjinxia.trade.app.dto;

import java.math.BigDecimal;
import java.util.Date;

public class IndexLoanOrderDto {

    private Long id;
    private String status;
    private Long productId;
    private Integer productCategory;
    private Integer periods;
    private BigDecimal orderAmount;
    private Date reviewFailTime;

    public IndexLoanOrderDto(){}
    public IndexLoanOrderDto(Long id, String status, Long productId, Integer productCategory,
                             Integer periods,  BigDecimal orderAmount,
                             Date reviewFailTime) {
        this.id = id;
        this.status = status;
        this.productId = productId;
        this.productCategory = productCategory;
        this.periods = periods;
        this.orderAmount = orderAmount;
        this.reviewFailTime = reviewFailTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(Integer productCategory) {
        this.productCategory = productCategory;
    }

    public Integer getPeriods() {
        return periods;
    }

    public void setPeriods(Integer periods) {
        this.periods = periods;
    }

    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
    }

    public Date getReviewFailTime() {
        return reviewFailTime;
    }

    public void setReviewFailTime(Date reviewFailTime) {
        this.reviewFailTime = reviewFailTime;
    }
}
