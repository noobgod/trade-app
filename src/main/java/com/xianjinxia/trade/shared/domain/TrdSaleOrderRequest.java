package com.xianjinxia.trade.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

public class TrdSaleOrderRequest {
    private Long id;

    private String saleNo;

    private Long userId;

    private String userName;

    private Long petId;

    private String petCode;

    private String petFrozenNo;

    private String petName;

    private String petImg;

    private int petGenerateLevel;

    private String petPropertyDetail;

    private String petVarity;

    private BigDecimal price;

    private String status;

    private Date applyTime;

    private String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSaleNo() {
        return saleNo;
    }

    public void setSaleNo(String saleNo) {
        this.saleNo = saleNo == null ? null : saleNo.trim();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getPetFrozenNo() {
        return petFrozenNo;
    }

    public void setPetFrozenNo(String petFrozenNo) {
        this.petFrozenNo = petFrozenNo == null ? null : petFrozenNo.trim();
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName == null ? null : petName.trim();
    }

    public String getPetImg() {
        return petImg;
    }

    public void setPetImg(String petImg) {
        this.petImg = petImg == null ? null : petImg.trim();
    }

    public String getPetPropertyDetail() {
        return petPropertyDetail;
    }

    public void setPetPropertyDetail(String petPropertyDetail) {
        this.petPropertyDetail = petPropertyDetail == null ? null : petPropertyDetail.trim();
    }

    public String getPetVarity() {
        return petVarity;
    }

    public void setPetVarity(String petVarity) {
        this.petVarity = petVarity == null ? null : petVarity.trim();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Long getPetId() {
        return petId;
    }

    public void setPetId(Long petId) {
        this.petId = petId;
    }

    public String getPetCode() {
        return petCode;
    }

    public void setPetCode(String petCode) {
        this.petCode = petCode;
    }

    public int getPetGenerateLevel() {
        return petGenerateLevel;
    }

    public void setPetGenerateLevel(int petGenerateLevel) {
        this.petGenerateLevel = petGenerateLevel;
    }
}