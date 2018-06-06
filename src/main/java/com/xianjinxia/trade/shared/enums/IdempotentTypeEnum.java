package com.xianjinxia.trade.shared.enums;

/**
 * Created by fanmaowen on 2017/11/27 0027.
 */
public enum IdempotentTypeEnum {

    SYNC_ORDER_STATUS("sync_order_status"),
	ORDER_TRACE("order_trace"),
    BIND_CARD_RECALL("bind_card_recall"),
    CONFIRM_LOAN("confirm_loan"),
    RECEIVE_ORDER_AUDIT("receive_order_audit"),

    SYNC_ORDER_STATUS_TO_TRADE("sync_loan_order_status_to_trade"),
    
    LOGISTICS_SHIPMENT_LOAN("logistics_shipment_loan"),
    
    CONFIRM_RECEIPT_LOAN("confirmReceiptLoan"),
    
    SYNC_SHOPPING_ORDER_STATUS_TO_TRADE("sync_shopping_order_status_to_trade"),

    ACTIVITY_ORDER_APPLY("activity_order_apply"),
    ;



    IdempotentTypeEnum(String code){
        this.code=code;
    }

    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
