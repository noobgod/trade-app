package com.xianjinxia.trade.platform.dto;

import java.util.Date;

public class TraceDto {
	
	private String traceNo;
	
	private String orderEvent;
	
	private String eventText;
	
	private Date eventTime;

	public String getTraceNo() {
		return traceNo;
	}

	public void setTraceNo(String traceNo) {
		this.traceNo = traceNo;
	}

	public String getOrderEvent() {
		return orderEvent;
	}

	public void setOrderEvent(String orderEvent) {
		this.orderEvent = orderEvent;
	}

	public String getEventText() {
		return eventText;
	}

	public void setEventText(String eventText) {
		this.eventText = eventText;
	}

	public Date getEventTime() {
		return eventTime;
	}

	public void setEventTime(Date eventTime) {
		this.eventTime = eventTime;
	}
	
	

}
