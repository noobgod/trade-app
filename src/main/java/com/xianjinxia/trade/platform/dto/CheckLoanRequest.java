package com.xianjinxia.trade.platform.dto;

import com.xianjinxia.trade.app.request.BaseRequest;

import javax.validation.constraints.NotNull;

public class CheckLoanRequest extends BaseRequest {

    @NotNull(message = "userId no value")
    private Long userId;

    @NotNull(message = "serviceCompany no value")
    private String serviceCompany;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getServiceCompany() {
        return serviceCompany;
    }

    public void setServiceCompany(String serviceCompany) {
        this.serviceCompany = serviceCompany;
    }
}
