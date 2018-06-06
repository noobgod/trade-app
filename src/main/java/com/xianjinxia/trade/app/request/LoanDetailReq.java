package com.xianjinxia.trade.app.request;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

/**
 * @author zhangjiayuan
 * @desc
 * @create 2017-09-25 14:40
 **/
public class LoanDetailReq extends BaseRequest {
    /**
     * 用户id
     */
    @ApiModelProperty(name = "userId",value = "用户Id",example = "222",required = true,dataType = "Long")
    @NotNull(message = "userId couldn't be null")
    private Long userId;
    /**
     * 页码
     */
    @ApiModelProperty(name = "pageNum",value = "页码",example = "1",required = true,dataType = "Integer")
    @NotNull(message = "pageNum couldn't be null")
    private Integer pageNum;
    /**
     * 每页长度
     */
    @ApiModelProperty(name = "pageSize",value = "每页长度",example = "5",required = true,dataType = "Integer")
    @NotNull(message = "pageSize couldn't be null")
    private Integer pageSize;

    /**
     * 订单类型 大额 2 小额 1
     */
    @ApiModelProperty(name = "productCategory",value = "订单类型",example = "1",required = true,dataType = "Integer")
    @NotNull(message = "productCategory couldn't be null")
    private Integer productCategory;

    /**
     * 商户号
     */
    @ApiModelProperty(name = "merchantNo",value = "商户号",example = "jsxjx",required = false,dataType = "String")
    private String merchantNo;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }


    @Override
    public String toString() {
        return "LoanDetailReq{" +
                "userId=" + userId +
                ", pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", productCategory=" + productCategory +
                ", merchantNo='" + merchantNo + '\'' +
                '}';
    }

    public Integer getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(Integer productCategory) {
        this.productCategory = productCategory;
    }

    public String getMerchantNo() {
        return merchantNo;
    }

    public void setMerchantNo(String merchantNo) {
        this.merchantNo = merchantNo;
    }
}
