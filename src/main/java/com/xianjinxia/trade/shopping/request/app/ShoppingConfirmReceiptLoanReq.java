package com.xianjinxia.trade.shopping.request.app;

import com.xianjinxia.trade.shared.idempotent.IdempotentKey;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

import com.xianjinxia.trade.app.request.BaseRequest;

/**
 * Created by chunliny on 2018/2/09
 */
@ApiModel
public class ShoppingConfirmReceiptLoanReq extends BaseRequest {
	
    @NotNull(message = "shopping sloan order couldn't be null")
    @ApiModelProperty(name = "s",value = "借款订单ID",example = "101",required = true,dataType = "long")
	@IdempotentKey(order=1)
    private Long shoppingLoanOrderId;

	public Long getShoppingLoanOrderId() {
		return shoppingLoanOrderId;
	}

	public void setShoppingLoanOrderId(Long shoppingLoanOrderId) {
		this.shoppingLoanOrderId = shoppingLoanOrderId;
	}
}
