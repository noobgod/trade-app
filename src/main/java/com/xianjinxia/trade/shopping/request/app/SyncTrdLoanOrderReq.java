package com.xianjinxia.trade.shopping.request.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

import com.xianjinxia.trade.app.request.BaseRequest;
/**
 * Created by chunliny on 2018/1/21
 */
@ApiModel
public class SyncTrdLoanOrderReq  extends BaseRequest  {
	
	@ApiModelProperty(name = "loanOrderId",value = "订单编号",example = "1",required = true,dataType = "Long")
    @NotNull(message="loanOrderId couldn't be null")
	private Long loanOrderId;

	@ApiModelProperty(name = "status",value = "状态",example = "03",required = true,dataType = "String")
    @NotNull(message="status couldn't be null")
    private String status;

	@ApiModelProperty(name = "productCategory",value = "产品分类",example = "03",required = true,dataType = "Integer")
	
    @NotNull(message="productCategory couldn't be null")
    private Integer productCategory;

    public Long getLoanOrderId() {
		return loanOrderId;
	}

	public void setLoanOrderId(Long loanOrderId) {
		this.loanOrderId = loanOrderId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(Integer productCategory) {
		this.productCategory = productCategory;
	}
    
}
