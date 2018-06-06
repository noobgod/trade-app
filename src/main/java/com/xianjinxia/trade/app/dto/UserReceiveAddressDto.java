package com.xianjinxia.trade.app.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.xianjinxia.trade.app.request.BaseRequest;
@ApiModel
public class UserReceiveAddressDto extends BaseRequest {
	
	@ApiModelProperty(name = "receiveProvince",value = "收货人人省份",example = "xx省",required = true,dataType = "String")
    @NotNull(message="receiveProvince couldn't be null")
	@NotEmpty(message="receiveProvince couldn't be empty")
	private String receiveProvince;

	@ApiModelProperty(name = "receiveCity",value = "收货人城市",example = "xx市",required = true,dataType = "String")
    @NotNull(message="receiveCity couldn't be null")
	@NotEmpty(message="receiveCity couldn't be empty")
    private String receiveCity;

	@ApiModelProperty(name = "receiveArea",value = "收货人区县",example = "xx区县",required = true,dataType = "String")
    @NotNull(message="receiveArea couldn't be null")
	@NotEmpty(message="receiveArea couldn't be empty")
    private String receiveArea;

	@ApiModelProperty(name = "receiveAddr",value = "收货人详细地址",example = "xx地址",required = true,dataType = "String")
    @NotNull(message="receiveAddr couldn't be null")
	@NotEmpty(message="receiveAddr couldn't be empty")
    private String receiveAddr;

	@ApiModelProperty(name = "receivePostNo",value = "收货人邮政编码",example = "xx",required = true,dataType = "String")
    @NotNull(message="receivePostNo couldn't be null")
	@NotEmpty(message="receivePostNo couldn't be empty")
    private String receivePostNo;

	@ApiModelProperty(name = "receiveUsername",value = "收货人姓名",example = "xx",required = true,dataType = "String")
    @NotNull(message="receiveUsername couldn't be null")
	@NotEmpty(message="receiveUsername couldn't be empty")
    private String receiveUsername;

	@ApiModelProperty(name = "receiveMobile",value = "收货人手机号",example = "xx",required = true,dataType = "String")
    @NotNull(message="receiveMobile couldn't be null")
	@NotEmpty(message="receiveMobile couldn't be empty")
    private String receiveMobile;
	
	@ApiModelProperty(name = "logisticsCompany",value = "物流公司名称",example = "xx",dataType = "String")
    @NotNull(message="logisticsCompany couldn't be null")
	@NotEmpty(message="logisticsCompany couldn't be empty")
    private String logisticsCompany;

	@ApiModelProperty(name = "logisticsNo",value = "物流编号",example = "xx",dataType = "String")
    @NotNull(message="logisticsNo couldn't be null")
	@NotEmpty(message="logisticsNo couldn't be empty")
    private String logisticsNo;

	@ApiModelProperty(name = "logisticsPhone",value = "物流公司电话",example = "xx",dataType = "String")
    private String logisticsPhone;
    
	public String getReceiveProvince() {
		return receiveProvince;
	}

	public void setReceiveProvince(String receiveProvince) {
		this.receiveProvince = receiveProvince;
	}

	public String getReceiveCity() {
		return receiveCity;
	}

	public void setReceiveCity(String receiveCity) {
		this.receiveCity = receiveCity;
	}

	public String getReceiveArea() {
		return receiveArea;
	}

	public void setReceiveArea(String receiveArea) {
		this.receiveArea = receiveArea;
	}

	public String getReceiveAddr() {
		return receiveAddr;
	}

	public void setReceiveAddr(String receiveAddr) {
		this.receiveAddr = receiveAddr;
	}

	public String getReceivePostNo() {
		return receivePostNo;
	}

	public void setReceivePostNo(String receivePostNo) {
		this.receivePostNo = receivePostNo;
	}

	public String getReceiveUsername() {
		return receiveUsername;
	}

	public void setReceiveUsername(String receiveUsername) {
		this.receiveUsername = receiveUsername;
	}

	public String getReceiveMobile() {
		return receiveMobile;
	}

	public void setReceiveMobile(String receiveMobile) {
		this.receiveMobile = receiveMobile;
	}

	public String getLogisticsCompany() {
		return logisticsCompany;
	}

	public void setLogisticsCompany(String logisticsCompany) {
		this.logisticsCompany = logisticsCompany;
	}

	public String getLogisticsNo() {
		return logisticsNo;
	}

	public void setLogisticsNo(String logisticsNo) {
		this.logisticsNo = logisticsNo;
	}

	public String getLogisticsPhone() {
		return logisticsPhone;
	}

	public void setLogisticsPhone(String logisticsPhone) {
		this.logisticsPhone = logisticsPhone;
	}

}
