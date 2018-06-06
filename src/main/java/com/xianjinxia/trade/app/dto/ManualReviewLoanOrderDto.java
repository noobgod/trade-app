package com.xianjinxia.trade.app.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by fanmaowen on 2017/12/13 0013.
 */
public class ManualReviewLoanOrderDto {

    @ApiModelProperty(example = "1", value = "订单号",dataType ="String")
    private String bizSeqNo;

    //订单状态
    private String status;

    public String getBizSeqNo() {
        return bizSeqNo;
    }

    public void setBizSeqNo(String bizSeqNo) {
        this.bizSeqNo = bizSeqNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ManualReviewLoanOrderDto{" +
                "bizSeqNo=" + bizSeqNo +
                ", status='" + status + '\'' +
                '}';
    }
}
