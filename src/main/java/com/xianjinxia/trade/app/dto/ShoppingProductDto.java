package com.xianjinxia.trade.app.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.xianjinxia.trade.app.request.BaseRequest;

@ApiModel
public class ShoppingProductDto extends BaseRequest {
	
	@ApiModelProperty(name = "productId",value = "产品编号",example = "1",required = true,dataType = "String")
    @NotNull(message="productId couldn't be null")
	@NotEmpty(message="productId couldn't be empty")
	private String productId;

	@ApiModelProperty(name = "productPrice",value = "产品价格",example = "1",required = true,dataType = "Integer")
    @NotNull(message="productPrice couldn't be null")
	private Integer productPrice;

	@ApiModelProperty(name = "productName",value = "产品名称",example = "1",required = true,dataType = "String")
    @NotNull(message="productName couldn't be null")
	@NotEmpty(message="productName couldn't be empty")
	private String productName;

	@ApiModelProperty(name = "productCategory",value = "产品类型",example = "1",required = true,dataType = "String")
    @NotNull(message="productCategory couldn't be null")
	@NotEmpty(message="productCategory couldn't be empty")
	private String productCategory;

	@ApiModelProperty(name = "buyNumber",value = "购买数量",example = "1",required = true,dataType = "Integer")
	@NotNull(message="buyNumber couldn't be null")
	private Integer buyNumber;

	@ApiModelProperty(name = "thirdProductId",value = "第三方商品id,商城二期后为必须参数",example = "jd0001",required = true,dataType = "String")
	private String thirdProductId;

    @ApiModelProperty(name = "kindId",value = "商品种类",example = "1：一般商品 2：虚拟商品",required = true,dataType = "Integer")
    @NotNull(message="kindId couldn't be null")
	private Integer kindId;

	@ApiModelProperty(name = "productUnitPrice",value = "商品单价",example = "50",required = true,dataType = "Integer")
    private Integer productUnitPrice;
 
	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public Integer getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(Integer productPrice) {
		this.productPrice = productPrice;
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

	public Integer getBuyNumber() {
		return buyNumber;
	}

	public void setBuyNumber(Integer buyNumber) {
		this.buyNumber = buyNumber;
	}

	public String getThirdProductId() {
		return thirdProductId;
	}

	public void setThirdProductId(String thirdProductId) {
		this.thirdProductId = thirdProductId;
	}

    public Integer getKindId() {
        return kindId;
    }

    public void setKindId(Integer kindId) {
        this.kindId = kindId;
    }

	public Integer getProductUnitPrice() {
		return productUnitPrice;
	}

	public void setProductUnitPrice(Integer productUnitPrice) {
		this.productUnitPrice = productUnitPrice;
	}
}
