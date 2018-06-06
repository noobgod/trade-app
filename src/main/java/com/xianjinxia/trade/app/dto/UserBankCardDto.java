package com.xianjinxia.trade.app.dto;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import com.xianjinxia.trade.app.request.BaseRequest;

@ApiModel
public class UserBankCardDto extends BaseRequest {

	@ApiModelProperty(name = "userBankCardId",value = "用户银行卡号id",example = "1",required = true,dataType = "Long")
    @NotNull(message="userBankCardId couldn't be null")
	private Long userBankCardId;

	@ApiModelProperty(name = "bankName",value = "银行名称",example = "1",required = true,dataType = "String")
    @NotNull(message="bankName couldn't be null")
	@NotEmpty(message="bankName couldn't be empty")
	private String bankName;

	@ApiModelProperty(name = "bankCardNoLastFour",value = "银行卡号后4位",example = "1",required = true,dataType = "String")
	private String bankCardNoLastFour;

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
}
