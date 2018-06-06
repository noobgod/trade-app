package com.xianjinxia.trade.app.response;

/**
 * @author tennyqin
 * @version 1.0
 * @title BaseResponse.java
 * @created 2017年8月30日
 */
public class BaseResponse<T> {


    public enum ResponseCode {

        SUCCESS("00", "success"),

        PARAM_CHECK_FAIL("01", "request params check fail"),

        LOGIN_CHECK_FAIL("02", "login check fail"),

        FREQUENT_REQUEST("03", "frequent request"),

        BIZ_CHECK_FAIL("04", "biz check fail"),

        UNSUPPORT_REQUEST_FAIL("05", "unsupport request fail, like http 404"),

        SYS_ERROR("99", "system error");

        private String value;

        private String description;

        ResponseCode(String value, String description) {
            this.value = value;
            this.description = description;
        }

        public String getValue() {
            return value;
        }

        public String getDescription() {
            return description;
        }

    }

    // 返回状态吗
    protected String code;
    // 返回说明
    protected String msg;
    //和前端兼容
    private String message;
    // 自定义返回数据
    protected T data;

    public BaseResponse() {
        this.code = ResponseCode.SUCCESS.value;
        this.msg = ResponseCode.SUCCESS.description;
        this.message = this.msg ;
    }

    public BaseResponse(T data) {
        this.code = ResponseCode.SUCCESS.getValue();
        this.msg = ResponseCode.SUCCESS.getDescription();
        this.message = this.msg ;
        this.data = data;
    }


    public BaseResponse(ResponseCode responseCode) {
        this.code = responseCode.getValue();
        this.msg = responseCode.getDescription();
        this.message = this.msg ;
    }

    public BaseResponse(String code, String msg) {
        this.code = code;
        this.msg = msg;
        this.message = this.msg ;
    }


    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
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
        this.message = msg ;
    }

    public void paramCheckFail() {
        this.code = ResponseCode.PARAM_CHECK_FAIL.value;
        this.msg = ResponseCode.PARAM_CHECK_FAIL.description;
        this.message = this.msg ;
    }

    public void systemError() {
        this.code = ResponseCode.SYS_ERROR.value;
        this.msg = ResponseCode.SYS_ERROR.description;
        this.message = this.msg ;
    }

    public void serviceError(String msg){
        this.code = ResponseCode.SYS_ERROR.value;
        this.msg = msg;
        this.message = this.msg ;
    }
}