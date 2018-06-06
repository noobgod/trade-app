package com.xianjinxia.trade.platform.task;

import com.xianjinxia.trade.platform.request.OpenApiConfirmLoanRequest;
import com.xianjinxia.trade.platform.service.PlatformStandaloneService;
import com.xianjinxia.trade.platform.service.PlatformStandaloneServiceImpl;
import com.xianjinxia.trade.shared.constant.Globals;
import com.xianjinxia.trade.shared.utils.SpringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfirmLoanTask implements Runnable{

    private static Logger logger= LoggerFactory.getLogger(ConfirmLoanTask.class);

    private String openId;
    private OpenApiConfirmLoanRequest openApiConfirmLoanRequest;

    private PlatformStandaloneService platformStandaloneService;

    public ConfirmLoanTask(String openId,OpenApiConfirmLoanRequest openApiConfirmLoanRequest){
        this.openId=openId;
        this.openApiConfirmLoanRequest=openApiConfirmLoanRequest;
        platformStandaloneService= SpringUtil.getContext().getBean(PlatformStandaloneServiceImpl.class);
    }

    @Override
    public void run() {
        for(int i=0;i< Globals.JOB_FIRST_RETRY_TIMES;i++){
            try {
                platformStandaloneService.pushToOpenApi(openId,openApiConfirmLoanRequest);
                break;
            } catch (Exception e) {
                logger.error("ConfirmLoanTask error,orderNo:{}",openApiConfirmLoanRequest.getOrder_no(),e);
            }
        }
    }
}
