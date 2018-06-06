package com.xianjinxia.trade.pet.request;

/**
 * Created by Myron on 2018/2/27.
 */
public class QuerySaleOrdersReq extends PetPageReq {
    private String sortBy;

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }
}
