package com.xianjinxia.trade.platform.request;

import javax.validation.constraints.NotNull;

public class LoanApplyRecallRequest {

    @NotNull(message = "orderNo no value")
    private String orderNo;

    @NotNull(message = "pushResult no value")
    private Boolean pushResult;

    @NotNull(message = "bizResult no value")
    private Boolean bizResult;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Boolean getPushResult() {
        return pushResult;
    }

    public void setPushResult(Boolean pushResult) {
        this.pushResult = pushResult;
    }

    public Boolean getBizResult() {
        return bizResult;
    }

    public void setBizResult(Boolean bizResult) {
        this.bizResult = bizResult;
    }
}
