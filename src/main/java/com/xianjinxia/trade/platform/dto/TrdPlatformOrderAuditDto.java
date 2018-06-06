package com.xianjinxia.trade.platform.dto;

/**
 * Created by liquan on 2017/11/25.
 */
public class TrdPlatformOrderAuditDto {

    private String orderNo;

    private String status;

    private String traceNo;

    private String serviceCompany;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTraceNo() {
        return traceNo;
    }

    public void setTraceNo(String traceNo) {
        this.traceNo = traceNo;
    }

    public String getServiceCompany() {
        return serviceCompany;
    }

    public void setServiceCompany(String serviceCompany) {
        this.serviceCompany = serviceCompany;
    }
}
