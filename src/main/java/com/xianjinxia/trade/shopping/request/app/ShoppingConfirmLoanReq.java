package com.xianjinxia.trade.shopping.request.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

import com.xianjinxia.trade.app.request.BaseRequest;

/**
 * Created by chunliny on 2018/1/18
 */
@ApiModel
public class ShoppingConfirmLoanReq extends BaseRequest {

	@ApiModelProperty(name = "shoppingLoanOrderId",value = "商品系统订单号",example = "1",required = true,dataType = "Long")
    @NotNull(message="shoppingLoanOrderId couldn't be null")
	private Long shoppingLoanOrderId;
	
	public Long getShoppingLoanOrderId() {
		return shoppingLoanOrderId;
	}

	public void setShoppingLoanOrderId(Long shoppingLoanOrderId) {
		this.shoppingLoanOrderId = shoppingLoanOrderId;
	}
}
