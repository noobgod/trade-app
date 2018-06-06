package com.xianjinxia.trade.activity.request;

import com.xianjinxia.trade.app.request.BaseRequest;
import com.xianjinxia.trade.shared.idempotent.IdempotentKey;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class BuyApplyRequest extends BaseRequest {
    @NotNull(message = "bizSeqNo couldn't be null")
    @ApiModelProperty(name = "bizSeqNo", value = "业务流水号", example = "1", required = true, dataType = "String")
    @IdempotentKey(order=1)
    private String bizSeqNo;

    @NotNull(message = "productId couldn't be null")
    @ApiModelProperty(name = "productId", value = "商品编号", example = "1", required = true, dataType = "Integer")
    @IdempotentKey(order=2)
    private Integer productId;

    @NotNull(message = "receiveUsername couldn't be null")
    @ApiModelProperty(name = "receiveUsername", value = "收货人姓名", example = "张三", required = true, dataType = "String")
    private String receiveUsername;

    @NotNull(message = "receivePhone couldn't be null")
    @ApiModelProperty(name = "receivePhone", value = "收货人手机号", example = "18521222222", required = true, dataType = "String")
    private String receivePhone;

    @NotNull(message = "receiveCity couldn't be null")
    @ApiModelProperty(name = "receiveCity", value = "收货人城市", example = "上海", required = true, dataType = "String")
    private String receiveCity;

    @NotNull(message = "receiveAddress couldn't be null")
    @ApiModelProperty(name = "receiveAddress", value = "收货人详细地址", example = "长宁区XX弄XX号", required = true, dataType = "String")
    private String receiveAddress;

    @NotNull(message = "userId couldn't be null")
    @ApiModelProperty(name = "userId", value = "用户ID", example = "1", required = true, dataType = "Long")
    @IdempotentKey(order=3)
    private Long userId;

    @NotNull(message = "userPhone couldn't be null")
    @ApiModelProperty(name = "userPhone",value = "用户手机号",example = "XX",required = true,dataType = "String")
    private String userPhone;

    @NotNull(message = "userName couldn't be null")
    @ApiModelProperty(name = "userName",value = "用户姓名",example = "xx",required = true,dataType = "String")
    private String userName;


    @NotNull(message = "productName couldn't be null")
    @ApiModelProperty(name = "productName", value = "商品名称", example = "xx", required = true, dataType = "String")
    private String productName;

    @NotNull(message = "specification couldn't be null")
    @ApiModelProperty(name = "specification", value = "商品规格", example = "xx", required = true, dataType = "String")
    private String specification;

    @NotNull(message = "productPrice couldn't be null")
    @ApiModelProperty(name = "productPrice", value = "商品价格（单位：分）", example = "20", required = true, dataType = "Long")
    private BigDecimal productPrice;

    public String getBizSeqNo() {
        return bizSeqNo;
    }

    public void setBizSeqNo(String bizSeqNo) {
        this.bizSeqNo = bizSeqNo;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getReceiveUsername() {
        return receiveUsername;
    }

    public void setReceiveUsername(String receiveUsername) {
        this.receiveUsername = receiveUsername;
    }

    public String getReceivePhone() {
        return receivePhone;
    }

    public void setReceivePhone(String receivePhone) {
        this.receivePhone = receivePhone;
    }

    public String getReceiveCity() {
        return receiveCity;
    }

    public void setReceiveCity(String receiveCity) {
        this.receiveCity = receiveCity;
    }

    public String getReceiveAddress() {
        return receiveAddress;
    }

    public void setReceiveAddress(String receiveAddress) {
        this.receiveAddress = receiveAddress;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "OrderApplyRequest{" +
                "bizSeqNo='" + bizSeqNo + '\'' +
                ", productId=" + productId +
                ", receiveUsername='" + receiveUsername + '\'' +
                ", receivePhone='" + receivePhone + '\'' +
                ", receiveCity='" + receiveCity + '\'' +
                ", receiveAddress='" + receiveAddress + '\'' +
                ", userId=" + userId +
                ", userPhone='" + userPhone + '\'' +
                ", userName='" + userName + '\'' +
                ", productName='" + productName + '\'' +
                ", specification='" + specification + '\'' +
                ", productPrice=" + productPrice +
                '}';
    }
}
