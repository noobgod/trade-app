package com.xianjinxia.trade.shopping.schedule;

import com.github.pagehelper.Page;
import com.xianjinxia.trade.platform.schedule.job.PagebleScanCollectionJob;
import com.xianjinxia.trade.shared.domain.TrdShoppingLoanOrder;
import com.xianjinxia.trade.shared.enums.ShoppingLoanOrderStatusEnum;
import com.xianjinxia.trade.shopping.service.IShoppingLoanService;
import com.xianjinxia.trade.shopping.service.ITrdShoppingLoanOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 获取人工审核通过的商城虚拟订单，向树鱼下单
 */
@Component
public class HandlerFetchManualReviewSuccessThirdOrderJob extends PagebleScanCollectionJob<TrdShoppingLoanOrder, List<TrdShoppingLoanOrder>> {

    private static final Logger logger = LoggerFactory.getLogger(HandlerFetchManualReviewSuccessThirdOrderJob.class);

    @Autowired
    private ITrdShoppingLoanOrderService trdShoppingLoanOrderService;

    @Autowired
    private IShoppingLoanService shoppingLoanService;

    @Override
    public int pageSize() {
        return 500;
    }

    @Override
    public int threshold() {
        return 3000;
    }

    @Override
    public void process(List<TrdShoppingLoanOrder> item) {
        for (TrdShoppingLoanOrder loanOrder : item) {
            try {
                shoppingLoanService.cardOrderApply(loanOrder);
                logger.info("树鱼下单调用成功，虚拟订单号为:{}", loanOrder.getId());
            } catch (Exception e) {
                logger.error("树鱼下单失败，虚拟订单号为:{}, ", loanOrder.getId(), e);
                continue;
            }
        }
    }

    @Override
    public Page<TrdShoppingLoanOrder> fetch() {
        //todo wuhaimin product_kind = 2 需确认
        Page<TrdShoppingLoanOrder> pages = (Page<TrdShoppingLoanOrder>) trdShoppingLoanOrderService.selectListByStatusAndKind(ShoppingLoanOrderStatusEnum.APPROVED.getCode(), 2);
        logger.info("there {} virtual shopping need third order", pages.getResult().size());
        return pages;
    }
}
