package com.xianjinxia.trade.app.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

/**
 * 还款详情请求参数
 * @author hym
 * 2017年9月8日
 */
@ApiModel
public class RepaymentDetailReq extends BaseRequest {

	@NotNull(message="loanId couldn't be null")
	@ApiModelProperty(example = "64",value = "借款单Id",dataType = "long")
    private long loanId;

	private  Long userId;

	/**
	 * 商户号
	 */
	@ApiModelProperty(name = "merchantNo",value = "商户号",example = "jsxjx",required = false,dataType = "String")
	private String merchantNo;
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public long getLoanId() {
		return loanId;
	}

	public void setLoanId(long loanId) {
		this.loanId = loanId;
	}

	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}

	@Override
	public String toString() {
		return "RepaymentDetailReq{" +
				"loanId=" + loanId +
				", userId=" + userId +
				", merchantNo='" + merchantNo + '\'' +
				'}';
	}
}
