package com.xianjinxia.trade.shopping.service;

import com.github.pagehelper.PageInfo;
import com.xianjinxia.trade.shopping.dto.ShoppingProductOrderFinanceDto;
import com.xianjinxia.trade.shopping.dto.ShoppingProductOrderFinanceSummaryDto;
import com.xianjinxia.trade.shopping.dto.ShoppingVirtualProductOrderFinanceDto;
import com.xianjinxia.trade.shopping.dto.ShoppingVirtualProductOrderFinanceSummaryDto;
import com.xianjinxia.trade.shopping.request.bgd.FinanceShoppingOrderRequest;

import java.text.ParseException;

/**
 * @author wuhaimin
 * @version 1.0
 * @Description: 商城订单财务管理
 * @created
 */
public interface ITrdShoppingOrderFinanceService {

    PageInfo<ShoppingProductOrderFinanceDto> selectByParameter(FinanceShoppingOrderRequest request) throws ParseException;

    ShoppingProductOrderFinanceSummaryDto selectSummaryByParameter(FinanceShoppingOrderRequest request);

    PageInfo<ShoppingVirtualProductOrderFinanceDto> selectVirtualOrderByParameter(FinanceShoppingOrderRequest request) throws ParseException;

    ShoppingVirtualProductOrderFinanceSummaryDto selectVirtualOrderSummaryByParameter(FinanceShoppingOrderRequest request);


}