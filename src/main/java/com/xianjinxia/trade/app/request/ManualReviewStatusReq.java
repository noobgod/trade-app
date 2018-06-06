package com.xianjinxia.trade.app.request;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by fanmaowen on 2017/12/13 0013.
 */
public class ManualReviewStatusReq  extends BaseRequest{
    // 借款订单主键
    @ApiModelProperty(example = "1", value = "订单业务流水号",dataType ="Long")
    @NotNull(message = "id couldn't be null")
    private String bizSeqNo;

    //人工审核是否通过
    @NotNull(message = "manualReviewIsPass couldn't be null")
    private Integer manualReviewIsPass;

    public String getBizSeqNo() {
        return bizSeqNo;
    }

    public void setBizSeqNo(String bizSeqNo) {
        this.bizSeqNo = bizSeqNo;
    }

    public Integer getManualReviewIsPass() {
        return manualReviewIsPass;
    }

    public void setManualReviewIsPass(Integer manualReviewIsPass) {
        this.manualReviewIsPass = manualReviewIsPass;
    }

    @Override
    public String toString() {
        return "ManualReviewStatusReq{" +
                "bizSeqNo=" + bizSeqNo +
                ", manualReviewIsPass=" + manualReviewIsPass +
                '}';
    }
}
