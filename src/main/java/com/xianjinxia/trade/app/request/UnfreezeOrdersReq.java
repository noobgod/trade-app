package com.xianjinxia.trade.app.request;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by liquan on 2018/1/2.
 */
public class UnfreezeOrdersReq extends BaseRequest {
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
    /**
     * 解冻时间=审核失败时间+冷却时间
     */

    @ApiModelProperty(name = "unfreezeTime",value = "审核失败时间",example = "1513699200000",required = true,dataType = "Date")
    @NotNull(message = "unfreezeTime couldn't be null")
    private Date unfreezeTime;
    /**
     * 产品表ID
     */
    @ApiModelProperty(name = "id",value = "产品ID",example = "1",required = true,dataType = "Long")
    @NotNull(message = "id couldn't be null")
    private Long id;

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

    public Date getUnfreezeTime() {
        return unfreezeTime;
    }

    public void setUnfreezeTime(Date unfreezeTime) {
        this.unfreezeTime = unfreezeTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    @Override
    public String toString(){
        return "UnfreezeOrdersReq:{"
                +"\n pageNum: "+pageNum
                +"\n pageSize: "+pageSize
                +"\n unfreezeTime: "+unfreezeTime
                +"\n id: "+id
                +"}";
    }
}
