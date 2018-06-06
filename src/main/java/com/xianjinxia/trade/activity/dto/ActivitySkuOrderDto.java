package com.xianjinxia.trade.activity.dto;

/** @author ganminghui */
public class ActivitySkuOrderDto {
    /* 订单编号 */private long id;
    /* 订单状态 */private String status;
    /* 商品编号 */private int productId;
    /* 商品名称 */private String productName;
    /* 商品售价 */private String productPrice;
    /* 收货人   */private String receiveUsername;
    /* 收货电话 */private String receivePhone;
    /* 收货城市 */private String receiveCity;
    /* 收货地址 */private String receiveAddress;
    /* 物流公司 */private String logisticsCompany;
    /* 快递单号 */private String logisticsNo;
    private Long userId;

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }

    public int getProductId() { return productId; }

    public void setProductId(int productId) { this.productId = productId; }

    public String getProductName() { return productName; }

    public void setProductName(String productName) { this.productName = productName; }

    public String getProductPrice() { return productPrice; }

    public void setProductPrice(String productPrice) { this.productPrice = productPrice; }

    public String getReceiveUsername() { return receiveUsername; }

    public void setReceiveUsername(String receiveUsername) { this.receiveUsername = receiveUsername; }

    public String getReceivePhone() { return receivePhone; }

    public void setReceivePhone(String receivePhone) { this.receivePhone = receivePhone; }

    public String getReceiveCity() { return receiveCity; }

    public void setReceiveCity(String receiveCity) { this.receiveCity = receiveCity; }

    public String getReceiveAddress() { return receiveAddress; }

    public void setReceiveAddress(String receiveAddress) { this.receiveAddress = receiveAddress; }

    public String getLogisticsCompany() { return logisticsCompany; }

    public void setLogisticsCompany(String logisticsCompany) { this.logisticsCompany = logisticsCompany; }

    public String getLogisticsNo() { return logisticsNo; }

    public void setLogisticsNo(String logisticsNo) { this.logisticsNo = logisticsNo; }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}