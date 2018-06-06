package com.xianjinxia.trade.app.request;

import java.io.Serializable;
import javax.validation.constraints.NotNull;

/**
 * 接收cashman-app发出的用户还款成功的消息（大额分期是所有的都还完）
 */
public class RepaymentSuccessReq extends BaseRequest implements Serializable {

	private static final long serialVersionUID = -130602646147091273L;

    /**
     * 订单ID
     */
    @NotNull(message="loanOrderId can't be null")
    private Long loanOrderId;

    public Long getLoanOrderId() {
        return loanOrderId;
    }

    public void setLoanOrderId(Long loanOrderId) {
        this.loanOrderId = loanOrderId;
    }
}




