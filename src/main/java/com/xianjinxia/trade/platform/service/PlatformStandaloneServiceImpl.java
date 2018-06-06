package com.xianjinxia.trade.platform.service;

import com.xianjinxia.trade.platform.remote.OpenApiRemoteService;
import com.xianjinxia.trade.platform.request.OpenApiConfirmLoanRequest;
import com.xianjinxia.trade.platform.status.LoanOrderStatus;
import com.xianjinxia.trade.shared.exception.SqlUpdateException;
import com.xianjinxia.trade.shared.mapper.TrdPlatformLoanOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PlatformStandaloneServiceImpl implements PlatformStandaloneService {


    @Autowired
    private TrdPlatformLoanOrderMapper trdPlatformLoanOrderMapper;

    @Autowired
    private OpenApiRemoteService openApiRemoteService;


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void pushToOpenApi(String appId,OpenApiConfirmLoanRequest openApiConfirmLoanRequest) {
        //推送第三方
        openApiRemoteService.confirmLoan(appId,openApiConfirmLoanRequest);
        //修改状态为确认借款推送成功
        int resultForPayment = trdPlatformLoanOrderMapper.updateLoanOrderStatus(openApiConfirmLoanRequest.getOrder_no(),
                LoanOrderStatus.CONFIRM_PUSH_SUCCESS.getValue(), LoanOrderStatus.CONFIRM_PUSHING.getValue(), null,null);
        if (resultForPayment != 1) {
            //不需要抛出异常，默认会重试
            throw new SqlUpdateException("update platformLoanOrder status from CONFIRM_PUSHING to CONFIRM_PUSH_SUCCESS error,orderNo:" +
                    openApiConfirmLoanRequest.getOrder_no());
        }

    }
}
