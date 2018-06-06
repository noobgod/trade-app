package com.xianjinxia.trade.shopping.request.bgd;

public class ShoppingLogisticsOrderEditRequest {
    private Long id;
    private Long shoppingLoanOrderId;
    private String venderId;//供应商ID
    private String venderName;//供应商名称
    private String logisticsNo;//物流编号
    private String sendPostNo;//物流邮编
    private String logisticsCompany;//物流公司
    private Integer logisticsPrice;//物流费用
    private Long sendTime;//发货时间

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
}
