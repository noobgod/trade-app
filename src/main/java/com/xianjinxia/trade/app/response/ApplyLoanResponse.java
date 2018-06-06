package com.xianjinxia.trade.app.response;

import com.xianjinxia.trade.shared.enums.LoanCodeMsgEnum;

/**
 * Created by Administrator on 2017/9/18 0018.
 */
public class ApplyLoanResponse extends LoanBaseResponse {

    private String traceNo ;

    public ApplyLoanResponse(LoanCodeMsgEnum code) {
        super(code);
    }

    public String getTraceNo() {
        return traceNo;
    }

    public void setTraceNo(String traceNo) {
        this.traceNo = traceNo;
    }
}
