package com.xianjinxia.trade.app.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @author zhangjiayuan
 * @create 2017-10-10 14:26
 **/
@ApiModel
public class LoanOrderDto {

    // 借款订单主键
    @ApiModelProperty(example = "1", value = "订单主键",dataType ="Long")
    private Long id;
    @ApiModelProperty(example = "527", value = "用户id",dataType ="Long")
    private Long userId;
    //借款金额
    @ApiModelProperty(example = "100000", value = "借款金额",dataType ="Long" )
    private Integer orderAmount;

    //利息费用
    private Integer interestAmount;

    // 借款类型 2 大额 1 小额
    private String orderType;
    //借款时间 创建时间
    @ApiModelProperty( example ="false", value ="创建时间",dataType ="Long" )
    private Date createdTime;
    //是否逾期
    @ApiModelProperty( example ="false", value ="是否逾期",dataType ="boolean" )
    private boolean isOverdue;
    // 状态显示文本
    @ApiModelProperty( example ="已逾期", value ="状态显示文本",dataType ="String" )
    private String statusText;
    //订单状态
    private String status;
    @ApiModelProperty(example = "2", value = "借款类型 2 大额 1 小额",dataType ="String" )
    private Integer productCategory;

    //商户号
    private String merchantNo;

    private Integer kindId;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(Integer orderAmount) {
        this.orderAmount = orderAmount;
    }

    public Integer getInterestAmount() {
        return interestAmount;
    }

    public void setInterestAmount(Integer interestAmount) {
        this.interestAmount = interestAmount;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public boolean isOverdue() {
        return isOverdue;
    }

    public void setOverdue(boolean overdue) {
        isOverdue = overdue;
    }

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public Integer getKindId() {
        return kindId;
    }

    public void setKindId(Integer kindId) {
        this.kindId = kindId;
    }
}
