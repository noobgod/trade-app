package com.xianjinxia.trade.platform.dto;

import java.math.BigDecimal;
import java.util.Date;

public class LoanOrderDetailDto {
	
	private BigDecimal orderAmount;
	
	private BigDecimal paymentAmount;
	
	private BigDecimal repaymentAmount;
	
	private BigDecimal feeAmount;
	
	private BigDecimal interestAmount;
	
	private BigDecimal totalFeeAmount;
	
	private Integer term;
	
	private String termUnit;
	
	private Date loanTime;
	
	private String bankName;
	
	private String bankCardNoLastFour;
	
	private String status;
	
	private String serviceCompany;
	
	private String productCode;
	
	 private Long userBankCardId;

	

	public BigDecimal getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(BigDecimal orderAmount) {
		this.orderAmount = orderAmount;
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

	public BigDecimal getFeeAmount() {
		return feeAmount;
	}

	public void setFeeAmount(BigDecimal feeAmount) {
		this.feeAmount = feeAmount;
	}
	
	public BigDecimal getInterestAmount() {
		return interestAmount;
	}

	public void setInterestAmount(BigDecimal interestAmount) {
		this.interestAmount = interestAmount;
	}

	public BigDecimal getTotalFeeAmount() {
		return totalFeeAmount;
	}

	public void setTotalFeeAmount(BigDecimal totalFeeAmount) {
		this.totalFeeAmount = totalFeeAmount;
	}

	public Integer getTerm() {
		return term;
	}

	public void setTerm(Integer term) {
		this.term = term;
	}

	public String getTermUnit() {
		return termUnit;
	}

	public void setTermUnit(String termUnit) {
		this.termUnit = termUnit;
	}


	public Date getLoanTime() {
		return loanTime;
	}

	public void setLoanTime(Date loanTime) {
		this.loanTime = loanTime;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}


	public String getBankCardNoLastFour() {
		return bankCardNoLastFour;
	}

	public void setBankCardNoLastFour(String bankCardNoLastFour) {
		this.bankCardNoLastFour = bankCardNoLastFour;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getServiceCompany() {
		return serviceCompany;
	}

	public void setServiceCompany(String serviceCompany) {
		this.serviceCompany = serviceCompany;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public Long getUserBankCardId() {
		return userBankCardId;
	}

	public void setUserBankCardId(Long userBankCardId) {
		this.userBankCardId = userBankCardId;
	}
	

}
