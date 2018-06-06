package com.xianjinxia.trade.app.service;

import com.xianjinxia.trade.shared.domain.LoanOrder;

public interface IPayCenterService {
    /**
     * 调用支付中心进行支付
     * 
     * @param loanOrder
     */
    void paymentRequest(LoanOrder loanOrder);

}
