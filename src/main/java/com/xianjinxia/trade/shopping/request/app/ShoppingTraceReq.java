package com.xianjinxia.trade.shopping.request.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.xianjinxia.trade.app.request.BaseRequest;
/**
 * Created by chunliny on 2018/1/19
 */
@ApiModel
public class ShoppingTraceReq extends BaseRequest  {

	@ApiModelProperty(name = "userId",value = "用户编号",example = "1",required = true,dataType = "Long")
    @NotNull(message="userId couldn't be null")
	private Long userId;
	
	@ApiModelProperty(name = "productId",value = "产品编号",example = "1",required = true,dataType = "String")
    @NotNull(message="productId couldn't be null")
	@NotEmpty(message="productId couldn't be empty")
	private String productId;

	@ApiModelProperty(name = "productName",value = "产品名称",example = "1",required = true,dataType = "String")
    @NotNull(message="productName couldn't be null")
	@NotEmpty(message="productName couldn't be empty")
	private String productName;

	@ApiModelProperty(name = "productCategory",value = "产品类型",example = "1",required = true,dataType = "String")
    @NotNull(message="productCategory couldn't be null")
	@NotEmpty(message="productCategory couldn't be empty")
	private String productCategory;
	
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}
 
}
