package com.xianjinxia.trade.shopping.dto;

import com.xianjinxia.trade.app.response.LoanBaseResponse;
import com.xianjinxia.trade.shared.enums.LoanCodeMsgEnum;

public class ShoppingTraceDto extends LoanBaseResponse {
	
	private String traceNo;

	public ShoppingTraceDto(LoanCodeMsgEnum code, String traceNo) {
		super(code);
		this.traceNo = traceNo;
	}
	
	public String getTraceNo() {
		return traceNo;
	}

	public void setTraceNo(String traceNo) {
		this.traceNo = traceNo;
	}
}
