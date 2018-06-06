package com.xianjinxia.trade.pet.response;

import com.xianjinxia.trade.app.response.BaseResponse;

public class AssetBaseResponse<T> extends BaseResponse<T> {


    public enum ResponseCode {

        SUCCESS("00", "success"),

        PARAM_CHECK_FAIL("400", "request params check fail"),

        LOGIN_CHECK_FAIL("400", "login check fail"),

        FREQUENT_REQUEST("400", "frequent request"),

        BIZ_CHECK_FAIL("400", "biz check fail"),

        UNSUPPORT_REQUEST_FAIL("400", "unsupport request fail, like http 404"),

        SYS_ERROR("400", "system error");

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

    public AssetBaseResponse() {
        this.code = ResponseCode.SUCCESS.value;
        this.msg = ResponseCode.SUCCESS.description;
    }

    public AssetBaseResponse(T data) {
        this.code = ResponseCode.SUCCESS.value;
        this.msg = ResponseCode.SUCCESS.description;
        this.data=data;
    }


    public AssetBaseResponse(ResponseCode responseCode) {
        this.code = responseCode.getValue();
        this.msg = responseCode.getDescription();
    }

    public AssetBaseResponse(String code , String msg) {
        this.code = code;
        this.msg = msg;
    }


    public void paramCheckFail() {
        this.code = ResponseCode.PARAM_CHECK_FAIL.value;
        this.msg = ResponseCode.PARAM_CHECK_FAIL.description;
    }

    public void systemError() {
        this.code = ResponseCode.SYS_ERROR.value;
        this.msg = ResponseCode.SYS_ERROR.description;
    }
}
