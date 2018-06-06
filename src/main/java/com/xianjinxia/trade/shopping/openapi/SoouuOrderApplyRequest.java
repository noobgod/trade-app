package com.xianjinxia.trade.shopping.openapi;

import com.google.common.base.Preconditions;

/**
 * 树鱼内部请求
 *
 * @author wuhaimin
 */
public class SoouuOrderApplyRequest {
    private String customerOrderNo;
    private String productId;
    private String buyNum;

    public String getCustomerOrderNo() {
        return customerOrderNo;
    }

    public void setCustomerOrderNo(String customerOrderNo) {
        this.customerOrderNo = customerOrderNo;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getBuyNum() {
        return buyNum;
    }

    public void setBuyNum(String buyNum) {
        this.buyNum = buyNum;
    }

    public SoouuOrderApplyRequest(String customerOrderNo, String productId, String buyNum) {
        this.customerOrderNo = customerOrderNo;
        this.productId = productId;
        this.buyNum = buyNum;
    }

    @Override
    public String toString() {
        return "SoouuOrderApplyRequest{" +
                ", customerOrderNo='" + customerOrderNo + '\'' +
                ", productId='" + productId + '\'' +
                ", buyNum='" + buyNum + '\'' +
                '}';
    }

    /**
     * 请求参数检查
     * @return
     */
    public boolean validate(){
        Preconditions.checkNotNull(customerOrderNo,"customerOrderNo can not be null!");
        Preconditions.checkNotNull(productId,"productId can not be null!");
        Preconditions.checkNotNull(buyNum,"buyNum can not be null!");
        return true;
    }


}
