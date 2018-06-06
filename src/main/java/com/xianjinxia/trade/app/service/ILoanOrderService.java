package com.xianjinxia.trade.app.service;


import com.github.pagehelper.PageInfo;
import com.xianjinxia.trade.app.dto.IndexLoanOrderDto;
import com.xianjinxia.trade.app.request.UnfreezeOrdersReq;
import com.xianjinxia.trade.app.response.UnfreezeOrdersResponse;
import com.xianjinxia.trade.shared.domain.LoanOrder;
import com.xianjinxia.trade.app.dto.LoanOrderDto;
import com.xianjinxia.trade.app.dto.TradeDto;
import com.xianjinxia.trade.app.request.ConfirmLoanReq;
import com.xianjinxia.trade.app.request.LoanDetailReq;
import com.xianjinxia.trade.app.response.BaseResponse;
import com.xianjinxia.trade.app.response.ConfirmLoanResponse;

/**
 * 
 * @author liuzhifang
 * @author zhangjiayuan 2017年9月1日
 */
public interface ILoanOrderService {

//    FinanceChannelConf loanFinanceChannelRout(LoanOrder loanOrder);

    // 根据条件分页查询
//    PageInfo<LoanOrderDto> conditionalPaging(LoanOrder loanOrder, Integer pageNum, Integer pageSize);
    PageInfo<LoanOrderDto> conditionalPaging(LoanDetailReq req);
    /**
     * 根据业务id查询借款订单，落单，通知支付中心放款
     */
    void loanOrderRecord(TradeDto tradeDto);

    LoanOrder getByUserIdAndOrderId(LoanOrder order);

    /**
     * 获取用户最新的借款记录
     * 
     * @param loanOrderDto
     * @return
     */
    IndexLoanOrderDto getUserLastLoanOrder(LoanOrderDto loanOrderDto);

    BaseResponse<ConfirmLoanResponse>  insertLoanOrderAndSendMq(ConfirmLoanReq confirmLoanReq , BaseResponse<ConfirmLoanResponse> response , ConfirmLoanResponse confirmLoanResponse);// throws Exception;


    Long getUserIdByOrderBizSeqNo(String bizSeqNo);

    PageInfo<UnfreezeOrdersResponse> getUnfreezeOrderList(UnfreezeOrdersReq unfreezeOrdersReq);

}
