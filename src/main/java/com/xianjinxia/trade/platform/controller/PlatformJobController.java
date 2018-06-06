package com.xianjinxia.trade.platform.controller;

import com.xianjinxia.trade.app.controller.BaseController;
import com.xianjinxia.trade.app.response.mqapp.ResultMsg;
import com.xianjinxia.trade.platform.schedule.HandlerRePushConfirmLoanJob;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.RejectedExecutionException;

/**
 * 执行定时任务的controller
 * <p>
 * <pre>
 *      1.逾期数据扫描任务
 *      2.逾期费用计算任务
 *      3.定时代扣扫描任务
 *      4.定时代扣计算任务
 *      5.通知催收系统的任务
 *      6.删除超时锁的任务
 *
 * </pre>
 *
 * @author zhangyongjia  zyj@xianjinxia.com
 */
@Api(tags = "job controller")
@RequestMapping("/service/job/")
@RestController
public class PlatformJobController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(PlatformJobController.class);

    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Autowired
    private HandlerRePushConfirmLoanJob handlerRePushConfirmLoanJob;


    /**
     * 订单确认补偿Job
     * @return
     */
    @PostMapping("/confirm-loan-job")
    public ResultMsg confirmLoanJob() {
        try {
            threadPoolTaskExecutor.execute(new Runnable() {
                @Override
                public void run(){
                    handlerRePushConfirmLoanJob.execute();
                }
            });
        }catch (RejectedExecutionException e) {
            logger.error("确认借款任务调度失败，请检查原因：1.调度过于频繁，导致ThreadPool拒收请求 2.JobLock锁未释放", e);
            return innerErrorResp();
        }
        return successResp();
    }

}