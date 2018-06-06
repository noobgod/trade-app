package com.xianjinxia.trade.shopping.service;

import com.xianjinxia.trade.shared.domain.TrdShoppingLoanCardInfo;
import com.xianjinxia.trade.shopping.response.bgd.CardInfoDto;
import com.xianjinxia.trade.shopping.response.soouu.SoouuSuccessResponse;

import java.util.List;

import org.apache.ibatis.annotations.Param;

/**
 * @author wuhaimin
 */
public interface ITrdShoppingLoanCardInfoService {
    void insert(Long shoppingProductOrderId, String productName, Integer productUnitPrice, SoouuSuccessResponse successResponse);

    List<CardInfoDto> getByOrderId(Long shoppingProductOrderId);
    /**
     * 搜卡通过卡和密码 list查询 返回list
     */
    String selectByNoAndPassword(String data);
}