package com.xianjinxia.trade.pet.dto;

import java.math.BigDecimal;

/**
 * Created by Myron on 2018/2/28.
 */
public class BaseAssetRespDto {
    private String traceNo;
    private String flNo;
    private BigDecimal gasUsed;
    private String txNo;
    private String channel;

    public String getTraceNo() {
        return traceNo;
    }

    public void setTraceNo(String traceNo) {
        this.traceNo = traceNo;
    }

    public String getFlNo() {
        return flNo;
    }

    public void setFlNo(String flNo) {
        this.flNo = flNo;
    }

    public BigDecimal getGasUsed() {
        return gasUsed;
    }

    public void setGasUsed(BigDecimal gasUsed) {
        this.gasUsed = gasUsed;
    }

    public String getTxNo() {
        return txNo;
    }

    public void setTxNo(String txNo) {
        this.txNo = txNo;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }
}
