package com.xianjinxia.trade.platform.request;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.xianjinxia.trade.app.request.BaseRequest;
import com.xianjinxia.trade.shared.idempotent.IdempotentKey;

public class LoanOrderStatusReq extends BaseRequest  {
	
	@NotNull(message="order_no couldn't be null")
	@IdempotentKey(order=1)
	private String order_no;
	
	@NotNull(message="order_status couldn't be null")
	@NotEmpty(message="order_status couldn't be empty")
	@IdempotentKey(order=2)
	private String order_status;
	
	@NotNull(message="update_time couldn't be null")
	private Long update_time;

	public String getOrder_no() {
		return order_no;
	}

	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}

	public String getOrder_status() {
		return order_status;
	}

	public void setOrder_status(String order_status) {
		this.order_status = order_status;
	}

	public Long getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(Long update_time) {
		this.update_time = update_time;
	}

	@Override
	public String toString() {
		return "LoanOrderStatusReq [order_no=" + order_no + ", order_status=" + order_status + ", update_time="
				+ update_time + "]";
	}


}
