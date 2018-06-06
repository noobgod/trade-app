package com.xianjinxia.trade.platform.request;

import com.xianjinxia.trade.app.request.BaseRequest;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 后台订单接口查询条件独享
 */
public class UserOrderRequest extends BaseRequest {

    @NotNull(message = "userId no value")
    private Long userId;
    private Integer pageNum;
    private Integer pageSize;

    private static final Integer DEFAULT_PAGE_SIZE=10;
    private static final Integer DEFAULT_PAGE_NUM=1;


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
