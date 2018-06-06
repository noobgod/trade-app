package com.xianjinxia.trade.pet.response;

/**
 * @author tennyqin
 * @version 1.0
 * @title PetBaseResponse.java
 * @created 2017年8月30日
 */
public class PetBaseResponse<T> {


    public enum ResponseCode {

        SUCCESS("00", "success"),

        PARAM_CHECK_FAIL("01", "request params check fail"),

        LOGIN_CHECK_FAIL("02", "login check fail"),

        FREQUENT_REQUEST("03", "frequent request"),

        BIZ_CHECK_FAIL("04", "biz check fail"),

        UNSUPPORT_REQUEST_FAIL("05", "unsupport request fail, like http 404"),

        PET_SELLED("10001", "该宠物已经被交易"),

        PET_FROZEN_FAIL("10002", "宠物冻结失败"),

        PET_FROZEN("10003", "该宠物已经被冻结"),

        PET_ONGOING("10004", "已经有人在购买该宠物"),

        OPER_SUCCESS("10005", "操作已经完成，请勿重复操作"),

        OPER_UNSALE_QUICK("10006", "上下架过于频繁"),

        COIN_NOT_ENOUGH("10007", "积分不足"),

        PET_PRICE_CHANGED("10008", "价格已经产生变动"),

        COIN_FROZEN_FAIL("10009", "积分冻结失败"),

        PET_SELLING("10010", "该宠物正在出售"),

        PET_BELONGED("10011", "宠物已经属于您了"),

        BIZ_FAIL("4000", "业务数据异常，请重试"),

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

    public PetBaseResponse() {
        this.code = ResponseCode.SUCCESS.value;
        this.msg = ResponseCode.SUCCESS.description;
        this.message = this.msg ;
    }

    public PetBaseResponse(T data) {
        this.code = ResponseCode.SUCCESS.getValue();
        this.msg = ResponseCode.SUCCESS.getDescription();
        this.message = this.msg ;
        this.data = data;
    }


    public PetBaseResponse(ResponseCode responseCode) {
        this.code = responseCode.getValue();
        this.msg = responseCode.getDescription();
        this.message = this.msg ;
    }

    public PetBaseResponse(String code, String msg) {
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