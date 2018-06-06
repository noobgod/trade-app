package com.xianjinxia.trade.shopping.request.app;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

import com.xianjinxia.trade.app.request.BaseRequest;

public class UserReceiveAddressDetailReq extends BaseRequest {
	
	@ApiModelProperty(name = "id",value = "地址编号",example = "1",required = true,dataType = "Long")
    @NotNull(message="id couldn't be null")
	private Long id;

	@ApiModelProperty(name = "userId",value = "用户编号",example = "1",required = true,dataType = "Long")
    @NotNull(message="userId couldn't be null")
	private Long userId;

	@ApiModelProperty(name = "province",value = "省份",example = "1",required = true,dataType = "String")
    @NotNull(message="province couldn't be null")
    private String province;

	@ApiModelProperty(name = "city",value = "城市",example = "1",required = true,dataType = "String")
    @NotNull(message="city couldn't be null")
    private String city;

	@ApiModelProperty(name = "area",value = "区县",example = "1",required = true,dataType = "String")
    @NotNull(message="area couldn't be null")
	private String area;

	@ApiModelProperty(name = "detailAddr",value = "详细地址",example = "1",required = true,dataType = "String")
    @NotNull(message="detailAddr couldn't be null")
    private String detailAddr;

	@ApiModelProperty(name = "area",value = "邮政编码",example = "",dataType = "String")
    private String postNo;

	@ApiModelProperty(name = "username",value = "用户名",example = "1",required = true,dataType = "String")
    @NotNull(message="username couldn't be null")
    private String username;

	@ApiModelProperty(name = "mobile",value = "手机号",example = "1",required = true,dataType = "String")
    @NotNull(message="mobile couldn't be null")
    private String mobile;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
    
	public UserReceiveAddressDetailReq() {
	}
    
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getDetailAddr() {
		return detailAddr;
	}

	public void setDetailAddr(String detailAddr) {
		this.detailAddr = detailAddr;
	}

	public String getPostNo() {
		return postNo;
	}

	public void setPostNo(String postNo) {
		this.postNo = postNo;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

}
