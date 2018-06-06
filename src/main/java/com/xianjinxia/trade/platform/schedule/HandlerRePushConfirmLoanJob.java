package com.xianjinxia.trade.platform.schedule;

import com.github.pagehelper.Page;
import com.xianjinxia.trade.platform.request.OpenApiConfirmLoanRequest;
import com.xianjinxia.trade.platform.schedule.job.PagebleScanCollectionJob;
import com.xianjinxia.trade.platform.service.PlatformStandaloneService;
import com.xianjinxia.trade.platform.status.LoanOrderStatus;
import com.xianjinxia.trade.platform.status.PlatformTraceOrderEventEnum;
import com.xianjinxia.trade.shared.constant.Globals;
import com.xianjinxia.trade.shared.domain.Trace;
import com.xianjinxia.trade.shared.domain.TrdPlatformLoanOrder;
import com.xianjinxia.trade.shared.mapper.TraceMapper;
import com.xianjinxia.trade.shared.mapper.TrdPlatformLoanOrderMapper;
import com.xianjinxia.trade.shared.utils.AppUtil;
import com.xianjinxia.trade.shared.utils.MoneyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.List;

/**
 * 确认借款补偿Job
 */
@Component
public class HandlerRePushConfirmLoanJob extends PagebleScanCollectionJob<TrdPlatformLoanOrder, List<TrdPlatformLoanOrder>> {

    private static Logger logger= LoggerFactory.getLogger(HandlerRePushConfirmLoanJob.class);

    @Autowired
    private PlatformStandaloneService platformStandaloneService;

    @Autowired
    private TrdPlatformLoanOrderMapper trdPlatformLoanOrderMapper;

    @Autowired
    private TraceMapper traceMapper;

    @Override
    public void process(List<TrdPlatformLoanOrder> item) {
        for (TrdPlatformLoanOrder loanOrder : item) {
            try {
                platformStandaloneService.pushToOpenApi(loanOrder.getServiceCompany(), new OpenApiConfirmLoanRequest(
                        loanOrder.getOrderNo(), MoneyUtil.changeCentToYuan(loanOrder.getOrderAmount()),
                        loanOrder.getTerm(),loanOrder.getUserBankCardId(),loanOrder.getIsReloan()==true?1:0));
            } catch (Exception e) {
                logger.error("PlatformConfirmLoanJob error,platform loanOrder orderNo:{}",loanOrder.getOrderNo(),e);
                int hasTryTimes=loanOrder.getRetryTimes()+1;
                //如果重试次数达到规定的最大次数
                if(hasTryTimes>= Globals.JOB_MAX_RETRY_TIMES){
                    //更新状态为失败
                    trdPlatformLoanOrderMapper.updateLoanOrderStatus(loanOrder.getOrderNo(),
                            LoanOrderStatus.PUSH_FAIL.getValue(),LoanOrderStatus.CONFIRM_PUSHING.getValue(),
                            null,"订单确认推送失败");
                    traceMapper.insert(new Trace(loanOrder.getTraceNo(), PlatformTraceOrderEventEnum.PLATFORM_AUDIT_FAIL.getCode(),
                            PlatformTraceOrderEventEnum.PLATFORM_AUDIT_FAIL.getText(), new Date(), null));
                    return;
                }
                trdPlatformLoanOrderMapper.updateNextRetryTimeAndRetryTimesById(loanOrder.getId(),
                        AppUtil.getNextRetryTime(new Date(),hasTryTimes), hasTryTimes);

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
    public Page<TrdPlatformLoanOrder> fetch() {
        Page<TrdPlatformLoanOrder> pages = (Page<TrdPlatformLoanOrder>) trdPlatformLoanOrderMapper.getByStatusAndNextRetryTime(LoanOrderStatus.CONFIRM_PUSHING.getValue(),new Date());
        return pages;

    }
}
