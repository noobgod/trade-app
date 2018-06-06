package com.xianjinxia.trade.app.request;



/**
 * 推送支付中心放款参数
 * 
 * @author liuzhifang
 *
 *         2017年9月5日
 */
public class LoanParamsReq {

    /**
     * 业务id
     */
    private String bizId;
    /**
     * 业务类型
     */
    private String bizType;
    /**
     * 银行行号
     */
    private String bankCode;
    /***
     * 银行卡号
     */
    private String bankCardNo;
    /**
     * 用户名
     */
    private String cardHolder;
    /**
     * 银行名称
     */
    private String bankName;
    /**
     * 持卡人电话
     */
    private String phoneNo;
    /**
     * 放款金额（分）
     */
    private Integer loanAmount;
    /**
     * 放款渠道路由渠道标识
     */
    private String routeStrategy;
    /**
     * 来源
     */
    private String requestSource;
    /**
     * 注册用户id
     */
    private String loanUser;
    /**
     * 签名
     */
    private String sign;
    /**
     * 备注
     */
    private String remark;

    /**
     * 扩展字段
     */
    private String extData;

    public String getBizId() {
        return bizId;
    }

    public void setBizId(String bizId) {
        this.bizId = bizId;
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBankCardNo() {
        return bankCardNo;
    }

    public void setBankCardNo(String bankCardNo) {
        this.bankCardNo = bankCardNo;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public Integer getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(Integer loanAmount) {
        this.loanAmount = loanAmount;
    }

    public String getRouteStrategy() {
        return routeStrategy;
    }

    public void setRouteStrategy(String routeStrategy) {
        this.routeStrategy = routeStrategy;
    }

    public String getRequestSource() {
        return requestSource;
    }

    public void setRequestSource(String requestSource) {
        this.requestSource = requestSource;
    }


    public String getLoanUser() {
        return loanUser;
    }

    public void setLoanUser(String loanUser) {
        this.loanUser = loanUser;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getExtData() {
        return extData;
    }

    public void setExtData(String extData) {
        this.extData = extData;
    }
}
