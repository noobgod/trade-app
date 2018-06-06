package com.xianjinxia.trade.platform.status;

/**
 * Created by fanmaowen on 2017/11/27 0027.
 */
public enum BindCardStatus {

    //订单审核结果枚举值
    SUCCESS(1,"绑卡成功"),
    FAILURE(2,"绑卡失败");
    private int code;
    private String msg;

    BindCardStatus(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
