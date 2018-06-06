package com.xianjinxia.trade.app.request;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by Administrator on 2017/9/17 0017.
 */
public class ConfirmLoanReq extends BaseRequest {

    @NotNull(message="userId couldn't be null")
    private Long  userId;

    @NotNull(message="orderAmount couldn't be null")
    private BigDecimal  orderAmount;

    @NotNull(message="feeAmount couldn't be null")
    private BigDecimal  feeAmount;

    @NotNull(message="paymentAmount couldn't be null")
    private BigDecimal  paymentAmount;

    @NotNull(message="repaymentAmount couldn't be null")
    private BigDecimal  repaymentAmount;

    @NotNull(message="interestAmount couldn't be null")
    private BigDecimal  interestAmount;

    @NotNull(message="periods couldn't be null")
    private Integer  periods;

    @NotNull(message="productId couldn't be null")
    private Long  productId;

    @NotNull(message="userBankCardId couldn't be null")
    private Long  userBankCardId;

    @NotNull(message="productCategory couldn't be null")
    private Integer productCategory;

    @NotNull(message="bankName couldn't be null")
    private String bankName;

    @NotNull(message="lastFourBankCardNo couldn't be null")
    private String lastFourBankCardNo;

    private String	remark;

    @NotNull(message="userType couldn't be null")
    private Boolean userType;

    @NotNull(message="userPhone couldn't be null")
    private String	userPhone;

    @NotNull(message="userName couldn't be null")
    private String  userName;

    @NotNull(message="source couldn't be null")
    private String  source;

    @NotNull(message="terminal couldn't be null")
    private String  terminal;

    @NotNull(message="traceNo couldn't be null")
    private String traceNo ;

    @NotNull(message="quietPeriod couldn't be null")
    private String quietPeriod;

    private String loanUsage;

    private String isDepository;

    private String termUnit;//期数单位

    private BigDecimal termRate;//期利率

    /** 商户号 */
    private String merchantNo;

    /** 是否购买头卡 */
    private String buyCard;

    /**
     * 提速卡id
     */
    private String speedCardId;

    public String getBuyCard() {
        return buyCard;
    }

    public void setBuyCard(String buyCard) {
        this.buyCard = buyCard;
    }


    public BigDecimal getInterestAmount() {
        return interestAmount;
    }

    public void setInterestAmount(BigDecimal interestAmount) {
        this.interestAmount = interestAmount;
    }

    public String getLoanUsage() {
        return loanUsage;
    }

    public void setLoanUsage(String loanUsage) {
        this.loanUsage = loanUsage;
    }

    public String getQuietPeriod() {
        return quietPeriod;
    }

    public void setQuietPeriod(String quietPeriod) {
        this.quietPeriod = quietPeriod;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTraceNo() {
        return traceNo;
    }

    public void setTraceNo(String traceNo) {
        this.traceNo = traceNo;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }


    public BigDecimal getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(BigDecimal orderAmount) {
		this.orderAmount = orderAmount;
	}

	public BigDecimal getFeeAmount() {
		return feeAmount;
	}

	public void setFeeAmount(BigDecimal feeAmount) {
		this.feeAmount = feeAmount;
	}

	public BigDecimal getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(BigDecimal paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public BigDecimal getRepaymentAmount() {
		return repaymentAmount;
	}

	public void setRepaymentAmount(BigDecimal repaymentAmount) {
		this.repaymentAmount = repaymentAmount;
	}

	public Integer getPeriods() {
        return periods;
    }

    public void setPeriods(Integer periods) {
        this.periods = periods;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getUserBankCardId() {
        return userBankCardId;
    }

    public void setUserBankCardId(Long userBankCardId) {
        this.userBankCardId = userBankCardId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Boolean getUserType() {
        return userType;
    }

    public void setUserType(Boolean userType) {
        this.userType = userType;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTerminal() {
        return terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }


    public Integer getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(Integer productCategory) {
		this.productCategory = productCategory;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getLastFourBankCardNo() {
		return lastFourBankCardNo;
	}

	public void setLastFourBankCardNo(String lastFourBankCardNo) {
		this.lastFourBankCardNo = lastFourBankCardNo;
	}

    public String getIsDepository() {
        return isDepository;
    }

    public void setIsDepository(String isDepository) {
        this.isDepository = isDepository;
    }

    public String getTermUnit() {
        return termUnit;
    }

    public void setTermUnit(String termUnit) {
        this.termUnit = termUnit;
    }

    public BigDecimal getTermRate() {
        return termRate;
    }

    public void setTermRate(BigDecimal termRate) {
        this.termRate = termRate;
    }


    public String getMerchantNo() {
        return merchantNo;
    }

    public void setMerchantNo(String merchantNo) {
        this.merchantNo = merchantNo;
    }

    public String getSpeedCardId() {
        return speedCardId;
    }

    public void setSpeedCardId(String speedCardId) {
        this.speedCardId = speedCardId;
    }

    @Override
    public String toString() {
        return "ConfirmLoanReq{" +
                "userId=" + userId +
                ", orderAmount=" + orderAmount +
                ", feeAmount=" + feeAmount +
                ", paymentAmount=" + paymentAmount +
                ", repaymentAmount=" + repaymentAmount +
                ", interestAmount=" + interestAmount +
                ", periods=" + periods +
                ", productId=" + productId +
                ", userBankCardId=" + userBankCardId +
                ", productCategory=" + productCategory +
                ", bankName='" + bankName + '\'' +
                ", lastFourBankCardNo='" + lastFourBankCardNo + '\'' +
                ", remark='" + remark + '\'' +
                ", userType=" + userType +
                ", userPhone='" + userPhone + '\'' +
                ", userName='" + userName + '\'' +
                ", source='" + source + '\'' +
                ", terminal='" + terminal + '\'' +
                ", traceNo='" + traceNo + '\'' +
                ", quietPeriod='" + quietPeriod + '\'' +
                ", loanUsage='" + loanUsage + '\'' +
                ", isDepository='" + isDepository + '\'' +
                ", termUnit='" + termUnit + '\'' +
                ", termRate=" + termRate +
                ", merchantNo='" + merchantNo + '\'' +
                ", buyCard='" + buyCard + '\'' +
                ", speedCardId='" + speedCardId + '\'' +
                '}';
    }
}
