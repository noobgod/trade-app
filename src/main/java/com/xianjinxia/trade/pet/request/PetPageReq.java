package com.xianjinxia.trade.pet.request;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

/**
 * Created by fanmaowen on 2017/12/13 0013.
 */
public class PetPageReq extends PetBaseRequest {

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
