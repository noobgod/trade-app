package com.xianjinxia.trade.app.request;



/**
 * 获取银行卡请求参数
 * 
 * @author liuzhifang
 *
 *         2017年9月6日
 */
public class BankInfoReq {

    private Long userId;

    private Long userBankCardId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUserBankCardId() {
        return userBankCardId;
    }

    public void setUserBankCardId(Long userBankCardId) {
        this.userBankCardId = userBankCardId;
    }

    @Override
    public String toString() {
        return "BankInfoReq [userId=" + userId + ", userBankCardId=" + userBankCardId + "]";
    }

}
