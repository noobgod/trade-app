package com.xianjinxia.trade.platform.request;

import javax.validation.constraints.NotNull;

import com.xianjinxia.trade.app.request.BaseRequest;

public class TraceReq extends BaseRequest{
	
	@NotNull(message="userId couldn't be null")
	private Long userId;
	
	@NotNull(message="orderNo couldn't be null")
	private String orderNo;
	

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "TraceReq [userId=" + userId + ", orderNo=" + orderNo + "]";
	}

	

}
