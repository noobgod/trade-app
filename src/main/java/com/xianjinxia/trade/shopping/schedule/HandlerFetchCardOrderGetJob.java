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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 获取人工审核通过的商城虚拟订单，向树鱼下单
 */
@Component
public class HandlerFetchCardOrderGetJob extends PagebleScanCollectionJob<TrdShoppingLoanOrder, List<TrdShoppingLoanOrder>> {

    private static final Logger logger = LoggerFactory.getLogger(HandlerFetchCardOrderGetJob.class);

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
                //下单时间超过10分钟才查询
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                long orderTime = loanOrder.getThirdOrderTime().getTime();
                long currTime = new Date().getTime();
                int minutes = (int) (currTime - orderTime) / (1000 * 60);
                if (minutes >= 10) {
                    shoppingLoanService.cardOrderGet(loanOrder);
                }
                logger.info("树鱼订单查询调用成功，虚拟订单号为:{}", loanOrder.getId());
            } catch (Exception e) {
                logger.error("树鱼订单查询失败，虚拟订单号为:{}", loanOrder.getId(), e);
                continue;
            }
        }
    }

    @Override
    public Page<TrdShoppingLoanOrder> fetch() {
        //todo wuhaimin product_kind = 2 需确认
        Page<TrdShoppingLoanOrder> pages = (Page<TrdShoppingLoanOrder>) trdShoppingLoanOrderService.selectListByStatusAndKind(ShoppingLoanOrderStatusEnum.THIRD_ORDERED.getCode(), 2);
        return pages;
    }
}
