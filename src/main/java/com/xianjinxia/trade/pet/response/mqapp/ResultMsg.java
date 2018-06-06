package com.xianjinxia.trade.pet.response.mqapp;

public class ResultMsg {

    public static final String RESULT_SUCCESS="00";

    public static final String RESULT_FAIL="-1";

    public static final String RESULT_RETRY="999";

    private String code;

    private String msg;

    public ResultMsg(){}

    public ResultMsg(String code, String msg){
        this.code=code;
        this.msg=msg;
    }

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
}
