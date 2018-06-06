package com.xianjinxia.trade.shared.constant;

/**
 * 存放所有放置发送给active mq的队列名称
 * Created by MJH on 2017/9/5.
 */
public class QueueConst {
    /** 放款请求发出后，收到支付中心的结果，若放款成功，发送给 userapp-app 的消息，放到此队列 */
    public static final String PAYMENT_SUCESS_RESULT_QUEUE="trd_success_queue";

    /** 调用cashman接口 归还用户额度（mq消息）放到此队列 */
    public static final String RETURN_QUOTA="trd_return_quota";

    /** 调用MqClient的发送放款消息，推送给支付中心进行放款  放到此队列 */
    public static final String TRADE_QUEUE="trd_payment_queue";

    /** 调用MqClient的确认借款 小额 推送给后台所需消息 */
    public static final String CONFIRM_LOAN_SMALL_QUEUE ="trd_confirm_loan_small_queue";

    /** 调用MqClient的确认借款 大额 推送给所需消息 */
    public static final String CONFIRM_LOAN_BIG_RISK_QUEUE ="trd_confirm_order_big_risk_queue";


    /** 借款申请落表后发送给loanark-app的队列 */
    public static final String APPLY_LOAN_NOTIFY_QUEUE ="trd_apply_order_notify_queue";

    /** 确认借款，生成订单，以及落trace表之后，发送消息通知cashman-app生成还款业务订单和还款计划,发送给 userapp-app 的消息，放到此队列 */
    public static final String ORDER_CONFIRM_SUCCESS_QUEUE="trd_order_confirm_success_queue";
    
    /** 确认商品申请，生成订单，以及落trace表之后，发送消息通知cashman-app生成还款业务订单和还款计划,发送给 userapp-app 的消息，放到此队列 */
    public static final String SHOPPING_ORDER_CONFIRM_SUCCESS_QUEUE="trd_shopping_order_confirm_success_queue";

    /** 风控审核结果，通知product-app解冻产品库存 */
    public static final String PRODUCT_UNFREEZE_QUEUE="trd_unfreeze_product_queue";
    
    /** 定单发货时，发送给cashman-app异步更新还款计划的预期还款时间 */
    public static final String SYNC_REPAYMENT_PLAN_TIME="trd_sync_repayment_plan_time";
    /** 虚拟定单下单失败时，发送给cashman-app异步更新订单状态 */
    public static final String SYNC_VIRTUAL_ORDER_STATUS="trd_sync_virtual_order_status";

    public static final String ACTIVITY_SHOPPING_PAY="trd_activity_pay";

}
