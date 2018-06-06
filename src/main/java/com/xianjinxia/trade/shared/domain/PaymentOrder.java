package com.xianjinxia.trade.shared.domain;

import java.util.Date;

public class PaymentOrder {
    private Long id;

    private Integer paymentAmount;

    private String paymentSeqNo;

    private Date createdTime;

    private String createdUser;

    private Date updatedTime;

    private Boolean dataValid;

    private String financeChannelFlag;

    private String rcvBankCardNo;

    private String rcvBankUnionNo;

    private String rcvBankAcctName;

    private String paymentOrderNo;

    private String retMsg;


    private Integer paymentOrderType;

    private String paymentRemark;

    private Date paymentTime;

    private Long trdOrderId;

    /**
     * 放款渠道
     */
    private String paymentChannel;

    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(Integer paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public String getPaymentSeqNo() {
        return paymentSeqNo;
    }

    public void setPaymentSeqNo(String paymentSeqNo) {
        this.paymentSeqNo = paymentSeqNo;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(String createdUser) {
        this.createdUser = createdUser;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Boolean getDataValid() {
        return dataValid;
    }

    public void setDataValid(Boolean dataValid) {
        this.dataValid = dataValid;
    }

    public String getFinanceChannelFlag() {
        return financeChannelFlag;
    }

    public void setFinanceChannelFlag(String financeChannelFlag) {
        this.financeChannelFlag = financeChannelFlag;
    }

    public String getRcvBankCardNo() {
        return rcvBankCardNo;
    }

    public void setRcvBankCardNo(String rcvBankCardNo) {
        this.rcvBankCardNo = rcvBankCardNo;
    }

    public String getRcvBankUnionNo() {
        return rcvBankUnionNo;
    }

    public void setRcvBankUnionNo(String rcvBankUnionNo) {
        this.rcvBankUnionNo = rcvBankUnionNo;
    }

    public String getRcvBankAcctName() {
        return rcvBankAcctName;
    }

    public void setRcvBankAcctName(String rcvBankAcctName) {
        this.rcvBankAcctName = rcvBankAcctName;
    }

    public String getPaymentOrderNo() {
        return paymentOrderNo;
    }

    public void setPaymentOrderNo(String paymentOrderNo) {
        this.paymentOrderNo = paymentOrderNo;
    }

    public String getRetMsg() {
        return retMsg;
    }

    public void setRetMsg(String retMsg) {
        this.retMsg = retMsg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getPaymentOrderType() {
        return paymentOrderType;
    }

    public void setPaymentOrderType(Integer paymentOrderType) {
        this.paymentOrderType = paymentOrderType;
    }

    public String getPaymentRemark() {
        return paymentRemark;
    }

    public void setPaymentRemark(String paymentRemark) {
        this.paymentRemark = paymentRemark;
    }

    public Date getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(Date paymentTime) {
        this.paymentTime = paymentTime;
    }

    public Long getTrdOrderId() {
        return trdOrderId;
    }

    public void setTrdOrderId(Long trdOrderId) {
        this.trdOrderId = trdOrderId;
    }

    public String getPaymentChannel() {
        return paymentChannel;
    }

    public void setPaymentChannel(String paymentChannel) {
        this.paymentChannel = paymentChannel;
    }

    @Override
    public String toString() {
        return "PaymentOrder{" +
                "id=" + id +
                ", paymentAmount=" + paymentAmount +
                ", paymentSeqNo='" + paymentSeqNo + '\'' +
                ", createdTime=" + createdTime +
                ", createdUser='" + createdUser + '\'' +
                ", updatedTime=" + updatedTime +
                ", dataValid=" + dataValid +
                ", financeChannelFlag='" + financeChannelFlag + '\'' +
                ", rcvBankCardNo='" + rcvBankCardNo + '\'' +
                ", rcvBankUnionNo='" + rcvBankUnionNo + '\'' +
                ", rcvBankAcctName='" + rcvBankAcctName + '\'' +
                ", paymentOrderNo='" + paymentOrderNo + '\'' +
                ", retMsg='" + retMsg + '\'' +
                ", status='" + status + '\'' +
                ", paymentOrderType=" + paymentOrderType +
                ", paymentRemark='" + paymentRemark + '\'' +
                ", paymentTime=" + paymentTime +
                ", trdOrderId=" + trdOrderId +
                ", paymentChannel='" + paymentChannel + '\'' +
                '}';
    }
}