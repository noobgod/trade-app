package com.xianjinxia.trade.app.response;

import com.xianjinxia.trade.shared.enums.LoanCodeMsgEnum;

/**
 * Created by Administrator on 2017/9/17 0017.
 */
public class ConfirmLoanResponse extends LoanBaseResponse{

    private String  orderId;

    public ConfirmLoanResponse(LoanCodeMsgEnum code) {
        super(code);
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
