package com.xianjinxia.trade.shopping.dto;


public class ShoppingVirtualProductOrderFinanceSummaryDto {

    /**
     * 订单总数
     */
    private Integer totalCount;
    /**
     * 总应付款
     */
    private String totalAmount;
    /**
     * 总销售额
     */
    private String totalSaleAmount;

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getTotalSaleAmount() {
        return totalSaleAmount;
    }

    public void setTotalSaleAmount(String totalSaleAmount) {
        this.totalSaleAmount = totalSaleAmount;
    }
}
