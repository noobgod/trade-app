package com.xianjinxia.trade.platform.status;

/**
 * 订单审核结果枚举
 * Created by liquan on 2017/11/25.
 */
public enum OrderAuditResults {
    //订单审核结果枚举值
    APPROVE("10","审核通过"),
    REFUSED("40","审核拒绝");
    private String code;
    private String description;

    OrderAuditResults(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
