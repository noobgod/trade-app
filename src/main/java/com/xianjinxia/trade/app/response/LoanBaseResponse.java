package com.xianjinxia.trade.app.response;


import com.xianjinxia.trade.shared.enums.LoanCodeMsgEnum;

/**
 * Created by fanmaowen on 2017/9/26 0026.
 */
public class LoanBaseResponse {

    private String  code;

    private String  msg ;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public LoanBaseResponse(LoanCodeMsgEnum code){
        this.code =code.getCode();
        this.msg =code.getValue();
    }

}
