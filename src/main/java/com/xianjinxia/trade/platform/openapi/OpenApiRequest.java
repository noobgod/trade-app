package com.xianjinxia.trade.platform.openapi;

public class OpenApiRequest {
    /** 接口编码 */
    private String method;
    /** 商户编码 */
    private String app_id;
    /** 请求数据 */
    private Object biz_data;

    public OpenApiRequest(String method, String app_id, Object biz_data) {
        this.method = method;
        this.app_id = app_id;
        this.biz_data = biz_data;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getApp_id() {
        return app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public Object getBiz_data() {
        return biz_data;
    }

    public void setBiz_data(Object biz_data) {
        this.biz_data = biz_data;
    }
}
