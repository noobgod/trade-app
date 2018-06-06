package com.xianjinxia.trade.shared.mapper;

import java.util.List;

import com.xianjinxia.trade.shopping.response.bgd.ShoppingProductOrderDto;
import org.apache.ibatis.annotations.Param;

import com.xianjinxia.trade.shared.domain.TrdShoppingProductOrder;

public interface TrdShoppingProductOrderMapper {

    int insert(TrdShoppingProductOrder record);

    TrdShoppingProductOrder selectByPrimaryKey(Long id);
    
    int updateProductOrderStatus(@Param("shoppingLoanOrderId") Long shoppingLoanOrderId,  @Param("newStatus") String newStatus, @Param("preStatus") String preStatus);

    List<TrdShoppingProductOrder> selectPage(ShoppingProductOrderDto params);

    TrdShoppingProductOrder getByShoppingLoanOrderId(@Param("shoppingLoanOrderId") Long shoppingLoanOrderId);

    int updateProductStatusAndPurchasePrice(@Param("shoppingLoanOrderId") Long shoppingLoanOrderId,  @Param("newStatus") String newStatus, @Param("preStatus") String preStatus,  @Param("purchasePrice") String purchasePrice);

    List<ShoppingProductOrderDto> selectByParams(ShoppingProductOrderDto params);
}