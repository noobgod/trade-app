package com.xianjinxia.trade.app.dto;


import com.xianjinxia.trade.shared.domain.LoanOrder;

/*
 * 风控需要的一些用户订单信息
 */
public class LoanOrderRiskDto {

    /**
     * 用户最近被拒的订单信息
     */
    private LoanOrder lastRejectLoanOrder;

    /**
     * 用户当前最新带审核的订单信息
     */
    private LoanOrder currentApplyLoanOrder;

    public LoanOrder getLastRejectLoanOrder() {
        return lastRejectLoanOrder;
    }

    public void setLastRejectLoanOrder(LoanOrder lastRejectLoanOrder) {
        this.lastRejectLoanOrder = lastRejectLoanOrder;
    }

    public LoanOrder getCurrentApplyLoanOrder() {
        return currentApplyLoanOrder;
    }

    public void setCurrentApplyLoanOrder(LoanOrder currentApplyLoanOrder) {
        this.currentApplyLoanOrder = currentApplyLoanOrder;
    }
}
