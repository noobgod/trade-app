package com.xianjinxia.trade.shopping.dto;

public class ProductAppNotifyDto {
    private String productFreezeNo;
    private Long shoppingLoanOrderId;
    private Boolean applyResult;

    public String getProductFreezeNo() {
        return productFreezeNo;
    }

    public void setProductFreezeNo(String productFreezeNo) {
        this.productFreezeNo = productFreezeNo;
    }

    public Long getShoppingLoanOrderId() {
        return shoppingLoanOrderId;
    }

    public void setShoppingLoanOrderId(Long shoppingLoanOrderId) {
        this.shoppingLoanOrderId = shoppingLoanOrderId;
    }

    public Boolean getApplyResult() {
        return applyResult;
    }

    public void setApplyResult(Boolean applyResult) {
        this.applyResult = applyResult;
    }

    public ProductAppNotifyDto(String productFreezeNo, Long shoppingLoanOrderId, Boolean applyResult) {
        this.productFreezeNo = productFreezeNo;
        this.shoppingLoanOrderId = shoppingLoanOrderId;
        this.applyResult = applyResult;
    }

    public ProductAppNotifyDto() {
    }
}
