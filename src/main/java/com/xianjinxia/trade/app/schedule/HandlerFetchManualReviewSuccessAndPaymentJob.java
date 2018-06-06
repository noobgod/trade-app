package com.xianjinxia.trade.app.schedule;

import com.github.pagehelper.Page;
import com.xianjinxia.trade.app.controller.JobController;
import com.xianjinxia.trade.app.service.IPayCenterService;
import com.xianjinxia.trade.platform.schedule.job.PagebleScanCollectionJob;
import com.xianjinxia.trade.shared.domain.LoanOrder;
import com.xianjinxia.trade.shared.enums.LoanOrderStatusEnum;
import com.xianjinxia.trade.shared.mapper.LoanOrderMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 获取人工审核通过的订单，发送给支付中心，进行放款
 */
@Component
public class HandlerFetchManualReviewSuccessAndPaymentJob extends PagebleScanCollectionJob<LoanOrder, List<LoanOrder>> {

    private static final Logger logger = LoggerFactory.getLogger(HandlerFetchManualReviewSuccessAndPaymentJob.class);

    @Autowired
    private LoanOrderMapper loanOrderMapper;

    @Autowired
    private IPayCenterService payCenterService;

    @Override
    public void process(List<LoanOrder> item) {
        for(LoanOrder loanOrder:item){
            try {
                payCenterService.paymentRequest(loanOrder);
                logger.info("发送给mq成功，订单号为:{}", loanOrder.getBizSeqNo());
            }catch(Exception e){
                logger.info("发送给mq失败，订单号为:{}", loanOrder.getBizSeqNo());
                continue;
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
    public Page<LoanOrder> fetch() {
        Page<LoanOrder> pages = (Page<LoanOrder>) loanOrderMapper.selectListByStatus(LoanOrderStatusEnum.MANUAL_APPROVED.getCode());
        return pages;
    }
}
