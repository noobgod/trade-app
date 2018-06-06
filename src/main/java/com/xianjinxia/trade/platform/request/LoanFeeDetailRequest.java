package com.xianjinxia.trade.platform.request;

public class LoanFeeDetailRequest {

    private String feeName;

    private String feeType;

    private Integer feeAmount;

    private String collectMode;

    public String getFeeName() {
        return feeName;
    }

    public void setFeeName(String feeName) {
        this.feeName = feeName;
    }

    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    public Integer getFeeAmount() {
        return feeAmount;
    }

    public void setFeeAmount(Integer feeAmount) {
        this.feeAmount = feeAmount;
    }

    public String getCollectMode() {
        return collectMode;
    }

    public void setCollectMode(String collectMode) {
        this.collectMode = collectMode;
    }
}
