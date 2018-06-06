package com.xianjinxia.trade.shared.constant;

/**
 * Created by liquan on 2017/12/29.
 */
public class SmsConstant {

    public  static final String SMS_MSG_SOURCE = "trade-app";

    /** 提交申请成功后，短信模板ID 即时*/
    public static final String SMS_APPLY_SUCCESS_ID = "notification-40";
    /**风控审核结果,失败后，短信模板ID 即时*/
    public static final String SMS_RISK_FAILED_ID = "notification-41";
    /**审核不通过解禁后 notification-43 9:30:00*/
    public static final String SMS_UNFREEZE_ID = "notification-43";
    /** 额度上限 暂无编号 即时*/ //TODO 待确认该编号 暂无
    public static final String SMS_EXCEED_LIMITED_ID ="notification-";
    /** notification-44	放款结果通知--成功 即时*/
    public static final String SMS_LOAN_SUCCESS_ID ="notification-44";
    /** notification-45	放款结果通知--失败 即时*/
    public static final String SMS_LOAN_FAILED_ID ="notification-45";
    /**notification-43	审核不通过解禁 9:30	审核不通过解禁后
     暂无编号	额度上限   即时	人工审核不通过
     notification-44	放款结果通知--成功 即时
     notification-45	放款结果通知--失败 即时
     notification-46	还款日前提醒 9:30 每期到期前2日（未逾期/未结清）
     notification-47	还款日提醒 10:30 每期到期日（未逾期/未结清）
     notification-48	还款成功--部分期数（含多期）11:30:00
     notification-49	还款成功--全部期数（未提额）AM 10:00:00
     notification-51	逾期首日 AM 9:30:00 逾期未还款-首日
     暂无编号	支付失败提醒 主动还款、定时代扣等
     暂无编号	支付成功提醒 主动还款、定时代扣等*/
    /**notification-46	还款日前提醒 9:30 每期到期前2日（未逾期/未结清）*/
    public static final String SMS_EXPIRATION_REMINDER_ADVANCE_ID ="notification-46";
    /**notification-47	还款日提醒 10:30 每期到期日（未逾期/未结清）*/
    public static final String SMS_EXPIRATION_REMINDER_ID ="notification-47";
    /**notification-48	还款成功--部分期数（含多期）11:30:00*/
    public static final String SMS_PAYMENT_SUCCESS_PART_ID ="notification-48";
    /**notification-49	还款成功--全部期数（未提额）AM 10:00:00*/
    public static final String SMS_LOAN_SUCCESS_ALL_ID ="notification-49";
    /**notification-51	逾期首日 AM 9:30:00 逾期未还款-首日*/
    public static final String SMS_LATE_REMIND_ID ="notification-51";
    /**暂无编号	支付失败提醒 主动还款、定时代扣等*/ //TODO 待确认该编号 暂无
    public static final String SMS_PAYMENT_FAILED_ID ="notification-";
    /**暂无编号	支付成功提醒 主动还款、定时代扣等*/ //TODO 待确认该编号 暂无
    public static final String SMS_PAYMENT_SUCCESS_ID ="notification-";
    /**虚拟商品卡密发送*/
    public static final String SMS_VIRTUAL__CARD_SEND_ID ="notification-63";


}
