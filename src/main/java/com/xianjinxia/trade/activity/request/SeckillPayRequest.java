package com.xianjinxia.trade.activity.request;

import com.xianjinxia.trade.app.request.BaseRequest;
import com.xianjinxia.trade.app.response.BaseResponse;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

public class SeckillPayRequest extends BaseRequest {

    private Long bizId;
    private String bizSeqNo;
    private Long userId;
    private Long bankId;
    private Long withholdingAmount;

    public Long getBizId() {
        return bizId;
    }

    public void setBizId(Long bizId) {
        this.bizId = bizId;
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

    public Long getBankId() {
        return bankId;
    }

    public void setBankId(Long bankId) {
        this.bankId = bankId;
    }

    public Long getWithholdingAmount() {
        return withholdingAmount;
    }

    public void setWithholdingAmount(Long withholdingAmount) {
        this.withholdingAmount = withholdingAmount;
    }

    public SeckillPayRequest(Long bizId, String bizSeqNo, Long userId, Long bankId, Long withholdingAmount) {
        this.bizId = bizId;
        this.bizSeqNo = bizSeqNo;
        this.userId = userId;
        this.bankId = bankId;
        this.withholdingAmount = withholdingAmount;
    }

    public SeckillPayRequest() {
    }
}
