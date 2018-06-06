package com.xianjinxia.trade.shared.constant;

public class LoanOrderConstants {
    // 借款申请推送失败的情况下，最大的重试推送次数
    public static final int MAX_RETRY_PUSH_TIMES = 3;
    // 支付中心发起支付请求的Request Source(cashman-app大额订单请求source)
    public static final String APPLICATION_PAYMENT_SOURCE = "A5";
}
