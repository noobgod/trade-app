package com.xianjinxia.trade.shopping.service;

import com.xianjinxia.trade.app.dto.IndexLoanOrderDto;
import com.xianjinxia.trade.app.dto.LoanOrderDto;
import com.xianjinxia.trade.platform.request.SyncLoanOrderReq;
import com.xianjinxia.trade.shared.domain.TrdShoppingLoanOrder;
import com.xianjinxia.trade.shared.domain.TrdShoppingProductOrder;
import com.xianjinxia.trade.shopping.response.bgd.ConfirmLoanOrderDetailResponse;
import com.xianjinxia.trade.shopping.dto.ShoppingLoanOrderDto;
import com.xianjinxia.trade.shopping.request.app.ShoppingConfirmLoanReq;
import com.xianjinxia.trade.shopping.request.app.ShoppingConfirmReceiptLoanReq;
import com.xianjinxia.trade.shopping.request.app.ShoppingLoanOrderReq;
import com.xianjinxia.trade.shopping.request.app.ShoppingLogisticsOrderReq;
import com.xianjinxia.trade.shopping.response.bgd.ReceiveLoanOrderDetailResponse;
import com.xianjinxia.trade.shopping.response.soouu.SoouuSuccessResponse;

/**
 * 分期商城订单相关接口
 *
 * @author chunliny ycl@xianjinxia.com
 */
public interface IShoppingLoanService {
	
	/**
	 * 订单立即申请，订单入库
	 * @param shoppingLoanOrderReq
	 * @return
	 */
	ShoppingLoanOrderDto applyLoan(ShoppingLoanOrderReq shoppingLoanOrderReq);
	
	/**
	 * 确认申请时，更新订单状态、冻结编号
	 * @param shoppingConfirmLoanReq
	 */
	void confirmLoan(ShoppingConfirmLoanReq shoppingConfirmLoanReq);
	
	/**
	 * 同步订单的状态
	 * @param syncLoanOrderReq
	 */
	void syncLoanOrderStatus(SyncLoanOrderReq syncLoanOrderReq);
	
	/**
	 * 获取订单详情
	 * @param shoppingLoanOrderId
	 * @return
	 */
	ReceiveLoanOrderDetailResponse getOrderDetailForReceive(Long shoppingLoanOrderId);

	ConfirmLoanOrderDetailResponse getOrderDetailForConfirm(Long shoppingLoanOrderId);

	/**
	 * 订单物流发货
	 * @param shoppingLogisticsOrderReq
	 * @return
	 */
	void logisticsShipmentLoan(ShoppingLogisticsOrderReq shoppingLogisticsOrderReq);
	
	/**
	 * 确认收货
	 * @param shoppingConfirmReceiptLoanReq
	 * @return
	 */
	void confirmReceiptLoan(ShoppingConfirmReceiptLoanReq shoppingConfirmReceiptLoanReq);
	
	/**
	 * 更新订单状态
	 * @param shoppingLoanOrderId
	 * @param newStatus
	 * @param preStatus
	 */
	void updateShoppingLoanOrderStatus(Long shoppingLoanOrderId, String newStatus, String preStatus);

	void updateVirtualShoppingLoanOrderStatus(TrdShoppingProductOrder trdShoppingProductOrder, String newStatus, String preStatus, SoouuSuccessResponse successResponse);


	IndexLoanOrderDto getUserLastLoanOrder(LoanOrderDto loanOrderDto);

	//树鱼下单
	void cardOrderApply(TrdShoppingLoanOrder trdShoppingLoanOrder);

	void cardOrderGet(TrdShoppingLoanOrder trdShoppingLoanOrder);
}
