package com.xianjinxia.trade.platform.service;


import com.xianjinxia.trade.platform.request.OpenApiConfirmLoanRequest;

public interface PlatformStandaloneService {

    void pushToOpenApi(String appId,OpenApiConfirmLoanRequest openApiConfirmLoanRequest);
}
