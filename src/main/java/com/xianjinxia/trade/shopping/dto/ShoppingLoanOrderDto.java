package com.xianjinxia.trade.shopping.dto;

import com.xianjinxia.trade.app.response.LoanBaseResponse;
import com.xianjinxia.trade.shared.enums.LoanCodeMsgEnum;

/**
 * 
 * @author chunliny
 *
 */
public class ShoppingLoanOrderDto extends LoanBaseResponse {

	private String traceNo ;
    
    private Long shoppingLoanOrderId;

	private String unToken;

    public ShoppingLoanOrderDto(LoanCodeMsgEnum code) {
        super(code);
    }

    public String getTraceNo() {
        return traceNo;
    }

    public void setTraceNo(String traceNo) {
        this.traceNo = traceNo;
    }
    
    public Long getShoppingLoanOrderId() {
		return shoppingLoanOrderId;
	}

	public void setShoppingLoanOrderId(Long shoppingLoanOrderId) {
		this.shoppingLoanOrderId = shoppingLoanOrderId;
	}
	
    public String getUnToken() {
		return unToken;
	}

	public void setUnToken(String unToken) {
		this.unToken = unToken;
	}
}
