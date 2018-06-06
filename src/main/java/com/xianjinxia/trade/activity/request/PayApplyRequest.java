package com.xianjinxia.trade.activity.request;

import com.xianjinxia.trade.app.request.BaseRequest;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class PayApplyRequest extends BaseRequest {

    @NotNull(message = "orderId couldn't be null")
    @ApiModelProperty(name = "orderId",value = "订单编号",example = "1",required = true,dataType = "Long")
    private Long orderId;

    @NotNull(message = "bankCardId couldn't be null")
    @ApiModelProperty(name = "bankCardId",value = "银行卡ID",example = "1",required = true,dataType = "Long")
    private Long bankCardId;

    @NotNull(message = "userId couldn't be null")
    @ApiModelProperty(name = "userId",value = "用户ID",example = "1",required = true,dataType = "Long")
    private Long userId;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getBankCardId() {
        return bankCardId;
    }

    public void setBankCardId(Long bankCardId) {
        this.bankCardId = bankCardId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "OrderPayRequest{" +
                "orderId=" + orderId +
                ", bankCardId=" + bankCardId +
                ", userId=" + userId +
                '}';
    }
}
