package com.xianjinxia.trade.shopping.controller;

import com.xianjinxia.trade.app.controller.BaseController;
import com.xianjinxia.trade.app.response.mqapp.ResultMsg;
import com.xianjinxia.trade.shopping.schedule.HandlerFetchCardOrderGetJob;
import com.xianjinxia.trade.shopping.schedule.HandlerFetchManualReviewSuccessThirdOrderJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/service/shopping/job")
@RestController
public class ShoppingJobController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(ShoppingJobController.class);

    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Autowired
    private HandlerFetchManualReviewSuccessThirdOrderJob handlerFetchManualReviewSuccessThirdOrderJob;

    @Autowired
    private HandlerFetchCardOrderGetJob handlerFetchCardOrderGetJob;

    /**
     * 商城虚拟商品订单，批量至第三方下单（订单状态31，kindId=2的至第三方下单）
     *
     * @return
     */
    @PostMapping("/batch-update-shopping-virtual-loan-status")
    public ResultMsg batchUpdateShoppingVirtualLoanStatus() {
        logger.info("virtual third order apply call start");
        try {
            threadPoolTaskExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    logger.info("batch-update-shopping-virtual-loan-status run start");

                    handlerFetchManualReviewSuccessThirdOrderJob.execute();

                    logger.info("batch-update-shopping-virtual-loan-status run end");
                }
            });
        } catch (Exception e) {
            logger.error("批量更新商城虚拟订单状态调度失败，请检查原因：1.调度过于频繁，导致ThreadPool拒收请求 2.JobLock锁未释放", e);
        }
        logger.info("batch-update-shopping-virtual-loan-status call end");
        return successResp();
    }

    /**
     * 商城虚拟商品订单，批量至第三方查询卡密（订单状态60的至第三方查询卡密）
     *
     * @return
     */
    @PostMapping("/batch-get-third-order-cards")
    public ResultMsg batchGetThirdOrderCards() {
        logger.info("virtual third order get cards call start");
        try {
            threadPoolTaskExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    logger.info("batch-get-third-order-cards run start");

                    handlerFetchCardOrderGetJob.execute();

                    logger.info("batch-get-third-order-cards run end");
                }
            });
        } catch (Exception e) {
            logger.error("批量更新商城虚拟订单状态调度失败，请检查原因：1.调度过于频繁，导致ThreadPool拒收请求 2.JobLock锁未释放", e);
        }
        logger.info("batch-get-third-order-cards call end");
        return successResp();
    }
}
