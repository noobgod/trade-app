package com.xianjinxia.trade.app.service;

import com.github.pagehelper.PageInfo;
import com.xianjinxia.trade.app.dto.ManualReviewLoanOrderDto;
import com.xianjinxia.trade.app.request.ManualReviewStatusReq;
import com.xianjinxia.trade.app.request.PageReq;
import com.xianjinxia.trade.platform.request.SyncLoanOrderReq;
import com.xianjinxia.trade.shared.domain.LoanOrder;
import com.xianjinxia.trade.app.request.ApplyLoanReq;
import com.xianjinxia.trade.app.request.ConfirmLoanReq;
import com.xianjinxia.trade.app.response.ApplyLoanResponse;
import com.xianjinxia.trade.app.response.BaseResponse;
import com.xianjinxia.trade.app.response.ConfirmLoanResponse;


/**
 * Created by Administrator on 2017/9/18 0018.
 */
public interface ILoanService {

    /**
     * 用户 -> 订单确认
     * @param confirmLoanReq
     * @return
     * @throws Exception
     */
    BaseResponse<ConfirmLoanResponse>  confirmLoanOrder(ConfirmLoanReq confirmLoanReq);// throws Exception;

    BaseResponse<ApplyLoanResponse>  applyLoan(ApplyLoanReq applyLoanReq) throws Exception;

    void orderSendMq(Long userId ,String traceNo ,int productCategory , int amount , String queueName) ;

    int updateLoanStatusByID(Long id);

    LoanOrder selectRejectOrder(Long userId);

    //查询该用户的待审核的订单
    LoanOrder selectReviewOrder(Long userId);

    LoanOrder selectLoanOrderById(Long id);

    /**
     * 查询人工审核中的订单
     * @param pageReq
     * @return
     */
    PageInfo<ManualReviewLoanOrderDto> selectManualReviewLoanOrderList(PageReq pageReq);

    void updateManualReviewStatus(ManualReviewStatusReq manualReviewStatusReq);

    void syncOrderStatus(SyncLoanOrderReq syncLoanOrderReq);

}
