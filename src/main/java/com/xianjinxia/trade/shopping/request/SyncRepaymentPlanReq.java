package com.xianjinxia.trade.shopping.request;

import com.xianjinxia.trade.app.request.BaseRequest;
import com.xianjinxia.trade.shared.idempotent.IdempotentKey;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class SyncRepaymentPlanReq extends BaseRequest {

    @IdempotentKey(order=1)
    @NotNull(message="trdLoanOrderId could not be null")
    private Long trdLoanOrderId;

    @NotNull(message="paymentPlanTime could not be null")
    private Date paymentPlanTime;

    public SyncRepaymentPlanReq(Long trdLoanOrderId, Date paymentPlanTime) {
        super();
        this.trdLoanOrderId = trdLoanOrderId;
        this.paymentPlanTime = paymentPlanTime;
    }


    public Long getTrdLoanOrderId() {
        return trdLoanOrderId;
    }

    public void setTrdLoanOrderId(Long trdLoanOrderId) {
        this.trdLoanOrderId = trdLoanOrderId;
    }

    public Date getPaymentPlanTime() {
        return paymentPlanTime;
    }

    public void setPaymentPlanTime(Date paymentPlanTime) {
        this.paymentPlanTime = paymentPlanTime;
    }

    @Override
    public String toString() {
        return "SyncRepaymentPlanReq [trdLoanOrderId=" + trdLoanOrderId
                + ", paymentPlanTime=" + paymentPlanTime + "]";
    }


}
