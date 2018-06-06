package com.xianjinxia.trade.app.controller;

import com.xianjinxia.trade.app.response.mqapp.ResultMsg;
import com.xianjinxia.trade.app.schedule.HandlerFetchManualReviewSuccessAndPaymentJob;
import com.xianjinxia.trade.shopping.schedule.HandlerLoanOrderStateChangeJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/service/review")
@RestController
public class JobController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(JobController.class);

    @Autowired
    private HandlerFetchManualReviewSuccessAndPaymentJob job;

    @Autowired
    private HandlerLoanOrderStateChangeJob handlerLoanOrderStateChangeJob;

    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;


    /**
     * 拉取人工审核通过的订单进行放款
     *
     * @return
     */
    @PostMapping("/fetch-mannual-review-success")
    public ResultMsg fetchManualReviewSuccessJob() {
        logger.info("fetch-mannual-review-success call start");
        try {
            threadPoolTaskExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    logger.info("fetch-mannual-review-success run start");

                    job.execute();

                    logger.info("fetch-mannual-review-success run end");
                }
            });
        } catch (Exception e) {
            logger.error("拉取人工审核通过的订单调度失败，请检查原因：1.调度过于频繁，导致ThreadPool拒收请求 2.JobLock锁未释放", e);
        }
        logger.info("fetch-mannual-review-success call end");
        return successResp();
    }

    /**
     * 批量更新商城订单状态(超10日将配送中状态,更新为已确认收货)
     *
     * @return
     */
    @PostMapping("/batch-update-shopping-loan-status")
    public ResultMsg batchUpdateShoppingLoanStatus() {
        logger.info("fetch-mannual-review-success call start");
        try {
            threadPoolTaskExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    logger.info("batch-update-shopping-loan-statu run start");

                    handlerLoanOrderStateChangeJob.execute();

                    logger.info("batch-update-shopping-loan-statu run end");
                }
            });
        } catch (Exception e) {
            logger.error("批量更新商城订单状态调度失败，请检查原因：1.调度过于频繁，导致ThreadPool拒收请求 2.JobLock锁未释放", e);
        }
        logger.info("batch-update-shopping-loan-status call end");
        return successResp();
    }


}
