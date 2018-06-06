package com.xianjinxia.trade.platform.service;

import com.xianjinxia.trade.platform.dto.CheckLoanRequest;
import com.xianjinxia.trade.platform.dto.LoanOrderDetailDto;
import com.xianjinxia.trade.platform.dto.TraceDto;
import com.xianjinxia.trade.platform.dto.TrdPlatformLoanApplyDto;
import com.xianjinxia.trade.platform.request.BackOrderRequest;
import com.xianjinxia.trade.platform.request.CancelOrderReq;
import com.xianjinxia.trade.platform.request.LoanApplyRequest;
import com.xianjinxia.trade.platform.request.LoanOrderDetailReq;
import com.xianjinxia.trade.platform.request.PlatformConfirmLoanRequest;
import com.xianjinxia.trade.platform.request.TraceReq;
import com.xianjinxia.trade.platform.request.UserOrderRequest;
import com.xianjinxia.trade.platform.response.BackOrderResp;
import com.xianjinxia.trade.platform.response.CheckLoanResponse;
import com.xianjinxia.trade.platform.response.UserOrderResp;
import com.xianjinxia.trade.platform.status.LoanOrderStatus;
import com.xianjinxia.trade.platform.status.PlatformTraceOrderEventEnum;
import com.xianjinxia.trade.shared.page.PageObj;

import java.util.Date;
import java.util.List;

public interface PlatformLoanOrderService {

    /**
     * 借款申请 提交订单
     * @param loanApplyRequest
     * @return
     */
    TrdPlatformLoanApplyDto loanApply(LoanApplyRequest loanApplyRequest);


    /**
     * 借款申请收到loanark-web的推送结果回调，更改借款订单推送的状态
     * @param orderNo 订单编号
     * @param pushResult    推送结果
     * @param bizResult     第三方业务校验结果，例如订单金额在第三方校验失败，此时pushResult=true, bizStatus=false, 推送结果和校验结果都成功的情况下，在交易系统才会把订单的第三方推送状态设置为成功
     */
    void loanApplyCallback(String orderNo, boolean pushResult, boolean bizResult);

    List<TrdPlatformLoanApplyDto> getLoanOrderApplyRetryList();

    /**
     * 根据条件分页查询订单信息
     * @param orderRequest 查询条件
     * @return
     */
    PageObj<BackOrderResp> getOrders(BackOrderRequest orderRequest);

    /**
     * 根据用户ID和 ServiceCompany分页查询用户订单
     * @param userOrderRequest
     * @return
     */
    PageObj<UserOrderResp> getUserOrders(UserOrderRequest userOrderRequest);

    /**
     * 订单确认处理
     * @param confirmLoanReq
     */
    void confirmLoan(PlatformConfirmLoanRequest confirmLoanReq);

    /**
     * 判断该笔订单是否属于这个用户
     * @param orderNo
     * @param userId
     * @return
     */
    Boolean orderIsBelongToUser(String orderNo, Long userId);

    LoanOrderDetailDto getLoanOrderDetail(LoanOrderDetailReq loanOrderDetailReq);

    List<TraceDto> getTrace(TraceReq traceReq);
    
    void cancelOrder(CancelOrderReq cancelOrderReq);


    /**
     * 订单状态变更   添加trace表记录
     *
     * @param orderNo             订单号
     * @param targetStatus        新状态
     * @param originStatus        老状态
     * @param remark              状态变换时的留言   没有可为空
     * @param platformTraceOrderEventEnum  Trace表需要的 更新场景
     * @param traceData           Trace表需要的data
     */
    void updateOrderStatusAndInsertTrace(String orderNo, LoanOrderStatus targetStatus, LoanOrderStatus originStatus, String remark, PlatformTraceOrderEventEnum platformTraceOrderEventEnum, String traceData);

    CheckLoanResponse checkLoan(CheckLoanRequest checkLoanRequest);



    void updateLoanOrderStatus(String orderNo, LoanOrderStatus targetStatus, LoanOrderStatus originStatus, String remark);



    void insertTrace(String traceNo, PlatformTraceOrderEventEnum platformTraceOrderEventEnum, String traceData);

    /**
     * 更新订单表状态--带更新时间
     * @param orderNo
     * @param targetStatus
     * @param originStatus
     * @param updateTime
     * @param remark
     */
    void updateOrderStatusAndTime(String orderNo, LoanOrderStatus targetStatus, LoanOrderStatus originStatus, Date updateTime, String remark);

}