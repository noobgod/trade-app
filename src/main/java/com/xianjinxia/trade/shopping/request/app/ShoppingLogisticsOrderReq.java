package com.xianjinxia.trade.shopping.request.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.xianjinxia.trade.app.request.BaseRequest;
import com.xianjinxia.trade.shared.idempotent.IdempotentKey;

/**
 * Created by chunliny on 2018/1/30
 */
@ApiModel
public class ShoppingLogisticsOrderReq extends BaseRequest {
	@ApiModelProperty(name = "logisticsOrderId",value = "商品系统订单号",example = "1",required = true,dataType = "Long")
    @NotNull(message="logisticsOrderId couldn't be null")
	@IdempotentKey(order=1)
	private Long logisticsOrderId;
	
	@ApiModelProperty(name = "logisticsCompany",value = "物流公司名称",example = "1",required = true,dataType = "String")
    @NotNull(message="logisticsCompany couldn't be null")
	@NotEmpty(message="logisticsCompany couldn't be empty")
    private String logisticsCompany;
	
	@ApiModelProperty(name = "logisticsNo",value = "物流编号",example = "1",required = true,dataType = "String")
    @NotNull(message="logisticsNo couldn't be null")
	@NotEmpty(message="logisticsNo couldn't be empty")
    private String logisticsNo;
	
	@ApiModelProperty(name = "logisticsPhone",value = "物流公司电话",example = "1",dataType = "String")
    private String logisticsPhone;
	
	@ApiModelProperty(name = "sendProvince",value = "发货人省份",example = "1",dataType = "String")
    private String sendProvince;

	@ApiModelProperty(name = "sendCity",value = "发货人城市",example = "1",dataType = "String")
	private String sendCity;

	@ApiModelProperty(name = "sendArea",value = "发货人区县",example = "1",dataType = "String")
    private String sendArea;

	@ApiModelProperty(name = "sendAddr",value = "发货人地址",example = "1",dataType = "String")
    private String sendAddr;

	@ApiModelProperty(name = "sendPostNo",value = "发货人邮政编码",example = "1",required = true,dataType = "String")
    @NotNull(message="sendPostNo couldn't be null")
	@NotEmpty(message="sendPostNo couldn't be empty")
	private String sendPostNo;

	@ApiModelProperty(name = "sendUsername",value = "发货人姓名",example = "1",required = true,dataType = "String")
    @NotNull(message="sendUsername couldn't be null")
	@NotEmpty(message="sendUsername couldn't be empty")
    private String sendUsername;

	@ApiModelProperty(name = "sendMobile",value = "发货人手机号",example = "1",required = true,dataType = "String")
    @NotNull(message="sendMobile couldn't be null")
	@NotEmpty(message="sendMobile couldn't be empty")
    private String sendMobile;
	
	@Override
	public String toString() {
		return "ShoppingLogisticsOrderReq [logisticsOrderId="
				+ logisticsOrderId + ", logisticsCompany="
				+ logisticsCompany + ", logisticsNo=" + logisticsNo
				+ ", logisticsPhone=" + logisticsPhone + ", sendProvince="
				+ sendProvince + ", sendCity=" + sendCity + ", sendArea="
				+ sendArea + ", sendAddr=" + sendAddr + ", sendPostNo="
				+ sendPostNo + ", sendUsername=" + sendUsername
				+ ", sendMobile=" + sendMobile + "]";
	}
	
	public Long getLogisticsOrderId() {
		return logisticsOrderId;
	}

	public void setLogisticsOrderId(Long logisticsOrderId) {
		this.logisticsOrderId = logisticsOrderId;
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

	public String getSendProvince() {
		return sendProvince;
	}

	public void setSendProvince(String sendProvince) {
		this.sendProvince = sendProvince;
	}

	public String getSendCity() {
		return sendCity;
	}

	public void setSendCity(String sendCity) {
		this.sendCity = sendCity;
	}

	public String getSendArea() {
		return sendArea;
	}

	public void setSendArea(String sendArea) {
		this.sendArea = sendArea;
	}

	public String getSendAddr() {
		return sendAddr;
	}

	public void setSendAddr(String sendAddr) {
		this.sendAddr = sendAddr;
	}

	public String getSendPostNo() {
		return sendPostNo;
	}

	public void setSendPostNo(String sendPostNo) {
		this.sendPostNo = sendPostNo;
	}

	public String getSendUsername() {
		return sendUsername;
	}

	public void setSendUsername(String sendUsername) {
		this.sendUsername = sendUsername;
	}

	public String getSendMobile() {
		return sendMobile;
	}

	public void setSendMobile(String sendMobile) {
		this.sendMobile = sendMobile;
	}

}
