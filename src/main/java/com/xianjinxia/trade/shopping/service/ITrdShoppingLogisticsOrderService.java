package com.xianjinxia.trade.shopping.service;

import com.github.pagehelper.PageInfo;
import com.xianjinxia.trade.shared.domain.TrdShoppingLogisticsOrder;
import com.xianjinxia.trade.shopping.response.bgd.ShoppingLogisticsOrderDto;

import java.util.List;

/**
 * @Description: 逻辑层接口
 * @author chunliny
 * @version 1.0
 * @created
 */
public interface ITrdShoppingLogisticsOrderService {
	void insert(TrdShoppingLogisticsOrder trdShoppingLogisticsOrder);
	int updateLogisticsOrderStatus(Long shoppingLoanOrderId, String newStatus, String preStatus);
	void updateLogisticsOrder(TrdShoppingLogisticsOrder trdShoppingLogisticsOrder, boolean isUpdateStatus);
	TrdShoppingLogisticsOrder getByShoppingLoanOrderId(Long shoppingLoanOrderId);
	List<TrdShoppingLogisticsOrder> selectBySendTimeAndStatus(String endTime, String status);
	//分页查询订单列表
	PageInfo<ShoppingLogisticsOrderDto> getLogisticsOrderPageList(ShoppingLogisticsOrderDto params);
	ShoppingLogisticsOrderDto getShoppingLogisticsOrderDtoById(Long id);
	TrdShoppingLogisticsOrder getById(Long id);

}