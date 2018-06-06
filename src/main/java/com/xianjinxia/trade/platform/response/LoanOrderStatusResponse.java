package com.xianjinxia.trade.platform.response;

public class LoanOrderStatusResponse  {
	
	private String order_no;
	
	private String order_status;
	
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
		return "LoanOrderStatusResponse [order_no=" + order_no + ", order_status=" + order_status + ", update_time="
				+ update_time + "]";
	}

	

}
