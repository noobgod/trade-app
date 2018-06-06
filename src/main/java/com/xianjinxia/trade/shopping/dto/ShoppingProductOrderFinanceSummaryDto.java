package com.xianjinxia.trade.shopping.dto;


public class ShoppingProductOrderFinanceSummaryDto {

    /**
     * 订单总数
     */
    private Integer totalCount;
    /**
     * 总应付款
     */
    private String totalAmount;
    /**
     * 商品总数
     */
    private Integer totalProducts;

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

    public Integer getTotalProducts() {
        return totalProducts;
    }

    public void setTotalProducts(Integer totalProducts) {
        this.totalProducts = totalProducts;
    }
}
