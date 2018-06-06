package com.xianjinxia.trade.shopping.request.app;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.xianjinxia.trade.app.dto.ShoppingProductDto;
import com.xianjinxia.trade.app.dto.UserBankCardDto;
import com.xianjinxia.trade.app.dto.UserReceiveAddressDto;
import com.xianjinxia.trade.app.request.BaseRequest;

/**
 * Created by chunliny on 2018/1/16
 */
@ApiModel
public class ShoppingLoanOrderReq extends BaseRequest {
	
	@ApiModelProperty(name = "userId",value = "用户编号",example = "1",required = true,dataType = "Long")
    @NotNull(message="userId couldn't be null")
	private Long userId;
	
	@ApiModelProperty(name = "userType",value = "用户类型(0 新用户,1 老用户)",example = "0",required = true,dataType = "Integer")
    @NotNull(message="userType couldn't be null")
	private Integer userType;

	@ApiModelProperty(name = "userPhone",value = "用户手机号",example = "18911111111",required = true,dataType = "String")
    @NotNull(message="userPhone couldn't be null")
	@NotEmpty(message="userPhone couldn't be empty")
	private String userPhone;

	@ApiModelProperty(name = "userName",value = "真实姓名",example = "1",required = true,dataType = "String")
    @NotNull(message="userName couldn't be null")
	@NotEmpty(message="userName couldn't be empty")
	private String userName;
	
	@ApiModelProperty(name = "userIdcardType",value = "证件类型",example = "1",required = true,dataType = "String")
    private String userIdcardType;

	@ApiModelProperty(name = "userIdcardNo",value = "证件号",example = "1",required = true,dataType = "String")
    private String userIdcardNo;
    
	@ApiModelProperty(name = "orderAmount",value = "订单金额,单位为分",example = "1",required = true,dataType = "Integer")
    @NotNull(message="orderAmount couldn't be null")
	private Integer orderAmount;

	@ApiModelProperty(name = "feeAmount",value = "订单服务费,单位为分",example = "1",required = true,dataType = "Integer")
    @NotNull(message="feeAmount couldn't be null")
	private Integer feeAmount;

	@ApiModelProperty(name = "interestAmount",value = "订单利息",example = "1",required = true,dataType = "Integer")
    @NotNull(message="interestAmount couldn't be null")
	private Integer interestAmount;
	
	@ApiModelProperty(name = "paymentAmount",value = "实际到账金额,单位为分",example = "1",required = true,dataType = "Integer")
	private Integer paymentAmount;

	@ApiModelProperty(name = "repaymentAmount",value = "还款金额,单位为分",example = "1",required = true,dataType = "Integer")
    @NotNull(message="repaymentAmount couldn't be null")
	private Integer repaymentAmount;

	@ApiModelProperty(name = "term",value = "期数",example = "1",required = true,dataType = "Integer")
    @NotNull(message="term couldn't be null")
	private Integer term;

	@ApiModelProperty(name = "termUnit",value = "期数单位",example = "1",required = true,dataType = "String")
    @NotNull(message="termUnit couldn't be null")
	private String termUnit;
	
	@ApiModelProperty(name = "termRate",value = "期利率",example = "1",required = true,dataType = "BigDecimal")
    @NotNull(message="termRate couldn't be null")
    private BigDecimal termRate;
	
	@ApiModelProperty(name = "productId",value = "产品id",example = "1",required = true,dataType = "Long")
    @NotNull(message="productId couldn't be null")
	private Long productId;
	
	@ApiModelProperty(name = "productCategory",value = "期数单位",example = "1",required = true,dataType = "Integer")
    @NotNull(message="productCategory couldn't be null")
	private Integer productCategory;
	
	@ApiModelProperty(name = "isDepository",value = "是否存管(1表示是 2表示否)",example = "2",required = true,dataType = "String")
    @NotNull(message="isDepository couldn't be null")
	@NotEmpty(message="isDepository couldn't be empty")
    private String isDepository;
	
	@ApiModelProperty(name = "traceNo",value = "订单系统追踪号",example = "1",required = true,dataType = "String")
    @NotNull(message="traceNo couldn't be null")
	@NotEmpty(message="traceNo couldn't be empty")
	private String traceNo;

	@ApiModelProperty(name = "source",value = "订单来源",example = "1",required = true,dataType = "String")
    @NotNull(message="source couldn't be null")
	@NotEmpty(message="source couldn't be empty")
	private String source;

	@ApiModelProperty(name = "terminal",value = "终端类型",example = "1",required = true,dataType = "String")
    @NotNull(message="terminal couldn't be null")
	@NotEmpty(message="terminal couldn't be empty")
	private String terminal;
	
	@NotNull(message = "specificationJson id couldn't be null")
    @ApiModelProperty(name = "specificationJson",value = "商品规格json",example = "1",required = true,dataType = "String")
	private String specificationJson;
	
	@NotNull(message = "specificationDesc id couldn't be null")
    @ApiModelProperty(name = "specificationDesc",value = "商品规格描述",example = "1",required = true,dataType = "String")
	private String specificationDesc;

	@ApiModelProperty(name = "quietPeriod",value = "冷却周期天数",example = "20",required = true,dataType = "Integer")
	@NotNull(message="quietPeriod couldn't be null")
	private Integer quietPeriod;

	private ShoppingProductDto shoppingProductDto;
	private UserBankCardDto userBankCardDto;
	private UserReceiveAddressDto userReceivwAddressDto;

	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Integer getUserType() {
		return userType;
	}
	public void setUserType(Integer userType) {
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
	public String getTraceNo() {
		return traceNo;
	}
	public void setTraceNo(String traceNo) {
		this.traceNo = traceNo;
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
	public ShoppingProductDto getShoppingProductDto() {
		return shoppingProductDto;
	}
	public void setShoppingProductDto(ShoppingProductDto shoppingProductDto) {
		this.shoppingProductDto = shoppingProductDto;
	}
	public UserBankCardDto getUserBankCardDto() {
		return userBankCardDto;
	}
	public void setUserBankCardDto(UserBankCardDto userBankCardDto) {
		this.userBankCardDto = userBankCardDto;
	}
	public UserReceiveAddressDto getUserReceivwAddressDto() {
		return userReceivwAddressDto;
	}
	public void setUserReceivwAddressDto(UserReceiveAddressDto userReceivwAddressDto) {
		this.userReceivwAddressDto = userReceivwAddressDto;
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

	public Integer getQuietPeriod() {
		return quietPeriod;
	}

	public void setQuietPeriod(Integer quietPeriod) {
		this.quietPeriod = quietPeriod;
	}
}
