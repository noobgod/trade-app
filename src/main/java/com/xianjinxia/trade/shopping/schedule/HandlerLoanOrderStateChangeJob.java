package com.xianjinxia.trade.shopping.schedule;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.pagehelper.Page;
import com.xianjinxia.trade.platform.schedule.job.PagebleScanCollectionJob;
import com.xianjinxia.trade.shared.domain.TrdShoppingLogisticsOrder;
import com.xianjinxia.trade.shared.enums.ShoppingLoanOrderStatusEnum;
import com.xianjinxia.trade.shared.utils.DateUtil;
import com.xianjinxia.trade.shopping.service.IShoppingLoanService;
import com.xianjinxia.trade.shopping.service.ITrdShoppingLogisticsOrderService;

/**
 * 商城订单状态变更,配送超十日,配送中修改为待还款状态
 * @author chunliny
 *
 */
@Component
public class HandlerLoanOrderStateChangeJob extends PagebleScanCollectionJob<TrdShoppingLogisticsOrder, List<TrdShoppingLogisticsOrder>> {
	
	/**
	 * 配送
	 */
    private static final int DISTRIBUTION_OVER_DAYS = -10;

	private static Logger logger = LoggerFactory.getLogger(HandlerLoanOrderStateChangeJob.class);
	
    @Autowired
	private ITrdShoppingLogisticsOrderService trdShoppingLogisticsOrderService;
	
    @Autowired
	private IShoppingLoanService shoppingLoanService;

    @Override
    public void process(List<TrdShoppingLogisticsOrder> item) {
        if(CollectionUtils.isEmpty(item)){
        	return;
        }
		for (TrdShoppingLogisticsOrder trdShoppingLogisticsOrder : item) {
			try {
				shoppingLoanService.updateShoppingLoanOrderStatus(trdShoppingLogisticsOrder.getShoppingLoanOrderId(), ShoppingLoanOrderStatusEnum.RECEIVED.getCode(), ShoppingLoanOrderStatusEnum.SENDING.getCode());
			} catch (Exception ex) {
				logger.error("商城订单状态变更失败,订单编号:{}", item);
			}
		}
    }

    @Override
    public int pageSize() {
        return 500;
    }

    @Override
    public int threshold() {
        return 3000;
    }

    @Override
    public Page<TrdShoppingLogisticsOrder> fetch() {
    	String endTime = DateUtil.getDateStr(DateUtil.addDay(new Date(), DISTRIBUTION_OVER_DAYS));
        Page<TrdShoppingLogisticsOrder> pages = (Page<TrdShoppingLogisticsOrder>) trdShoppingLogisticsOrderService.selectBySendTimeAndStatus(endTime, ShoppingLoanOrderStatusEnum.SENDING.getCode());
        return pages;
    }
}
