package com.xianjinxia.trade.shopping.service;

import com.github.pagehelper.PageInfo;
import com.xianjinxia.trade.shared.domain.TrdShoppingProductOrder;
import com.xianjinxia.trade.shopping.response.bgd.ShoppingProductOrderDto;

/**
 * @Description: 逻辑层接口
 * @author chunliny
 * @version 1.0
 * @created
 */
public interface ITrdShoppingProductOrderService {

	void insert(TrdShoppingProductOrder trdShoppingProductOrder);
	int updateProductOrderStatus(Long shoppingLoanOrderId, String newStatus, String preStatus);
	//分页查询订单列表
	PageInfo<ShoppingProductOrderDto> getProductOrderPageList(ShoppingProductOrderDto params);

	ShoppingProductOrderDto getById(Long id);

	TrdShoppingProductOrder getByShoppingLoanOrderId(Long shoppingLoanOrderId);

	int updateVirtualProductOrderStatus(Long shoppingLoanOrderId, String newStatus, String preStatusString, String purchasePrice);
}