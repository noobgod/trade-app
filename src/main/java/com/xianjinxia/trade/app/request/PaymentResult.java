package com.xianjinxia.trade.app.request;

import com.xianjinxia.trade.app.dto.LoanCaptionInfo;
import com.xianjinxia.trade.shared.idempotent.IdempotentKey;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 接收支付中心 放款结果的对象
 * Created by MJH on 2017/9/4.
 */
public class PaymentResult extends BaseRequest implements Serializable {


    public enum CodeEnum{

        SUCCESS("200"),
        FAIL("4000");

        CodeEnum(String code){
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

    /**
	 * 
	 */
	private static final long serialVersionUID = -130602646147001273L;

	/**
     * trade系统的paymentOrder表的流水号
     */
    @NotNull(message="paymentOrderSeqNo can't be null")
    @IdempotentKey(order=1)
    private String paymentOrderSeqNo;

    /**
     * 交易类型
     */
    @NotNull(message="tradeType can't be null")
    private String tradeType;

    /**
     * 来源ID
     */
    @NotNull(message="sourceId can't be null")
    private String sourceId;

    /**
     * 支付中心的流水号
     */
    private String paymentOrderNo;

    /**
     * 响应状态码
     */
    @NotNull(message="code can't be null")
    private String code;

    /**
     * 响应的message
     */
    private String msg;

    /**
     * 银行交易时间
     */
    private Date bankPayTime;

    /**
     * 支付中心更新时间
     */
    private Date paymentUpdateTime;

    private LoanCaptionInfo loanCaptionInfo;

    public LoanCaptionInfo getLoanCaptionInfo() {
        return loanCaptionInfo;
    }

    public void setLoanCaptionInfo(LoanCaptionInfo loanCaptionInfo) {
        this.loanCaptionInfo = loanCaptionInfo;
    }

    public String getPaymentOrderSeqNo() {
        return paymentOrderSeqNo;
    }

    public void setPaymentOrderSeqNo(String paymentOrderSeqNo) {
        this.paymentOrderSeqNo = paymentOrderSeqNo;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getPaymentOrderNo() {
        return paymentOrderNo;
    }

    public void setPaymentOrderNo(String paymentOrderNo) {
        this.paymentOrderNo = paymentOrderNo;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Date getBankPayTime() {
        return bankPayTime;
    }

    public void setBankPayTime(Date bankPayTime) {
        this.bankPayTime = bankPayTime;
    }

    public Date getPaymentUpdateTime() {
        return paymentUpdateTime;
    }

    public void setPaymentUpdateTime(Date paymentUpdateTime) {
        this.paymentUpdateTime = paymentUpdateTime;
    }

    @Override
    public String toString() {
        return "PaymentResult{" +
                "paymentOrderSeqNo='" + paymentOrderSeqNo + '\'' +
                ", tradeType='" + tradeType + '\'' +
                ", sourceId='" + sourceId + '\'' +
                ", paymentOrderNo='" + paymentOrderNo + '\'' +
                ", code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", bankPayTime=" + bankPayTime +
                ", paymentUpdateTime=" + paymentUpdateTime +
                ", loanCaptionInfo=" + loanCaptionInfo +
                '}';
    }
}




