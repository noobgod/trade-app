package com.xianjinxia.trade.platform.service;

import com.xianjinxia.trade.platform.plugin.annotation.PlatformApi;
import com.xianjinxia.trade.platform.plugin.annotation.PlatformApiNo;
import com.xianjinxia.trade.platform.request.BindCardResultReq;
import com.xianjinxia.trade.platform.request.LoanOrderStatusReq;
import com.xianjinxia.trade.platform.request.ReceiveOrderAuditReq;

@PlatformApi
public interface PlatformOpenApiService {


    /**
     * 订单审核-第三方回调，更改订单状态和落trace表
     *
     * @param receiveOrderAuditReq
     */
    @PlatformApiNo("100020")
    void loanAuditCallback(ReceiveOrderAuditReq receiveOrderAuditReq);


    /**
     * 1`12
     * 第三方机构调用，来更新订单状态
     *
     * @param loanOrderStatusReq
     */
    @PlatformApiNo("100050")
    void syncOrderStatus(LoanOrderStatusReq loanOrderStatusReq);

    @PlatformApiNo("100010")
    void bindCardRecall(BindCardResultReq bindCardResultReq);

}