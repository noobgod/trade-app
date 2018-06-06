package com.xianjinxia.trade.shared.domain;

import java.math.BigDecimal;

public class TransferReqeustDetails {
    private Long id;

    private Long transferRequestId;

    private Long fromUserId;

    private BigDecimal coinNum;

    private Long mediumId;

    private Long toUserId;

    private String transactionType;

    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTransferRequestId() {
        return transferRequestId;
    }

    public void setTransferRequestId(Long transferRequestId) {
        this.transferRequestId = transferRequestId;
    }

    public Long getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(Long fromUserId) {
        this.fromUserId = fromUserId;
    }

    public BigDecimal getCoinNum() {
        return coinNum;
    }

    public void setCoinNum(BigDecimal coinNum) {
        this.coinNum = coinNum;
    }

    public Long getMediumId() {
        return mediumId;
    }

    public void setMediumId(Long mediumId) {
        this.mediumId = mediumId;
    }

    public Long getToUserId() {
        return toUserId;
    }

    public void setToUserId(Long toUserId) {
        this.toUserId = toUserId;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType == null ? null : transactionType.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }
}