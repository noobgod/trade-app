package com.xianjinxia.trade.shared.domain;

import java.util.Date;

public class TrdShoppingLogisticsOrder {
    private Long id;

    private Long shoppingLoanOrderId;

    private String receiveProvince;

    private String receiveCity;

    private String receiveArea;

	private String receiveAddr;

    private String receivePostNo;

    private String receiveUsername;

    private String receiveMobile;

    private String logisticsCompany;

    private String logisticsNo;

    private String logisticsPhone;

    private Integer logisticsPrice;

    private String status;

    private Date createdAt;

    private String createdUser;

    private Date updatedAt;

    private String updatedUser;

    private Boolean dataValid;
    
    private String sendProvince;

	private String sendCity;

    private String sendArea;

    private String sendAddr;

    private String sendPostNo;

    private String sendUsername;

    private String sendMobile;

    private String orderType;

    private Date sendTime;

    private String venderId;
    private String venderName;
    
    public TrdShoppingLogisticsOrder(){
    	
    }
    
    public TrdShoppingLogisticsOrder(Long id, Long shoppingLoanOrderId,
			String receiveProvince, String receiveCity, String receiveArea,
			String receiveAddr, String receivePostNo, String receiveUsername,
			String receiveMobile, String logisticsCompany, String logisticsNo,
			String logisticsPhone, String status, Date createdAt,
			String createdUser, Date updatedAt, String updatedUser,
			Boolean dataValid, String sendProvince, String sendCity,
			String sendArea, String sendAddr, String sendPostNo,
			String sendUsername, String sendMobile, String orderType) {
		super();
		this.id = id;
		this.shoppingLoanOrderId = shoppingLoanOrderId;
		this.receiveProvince = receiveProvince;
		this.receiveCity = receiveCity;
		this.receiveArea = receiveArea;
		this.receiveAddr = receiveAddr;
		this.receivePostNo = receivePostNo;
		this.receiveUsername = receiveUsername;
		this.receiveMobile = receiveMobile;
		this.logisticsCompany = logisticsCompany;
		this.logisticsNo = logisticsNo;
		this.logisticsPhone = logisticsPhone;
		this.status = status;
		this.createdAt = createdAt;
		this.createdUser = createdUser;
		this.updatedAt = updatedAt;
		this.updatedUser = updatedUser;
		this.dataValid = dataValid;
		this.sendProvince = sendProvince;
		this.sendCity = sendCity;
		this.sendArea = sendArea;
		this.sendAddr = sendAddr;
		this.sendPostNo = sendPostNo;
		this.sendUsername = sendUsername;
		this.sendMobile = sendMobile;
		this.orderType = orderType;
	}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getShoppingLoanOrderId() {
        return shoppingLoanOrderId;
    }

    public void setShoppingLoanOrderId(Long shoppingLoanOrderId) {
        this.shoppingLoanOrderId = shoppingLoanOrderId;
    }

    public String getReceiveProvince() {
        return receiveProvince;
    }

    public void setReceiveProvince(String receiveProvince) {
        this.receiveProvince = receiveProvince == null ? null : receiveProvince.trim();
    }

    public String getReceiveCity() {
        return receiveCity;
    }

    public void setReceiveCity(String receiveCity) {
        this.receiveCity = receiveCity == null ? null : receiveCity.trim();
    }

    public String getReceiveArea() {
        return receiveArea;
    }

    public void setReceiveArea(String receiveArea) {
        this.receiveArea = receiveArea == null ? null : receiveArea.trim();
    }

    public String getReceiveAddr() {
        return receiveAddr;
    }

    public void setReceiveAddr(String receiveAddr) {
        this.receiveAddr = receiveAddr == null ? null : receiveAddr.trim();
    }

    public String getReceivePostNo() {
        return receivePostNo;
    }

    public void setReceivePostNo(String receivePostNo) {
        this.receivePostNo = receivePostNo == null ? null : receivePostNo.trim();
    }

    public String getReceiveUsername() {
        return receiveUsername;
    }

    public void setReceiveUsername(String receiveUsername) {
        this.receiveUsername = receiveUsername == null ? null : receiveUsername.trim();
    }

    public String getReceiveMobile() {
        return receiveMobile;
    }

    public void setReceiveMobile(String receiveMobile) {
        this.receiveMobile = receiveMobile == null ? null : receiveMobile.trim();
    }

    public String getLogisticsCompany() {
        return logisticsCompany;
    }

    public void setLogisticsCompany(String logisticsCompany) {
        this.logisticsCompany = logisticsCompany == null ? null : logisticsCompany.trim();
    }

    public String getLogisticsNo() {
        return logisticsNo;
    }

    public void setLogisticsNo(String logisticsNo) {
        this.logisticsNo = logisticsNo == null ? null : logisticsNo.trim();
    }

    public String getLogisticsPhone() {
        return logisticsPhone;
    }

    public void setLogisticsPhone(String logisticsPhone) {
        this.logisticsPhone = logisticsPhone == null ? null : logisticsPhone.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(String createdUser) {
        this.createdUser = createdUser == null ? null : createdUser.trim();
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUpdatedUser() {
        return updatedUser;
    }

    public void setUpdatedUser(String updatedUser) {
        this.updatedUser = updatedUser == null ? null : updatedUser.trim();
    }

    public Boolean getDataValid() {
        return dataValid;
    }

    public void setDataValid(Boolean dataValid) {
        this.dataValid = dataValid;
    }
    
    public String getSendProvince() {
        return sendProvince;
    }

    public void setSendProvince(String sendProvince) {
        this.sendProvince = sendProvince == null ? null : sendProvince.trim();
    }

    public String getSendCity() {
        return sendCity;
    }

    public void setSendCity(String sendCity) {
        this.sendCity = sendCity == null ? null : sendCity.trim();
    }

    public String getSendArea() {
        return sendArea;
    }

    public void setSendArea(String sendArea) {
        this.sendArea = sendArea == null ? null : sendArea.trim();
    }

    public String getSendAddr() {
        return sendAddr;
    }

    public void setSendAddr(String sendAddr) {
        this.sendAddr = sendAddr == null ? null : sendAddr.trim();
    }

    public String getSendPostNo() {
        return sendPostNo;
    }

    public void setSendPostNo(String sendPostNo) {
        this.sendPostNo = sendPostNo == null ? null : sendPostNo.trim();
    }

    public String getSendUsername() {
        return sendUsername;
    }

    public void setSendUsername(String sendUsername) {
        this.sendUsername = sendUsername == null ? null : sendUsername.trim();
    }

    public String getSendMobile() {
        return sendMobile;
    }

    public void setSendMobile(String sendMobile) {
        this.sendMobile = sendMobile == null ? null : sendMobile.trim();
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType == null ? null : orderType.trim();
    }

    public Integer getLogisticsPrice() {
        return logisticsPrice;
    }

    public void setLogisticsPrice(Integer logisticsPrice) {
        this.logisticsPrice = logisticsPrice;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public String getVenderId() {
        return venderId;
    }

    public void setVenderId(String venderId) {
        this.venderId = venderId;
    }

    public String getVenderName() {
        return venderName;
    }

    public void setVenderName(String venderName) {
        this.venderName = venderName;
    }
}