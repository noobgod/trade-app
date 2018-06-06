package com.xianjinxia.trade.shared.mapper;

import com.xianjinxia.trade.shopping.dto.ShoppingProductOrderFinanceDto;
import com.xianjinxia.trade.shopping.dto.ShoppingProductOrderFinanceSummaryDto;
import com.xianjinxia.trade.shopping.dto.ShoppingVirtualProductOrderFinanceDto;
import com.xianjinxia.trade.shopping.dto.ShoppingVirtualProductOrderFinanceSummaryDto;
import com.xianjinxia.trade.shopping.request.bgd.FinanceShoppingOrderRequest;

import java.util.List;

public interface TrdShoppingLoanOrderFinanceMapper {


    List<ShoppingProductOrderFinanceDto> selectSelective(FinanceShoppingOrderRequest request);

    ShoppingProductOrderFinanceSummaryDto selectSummaryBySelective(FinanceShoppingOrderRequest request);

    List<ShoppingVirtualProductOrderFinanceDto> selectVirtualOrderByParameter(FinanceShoppingOrderRequest request);

    ShoppingVirtualProductOrderFinanceSummaryDto selectVirtualOrderSummaryByParameter(FinanceShoppingOrderRequest request);


}