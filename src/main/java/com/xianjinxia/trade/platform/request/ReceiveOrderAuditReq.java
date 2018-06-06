package com.xianjinxia.trade.platform.request;

import com.xianjinxia.trade.app.request.BaseRequest;
import com.xianjinxia.trade.shared.idempotent.IdempotentKey;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

/**
 * 订单审核结果通知-（第三方回调）
 * Created by liquan on 2017/11/25.
 */
public class ReceiveOrderAuditReq extends BaseRequest{
    /**
     * 订单编号
     */
    @NotNull(message="orderNo couldn't be null")
    @ApiModelProperty(name = "orderNo",value = "订单编号",example = "lo20171017185759242tx",required = true,dataType = "String")
    @IdempotentKey(order=1)
    private String orderNo;
    /**
     * 审批结论--返回码
     */
    @NotNull(message="conclusion couldn't be null")
    @ApiModelProperty(name = "conclusion",value = "审批结论",example = "10",required = true,dataType = "Integer")
    private String conclusion;

    /**
     * 返回结果描述
     */
    @ApiModelProperty(name = "remark",value = "审批结论描述",example = "审核通过",required = false,dataType = "String")
    private String remark;

    /**
     * 审核时间
     */
    @NotNull(message="approvalTime couldn't be null")
    @ApiModelProperty(name = "approvalTime",value = "审核时间",example = "1356789908",required = true,dataType = "Long")
    private Long approvalTime;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getConclusion() {
        return conclusion;
    }

    public void setConclusion(String conclusion) {
        this.conclusion = conclusion;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getApprovalTime() {
        return approvalTime;
    }

    public void setApprovalTime(Long approvalTime) {
        this.approvalTime = approvalTime;
    }

    @Override
    public String toString(){
        return "ReceiveOrderAuditReq [orderNo=" + orderNo + ", conclusion="
                + conclusion + ", remark=" + remark + ", approvalTime=" + approvalTime +"]";
    }
}
