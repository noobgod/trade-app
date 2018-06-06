package com.xianjinxia.trade.app.response;

import com.xianjinxia.trade.app.request.BaseRequest;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

/**
 * Created by liquan on 2018/1/2.
 */
public class UnfreezeOrdersResponse {

    @ApiModelProperty(example = "15027990379", value = "用户手机号",dataType ="String")
    private String userPhone;
    @ApiModelProperty(example = "lo2017111018115259o3", value = "订单业务编号",dataType ="String")
    private String bizSeqNo;
    /**
     * 页码
     */
    @ApiModelProperty(name = "pageNum",value = "页码",example = "1",required = true,dataType = "Integer")
    @NotNull(message = "pageNum couldn't be null")
    private Integer pageNum;
    /**
     * 每页长度
     */
    @ApiModelProperty(name = "pageSize",value = "每页长度",example = "500",required = true,dataType = "Integer")
    @NotNull(message = "pageSize couldn't be null")
    private Integer pageSize;
    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getBizSeqNo() {
        return bizSeqNo;
    }

    public void setBizSeqNo(String bizSeqNo) {
        this.bizSeqNo = bizSeqNo;
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
}
