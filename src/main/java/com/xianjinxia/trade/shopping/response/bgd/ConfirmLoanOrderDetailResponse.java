package com.xianjinxia.trade.shopping.response.bgd;

import com.xianjinxia.trade.shopping.dto.ShoppingLogisticsOrderDto;
import com.xianjinxia.trade.shopping.dto.ShoppingProductOrderDto;

import java.math.BigDecimal;
import java.util.Date;

public class ConfirmLoanOrderDetailResponse {
	
	private Long id;

    private String productFreezeNo;

    private Integer orderAmount;

    private Integer feeAmount;

    private Integer interestAmount;

    private Integer paymentAmount;

    private Integer repaymentAmount;

    private Integer term;

    private String termUnit;

    private BigDecimal termRate;

    private Long productId;

    private Integer productCategory;

    private String isDepository;

    private Boolean userType;

    private String status;

    private String traceNo;

    private Long userId;

    private String userIdcardType;

    private String userIdcardNo;

    private Long userBankCardId;

    private String bankName;

    private String bankCardNoLastFour;

    private String userPhone;

    private String userName;

    private String source;

    private String terminal;

    private String ukToken;

    private Date createdAt;

	private String specificationJson;
    
    private String specificationDesc;
    
	private ShoppingLogisticsOrderDto shoppingLogisticsOrderDto;
    
    private ShoppingProductOrderDto shoppingProductOrderDto;
    
    public ShoppingLogisticsOrderDto getShoppingLogisticsOrderDto() {
		return shoppingLogisticsOrderDto;
	}

	public void setShoppingLogisticsOrderDto(ShoppingLogisticsOrderDto shoppingLogisticsOrderDto) {
		this.shoppingLogisticsOrderDto = shoppingLogisticsOrderDto;
	}

	public ShoppingProductOrderDto getShoppingProductOrderDto() {
		return shoppingProductOrderDto;
	}

	public void setShoppingProductOrderDto(ShoppingProductOrderDto shoppingProductOrderDto) {
		this.shoppingProductOrderDto = shoppingProductOrderDto;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProductFreezeNo() {
		return productFreezeNo;
	}

	public void setProductFreezeNo(String productFreezeNo) {
		this.productFreezeNo = productFreezeNo;
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

	public Integer getInterestAmount() {
		return interestAmount;
	}

	public void setInterestAmount(Integer interestAmount) {
		this.interestAmount = interestAmount;
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

	public BigDecimal getTermRate() {
		return termRate;
	}

	public void setTermRate(BigDecimal termRate) {
		this.termRate = termRate;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Integer getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(Integer productCategory) {
		this.productCategory = productCategory;
	}

	public String getIsDepository() {
		return isDepository;
	}

	public void setIsDepository(String isDepository) {
		this.isDepository = isDepository;
	}

	public Boolean getUserType() {
		return userType;
	}

	public void setUserType(Boolean userType) {
		this.userType = userType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getUserIdcardType() {
		return userIdcardType;
	}

	public void setUserIdcardType(String userIdcardType) {
		this.userIdcardType = userIdcardType;
	}

	public String getUserIdcardNo() {
		return userIdcardNo;
	}

	public void setUserIdcardNo(String userIdcardNo) {
		this.userIdcardNo = userIdcardNo;
	}

	public Long getUserBankCardId() {
		return userBankCardId;
	}

	public void setUserBankCardId(Long userBankCardId) {
		this.userBankCardId = userBankCardId;
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

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getTerminal() {
		return terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public String getUkToken() {
		return ukToken;
	}

	public void setUkToken(String ukToken) {
		this.ukToken = ukToken;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getSpecificationJson() {
		return specificationJson;
	}

	public void setSpecificationJson(String specificationJson) {
		this.specificationJson = specificationJson;
	}

	public String getSpecificationDesc() {
		return specificationDesc;
	}

	public void setSpecificationDesc(String specificationDesc) {
		this.specificationDesc = specificationDesc;
	}
}
