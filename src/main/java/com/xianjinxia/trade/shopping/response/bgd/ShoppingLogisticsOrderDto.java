package com.xianjinxia.trade.shopping.response.bgd;

import javax.validation.constraints.NotNull;

public class ShoppingLogisticsOrderDto {
    private Long id;//订单编号
    private Long shoppingLoanOrderId;//借款订单ID
    private String status;
    private String statusText;
    private String productName;//商品名称
    private Integer productPrice;//商品价格
    private Integer productCount;//商品数量
    private String specificationDesc;//商品规格
    private String userName;// 购买人姓名
    private String receiveUsername;
    private String receiveMobile;
    private String receiveAddr;
    private Long createdTime;
    private Long finishTime;

    private String venderId;
    private String venderName;
    private String logisticsNo;//物流编号
    private String sendPostNo;//物流邮编
    private String logisticsCompany;//物流公司
    private Integer logisticsPrice;//物流费用
    private Long sendTime;//发货时间
    private Integer kindId;

    @NotNull(message = "pageNum不能为空")
    private Integer pageNum;
    @NotNull(message = "pageSize不能为空")
    private Integer pageSize;

    public ShoppingLogisticsOrderDto(){};

    public ShoppingLogisticsOrderDto(Long id, Long shoppingLoanOrderId, String status, String statusText, String productName, Integer productPrice, Integer productCount, String specificationDesc, String userName, String receiveUsername, String receiveMobile, String receiveAddr, Long createdTime, Long finishTime, String venderId, String venderName, String logisticsNo, String sendPostNo, String logisticsCompany, Integer logisticsPrice, Long sendTime, Integer kindId) {
        this.id = id;
        this.shoppingLoanOrderId = shoppingLoanOrderId;
        this.status = status;
        this.statusText = statusText;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productCount = productCount;
        this.specificationDesc = specificationDesc;
        this.userName = userName;
        this.receiveUsername = receiveUsername;
        this.receiveMobile = receiveMobile;
        this.receiveAddr = receiveAddr;
        this.createdTime = createdTime;
        this.finishTime = finishTime;
        this.venderId = venderId;
        this.venderName = venderName;
        this.logisticsNo = logisticsNo;
        this.sendPostNo = sendPostNo;
        this.logisticsCompany = logisticsCompany;
        this.logisticsPrice = logisticsPrice;
        this.sendTime = sendTime;
        this.kindId = kindId;
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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Integer productPrice) {
        this.productPrice = productPrice;
    }

    public Integer getProductCount() {
        return productCount;
    }

    public void setProductCount(Integer productCount) {
        this.productCount = productCount;
    }

    public String getSpecificationDesc() {
        return specificationDesc;
    }

    public void setSpecificationDesc(String specificationDesc) {
        this.specificationDesc = specificationDesc;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getReceiveAddr() {
        return receiveAddr;
    }

    public void setReceiveAddr(String receiveAddr) {
        this.receiveAddr = receiveAddr;
    }

    public Long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Long createdTime) {
        this.createdTime = createdTime;
    }

    public Long getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Long finishTime) {
        this.finishTime = finishTime;
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

    public String getLogisticsNo() {
        return logisticsNo;
    }

    public void setLogisticsNo(String logisticsNo) {
        this.logisticsNo = logisticsNo;
    }

    public String getSendPostNo() {
        return sendPostNo;
    }

    public void setSendPostNo(String sendPostNo) {
        this.sendPostNo = sendPostNo;
    }

    public String getLogisticsCompany() {
        return logisticsCompany;
    }

    public void setLogisticsCompany(String logisticsCompany) {
        this.logisticsCompany = logisticsCompany;
    }

    public Integer getLogisticsPrice() {
        return logisticsPrice;
    }

    public void setLogisticsPrice(Integer logisticsPrice) {
        this.logisticsPrice = logisticsPrice;
    }

    public Long getSendTime() {
        return sendTime;
    }

    public void setSendTime(Long sendTime) {
        this.sendTime = sendTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    public Integer getKindId() {
        return kindId;
    }

    public void setKindId(Integer kindId) {
        this.kindId = kindId;
    }

    public Integer getPageNum() { return pageNum; }

    public void setPageNum(Integer pageNum) { this.pageNum = pageNum; }

    public Integer getPageSize() { return pageSize; }

    public void setPageSize(Integer pageSize) { this.pageSize = pageSize; }
}