package com.xianjinxia.trade.platform.request;

import javax.validation.constraints.NotNull;

import com.xianjinxia.trade.app.request.BaseRequest;

public class CancelOrderReq extends BaseRequest {
	
	@NotNull(message="userId couldn't be null")
	private Long userId;
	
	@NotNull(message="orderNo couldn't be null")
	private String orderNo;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	@Override
	public String toString() {
		return "CancelOrderReq [userId=" + userId + ", orderNo=" + orderNo + "]";
	}

}
