package com.xianjinxia.trade.shared.domain;

import java.math.BigDecimal;
import java.util.Date;
/**
 * 借款订单表
 * @author liuzhifang
 *
	2017年9月19日
 */
public class LoanOrder {
	/**
	 * 表id
	 */
    private Long id;
    /**
     * 用户id
     */
    private Long userId;

    /**
     * 业务流水号
     */
    private String bizSeqNo;

    private String ukToken;

    /**
     * 订单类型
     */
    private String orderType;

    /**
     * 订单金额，单位为分
     */
    private Integer orderAmount;

    /**
     * 借款服务费
     */
    private Integer feeAmount;

    /**
     * 实际到账金额，单位为分
     */
    private Integer paymentAmount;

    /**
     * 应还金额
     */
    private Integer repaymentAmount;

    /**
     * 利息金额
     */
    private Integer interestAmount;

    /**
     * 总期数
     */
    private Integer periods;

    /**
     * 产品id
     */
    private Long productId;
    
    /**
     * 产品类型  (1:小额   2:大额)
     */
    private Integer productCategory;

    /**
     * 用户银行卡号id
     */
    private Long userBankCardId;
    
    /**
     * 银行名称
     */
    private String bankName;
    
    /**
     * 银行卡号后四位
     */
    private String lastFourBankCardNo;

    /**
     * 备注
     */
    private String remark;

    /**
     * 是否是老用户：0、新用户；1；老用户
     */
    private Boolean userType;

    /**
     * 用户手机号
     */
    private String userPhone;

    /**
     * 真实姓名
     */
    private String userName;

    /**
     * 状态：1:审核中 ; 2:审核失败; 3:放款中 ;4:放款失败;5:放款成功；
     */
    private String status;

    /**
     * 订单来源：01、现金侠 02、融360
     */
    private String source;

    /**
     * 终端类型 01 ios ,02 android, 03 h5
     */
    private String terminal;

    /**
     * 创建时间
     */
    private Date createdTime;

    /**
     * 创建人
     */
    private String createdUser;

    /**
     * 更新时间
     */
    private Date updatedTime;

    /**
     * 数据有效性 0 无效  1 有效
     */
    private Boolean dataValid;

    /**
     * 追踪号
     */
    private String traceNo;
    /**
     * 用途场景
     */
    private String loanUsage ;

    private Date loanTime;

    /**
     * 审核失败时间
     */
    private Date reviewFailTime;

    private String isDepository;//是否存管  "1"表示是 "0"表示否

    private String termUnit;//期数单位

    private BigDecimal termRate;//期利率

    private String merchantNo;//商户号


    private String speedCardId;//提速卡号


    public Date getReviewFailTime() {
        return reviewFailTime;
    }

    public void setReviewFailTime(Date reviewFailTime) {
        this.reviewFailTime = reviewFailTime;
    }

    public Date getLoanTime() {
        return loanTime;
    }

    public void setLoanTime(Date loanTime) {
        this.loanTime = loanTime;
    }

    public Integer getInterestAmount() {
        return interestAmount;
    }

    public void setInterestAmount(Integer interestAmount) {
        this.interestAmount = interestAmount;
    }

    public String getLoanUsage() {
        return loanUsage;
    }

    public void setLoanUsage(String loanUsage) {
        this.loanUsage = loanUsage;
    }

    public String getUkToken() {
        return ukToken;
    }

    public void setUkToken(String ukToken) {
        this.ukToken = ukToken;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getBizSeqNo() {
        return bizSeqNo;
    }

    public void setBizSeqNo(String bizSeqNo) {
        this.bizSeqNo = bizSeqNo == null ? null : bizSeqNo.trim();
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType == null ? null : orderType.trim();
    }

    public Integer getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(Integer orderAmount) {
        this.orderAmount = orderAmount;
    }

    public Integer getFeeAmount() {
        return feeAmount;
    }

    public void setFeeAmount(Integer feeAmount) {
        this.feeAmount = feeAmount;
    }

    public Integer getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(Integer paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public Integer getRepaymentAmount() {
        return repaymentAmount;
    }

    public void setRepaymentAmount(Integer repaymentAmount) {
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
        this.remark = remark == null ? null : remark.trim();
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
        this.userPhone = userPhone == null ? null : userPhone.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source == null ? null : source.trim();
    }

    public String getTerminal() {
        return terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal == null ? null : terminal.trim();
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(String createdUser) {
        this.createdUser = createdUser == null ? null : createdUser.trim();
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Boolean getDataValid() {
        return dataValid;
    }

    public void setDataValid(Boolean dataValid) {
        this.dataValid = dataValid;
    }

    public String getTraceNo() {
        return traceNo;
    }

    public void setTraceNo(String traceNo) {
        this.traceNo = traceNo == null ? null : traceNo.trim();
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
        return "LoanOrder{" +
                "id=" + id +
                ", userId=" + userId +
                ", bizSeqNo='" + bizSeqNo + '\'' +
                ", ukToken='" + ukToken + '\'' +
                ", orderType='" + orderType + '\'' +
                ", orderAmount=" + orderAmount +
                ", feeAmount=" + feeAmount +
                ", paymentAmount=" + paymentAmount +
                ", repaymentAmount=" + repaymentAmount +
                ", interestAmount=" + interestAmount +
                ", periods=" + periods +
                ", productId=" + productId +
                ", productCategory=" + productCategory +
                ", userBankCardId=" + userBankCardId +
                ", bankName='" + bankName + '\'' +
                ", lastFourBankCardNo='" + lastFourBankCardNo + '\'' +
                ", remark='" + remark + '\'' +
                ", userType=" + userType +
                ", userPhone='" + userPhone + '\'' +
                ", userName='" + userName + '\'' +
                ", status='" + status + '\'' +
                ", source='" + source + '\'' +
                ", terminal='" + terminal + '\'' +
                ", createdTime=" + createdTime +
                ", createdUser='" + createdUser + '\'' +
                ", updatedTime=" + updatedTime +
                ", dataValid=" + dataValid +
                ", traceNo='" + traceNo + '\'' +
                ", loanUsage='" + loanUsage + '\'' +
                ", loanTime=" + loanTime +
                ", reviewFailTime=" + reviewFailTime +
                ", isDepository='" + isDepository + '\'' +
                ", termUnit='" + termUnit + '\'' +
                ", termRate=" + termRate +
                ", merchantNo='" + merchantNo + '\'' +
                ", speedCardId='" + speedCardId + '\'' +
                '}';
    }
}