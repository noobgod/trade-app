package com.xianjinxia.trade.app.response;

import com.github.pagehelper.Page;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: cff
 * Date: 2017-12-26
 * Time: 下午 2:56
 */
public class BaseResponseExt<T> extends BaseResponse {

    private Integer waitAuditNum;
    private Integer loanNumberEnd;
    private Integer loanMoneyEnd;

    public Integer getWaitAuditNum() {
        return waitAuditNum;
    }

    public void setWaitAuditNum(Integer waitAuditNum) {
        this.waitAuditNum = waitAuditNum;
    }

    public Integer getLoanNumberEnd() {
        return loanNumberEnd;
    }

    public void setLoanNumberEnd(Integer loanNumberEnd) {
        this.loanNumberEnd = loanNumberEnd;
    }

    public Integer getLoanMoneyEnd() {
        return loanMoneyEnd;
    }

    public void setLoanMoneyEnd(Integer loanMoneyEnd) {
        this.loanMoneyEnd = loanMoneyEnd;
    }
}
