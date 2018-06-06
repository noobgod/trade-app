package com.xianjinxia.trade.platform.request;

import com.xianjinxia.trade.app.request.BaseRequest;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 后台订单接口查询条件独享
 */
public class BackOrderRequest extends BaseRequest {

    private String orderNo;
    private String serviceCompany;
    private String productCode;
    private String status;
    private String userId;
    private String userPhone;
    private Date beginCreatedTime;
    private Date endCreatedTime;
    @NotNull(message = "pageNum no value")
    private Integer pageNum=DEFAULT_PAGE_NUM;
    private Integer pageSize=DEFAULT_PAGE_SIZE;

    private static final Integer DEFAULT_PAGE_SIZE=10;
    private static final Integer DEFAULT_PAGE_NUM=1;


    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getServiceCompany() {
        return serviceCompany;
    }

    public void setServiceCompany(String serviceCompany) {
        this.serviceCompany = serviceCompany;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public Date getBeginCreatedTime() {
        return beginCreatedTime;
    }

    public void setBeginCreatedTime(Date beginCreatedTime) {
        this.beginCreatedTime = beginCreatedTime;
    }

    public Date getEndCreatedTime() {
        return endCreatedTime;
    }

    public void setEndCreatedTime(Date endCreatedTime) {
        this.endCreatedTime = endCreatedTime;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        if(pageNum==null || pageNum<=0){
            this.pageNum=DEFAULT_PAGE_NUM;
            return;
        }
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        if(pageSize==null || pageSize<=0){
            this.pageSize=DEFAULT_PAGE_SIZE;
            return;
        }
        this.pageSize = pageSize;
    }
}
