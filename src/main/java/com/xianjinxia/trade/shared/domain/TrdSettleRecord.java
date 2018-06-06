package com.xianjinxia.trade.shared.domain;

import com.alibaba.fastjson.JSON;

import java.math.BigDecimal;
import java.util.Date;

public class TrdSettleRecord {
    private Long id;

    private String sourceId;

    private String sourceType;

    private String settleNo;

    private String settleOderType;

    private Long fromUserId;

    private String fromUserName;

    private Long toUserId;

    private String toUserName;

    private BigDecimal price;

    private BigDecimal expecteServiceCharge;

    private BigDecimal actualServiceCharge;

    private Date paymentTime;

    private String status;

    private Long petId;

    private String petCode;

    private String petName;

    private String petImg;

    private int petGenerateLevel;

    private String petPropertyDetail;

    private String petVarity;

    private String fromFrozenNo;

    private String toFrozenNo;

    private String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId == null ? null : sourceId.trim();
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType == null ? null : sourceType.trim();
    }

    public String getSettleNo() {
        return settleNo;
    }

    public void setSettleNo(String settleNo) {
        this.settleNo = settleNo == null ? null : settleNo.trim();
    }

    public String getSettleOderType() {
        return settleOderType;
    }

    public void setSettleOderType(String settleOderType) {
        this.settleOderType = settleOderType == null ? null : settleOderType.trim();
    }

    public Long getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(Long fromUserId) {
        this.fromUserId = fromUserId;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName == null ? null : fromUserName.trim();
    }

    public Long getToUserId() {
        return toUserId;
    }

    public void setToUserId(Long toUserId) {
        this.toUserId = toUserId;
    }

    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName == null ? null : toUserName.trim();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getExpecteServiceCharge() {
        return expecteServiceCharge;
    }

    public void setExpecteServiceCharge(BigDecimal expecteServiceCharge) {
        this.expecteServiceCharge = expecteServiceCharge;
    }

    public BigDecimal getActualServiceCharge() {
        return actualServiceCharge;
    }

    public void setActualServiceCharge(BigDecimal actualServiceCharge) {
        this.actualServiceCharge = actualServiceCharge;
    }

    public Date getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(Date paymentTime) {
        this.paymentTime = paymentTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
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

    public String getFromFrozenNo() {
        return fromFrozenNo;
    }

    public void setFromFrozenNo(String fromFrozenNo) {
        this.fromFrozenNo = fromFrozenNo == null ? null : fromFrozenNo.trim();
    }

    public String getToFrozenNo() {
        return toFrozenNo;
    }

    public void setToFrozenNo(String toFrozenNo) {
        this.toFrozenNo = toFrozenNo == null ? null : toFrozenNo.trim();
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

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}