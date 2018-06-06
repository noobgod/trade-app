package com.xianjinxia.trade.pet.dto;

/**
 * Created by Myron on 2018/2/28.
 */
public class QueryPlatformPetsDto {
    private Long userId;
    private String status;
    private Integer current;
    private Integer limit;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getCurrent() {
        return current;
    }

    public void setCurrent(Integer current) {
        this.current = current;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }
}
