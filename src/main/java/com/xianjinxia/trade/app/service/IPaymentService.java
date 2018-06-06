package com.xianjinxia.trade.app.service;

import com.xianjinxia.trade.app.request.PaymentResult;

/**
 * Created by MJH on 2017/9/1.
 */
public interface IPaymentService {

    /**
     * 处理支付中心返回的 放款结果
     * @param paymentResult 支付中心的响应数据
     */
    void processPayResult(PaymentResult paymentResult);
}
