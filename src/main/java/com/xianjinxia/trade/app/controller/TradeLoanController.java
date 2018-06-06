package com.xianjinxia.trade.app.controller;

import com.alibaba.fastjson.JSON;
import com.dianping.cat.common.EventInfo;
import com.dianping.cat.utils.CatUtils;
import com.github.pagehelper.PageInfo;
import com.xianjinxia.trade.app.dto.IndexLoanOrderDto;
import com.xianjinxia.trade.app.dto.ManualReviewLoanOrderDto;
import com.xianjinxia.trade.app.request.*;
import com.xianjinxia.trade.app.response.*;
import com.xianjinxia.trade.app.response.mqapp.ResultMsg;
import com.xianjinxia.trade.shared.enums.LoanCodeMsgEnum;
import com.xianjinxia.trade.shared.exception.IdempotentException;
import com.xianjinxia.trade.shared.constant.QueueConst;
import com.xianjinxia.trade.shared.domain.LoanOrder;
import com.xianjinxia.trade.shared.domain.Trace;
import com.xianjinxia.trade.app.dto.LoanOrderDto;
import com.xianjinxia.trade.app.dto.LoanOrderRiskDto;
import com.xianjinxia.trade.app.dto.TraceDto;
import com.xianjinxia.trade.app.service.ILoanOrderService;
import com.xianjinxia.trade.app.service.ILoanService;
import com.xianjinxia.trade.app.service.ITraceService;
import com.xianjinxia.trade.shared.exception.ServiceException;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author fanmaowen
 * <p>
 * 2017年9月8日
 */
@RestController
@RequestMapping("/service/loan")
public class TradeLoanController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(TradeLoanController.class);

    @Autowired
    private ILoanService loanService;


    @Autowired
    ILoanOrderService loanOrderService;

    @Autowired
    ITraceService traceService;


    @ApiOperation(value = "trade_loan_apply", httpMethod = "POST",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE, notes = "trade 申请借款")
    @ApiImplicitParam(name = "applyLoanReq", value = "applyLoanReq", required = true,
            dataType = "ApplyLoanReq")
    @ApiResponse(response = BaseResponse.class, message = "baseResponse", code = 200)
    @PostMapping(value = "apply-loan")
    public BaseResponse<ApplyLoanResponse> applyLoan(@RequestBody ApplyLoanReq applyLoanReq) {
        logger.info("apply-loan请求交易参数:{}", JSON.toJSONString(applyLoanReq));
        BaseResponse<ApplyLoanResponse> baseResponse = new BaseResponse<>();
        if (applyLoanReq.paramCheck(baseResponse)) {
            try {
                EventInfo event = new EventInfo();
                event.put("userId", applyLoanReq.getUserId());
                event.setEventType("trade_loan_apply");
                CatUtils.info(event);
                baseResponse = loanService.applyLoan(applyLoanReq);
            } catch (ServiceException se) {
                logger.error("service/loan/apply-loan  has  exception :{}", se);
                baseResponse.setCode(BaseResponse.ResponseCode.BIZ_CHECK_FAIL.getValue());
                baseResponse.setMsg(se.getMsg());
            } catch (Exception e) {
                logger.error("service/loan/apply-loan  has  exception :{}", e);
                baseResponse.systemError();
            }
        }
        return baseResponse;
    }



    @ApiOperation(value = "trade_loan_confirm", httpMethod = "POST",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE, notes = "trade 确认借款")
    @ApiImplicitParam(name = "confirmLoanReq", value = "confirmLoanReq", required = true,
            dataType = "ConfirmLoanReq")
    @ApiResponse(response = BaseResponse.class, message = "baseResponse", code = 200)
    @PostMapping(value = "confirm-loan")
    public BaseResponse<ConfirmLoanResponse> confirm(@RequestBody ConfirmLoanReq confirmLoanReq) {
        logger.info("调用trade-app的confirm-loan接口时的请求交易参数为:{}",JSON.toJSONString(confirmLoanReq));
        BaseResponse<ConfirmLoanResponse> baseResponse = new BaseResponse<>();
        if (confirmLoanReq.paramCheck(baseResponse)) {
            try {
                EventInfo event = new EventInfo();
                event.put("userId", confirmLoanReq.getUserId());
                event.put("traceNo", confirmLoanReq.getTraceNo());
                event.setEventType("trade_loan_confirm");
                CatUtils.info(event);
                baseResponse = loanService.confirmLoanOrder(confirmLoanReq);
            }catch(IdempotentException ie){
                return baseResponse;
            } catch (Exception e) {
                logger.error("service/loan/confirm-loan  has  exception :{}", e);
                loanService.orderSendMq(confirmLoanReq.getUserId() ,
                                        confirmLoanReq.getTraceNo() , confirmLoanReq.getProductCategory(),
                                        confirmLoanReq.getOrderAmount().multiply(new BigDecimal(100)).intValue(),
                                        QueueConst.RETURN_QUOTA);

                baseResponse.systemError();
            }
        }
        return baseResponse;
    }

    @PostMapping("users/loan-orders")
    @ApiOperation(value = "loan-orders",httpMethod = "POST",notes = "获取我的订单")
    @ApiImplicitParam(name = "req" ,value = "还款订单",required = true,dataType = "LoanDetailReq")
    @ApiResponse(response = BaseResponse.class,message = "baseResponse",code = 200)
    public BaseResponse<PageInfo<LoanOrderDto>> getMyOrders(@RequestBody LoanDetailReq req) {

        BaseResponse<PageInfo<LoanOrderDto>> response = new BaseResponse<>();
        //校验入参是否非空
        if(!req.paramCheck(response)){
            return response;
        }
        try {
            // cat 日志收集
            EventInfo eventInfo = new EventInfo();
            eventInfo.put("userId", req.getUserId());
            eventInfo.setEventType("users-loan-orders");
            CatUtils.info(eventInfo);
            PageInfo<LoanOrderDto> pageInfo = loanOrderService.conditionalPaging(req);
            response.setData(pageInfo);
            eventInfo.setMessage("user orders is {"+req.toString()+"}");
            // cat日志收集结束
            eventInfo.endSend();
        }catch (Exception e){
            logger.error("查询用户订单列表失败",e);
            response.systemError();
        }
        return  response;
    }
    @PostMapping("users/loan-orders/detail")
    @ApiOperation(value = "loan-orders/detail",httpMethod = "POST",notes = "获取我的订单详情")
    @ApiImplicitParam(name = "req" ,value = "还款订单",required = true,dataType = "RepaymentDetailReq")
    @ApiResponse(response = BaseResponse.class,message = "baseResponse",code = 200)
    public BaseResponse<LoanOrder> getLoanOrderDetail(@RequestBody RepaymentDetailReq req) {
        BaseResponse<LoanOrder> response=new BaseResponse<>();
        logger.info("查询订单入参："+req.toString());
        //校验入参是否非空
        if(!req.paramCheck(response)){
            return response;
        }
        try {
            LoanOrder order = new LoanOrder();
            order.setId(req.getLoanId());
            order.setUserId(req.getUserId());
            order.setMerchantNo(req.getMerchantNo());
            response.setData(loanOrderService.getByUserIdAndOrderId(order));
        } catch (Exception e) {
            logger.error("查询借款单详情出错:", e);
            response=new BaseResponse<>();
            response.systemError();
        }
        return  response;
    }
    @PostMapping("users/orders/traces")
    @ApiOperation(value = "orders/traces",httpMethod = "POST",notes = "换取订单进度")
    @ApiImplicitParam(name = "req" ,value = "还款订单",required = true,dataType = "RepaymentDetailReq")
    @ApiResponse(response = BaseResponse.class,message = "baseResponse",code = 200)
    public BaseResponse<List<Trace>> getTraces(@RequestBody RepaymentDetailReq req) {
        BaseResponse<List<Trace>> response = new BaseResponse();
        //校验入参是否非空
        if(!req.paramCheck(response)){
            return response;
        }
        try {
            TraceDto dto = new TraceDto();
            dto.setLoanOrderId(req.getLoanId());
            dto.setUserId(req.getUserId());
            response.setData(traceService.selectByUserIdAndOrderId(dto));
        }catch (Exception e){
            logger.error("查询traces列表出错",e);
            response.systemError();
        }
        return response;
    }



    @PostMapping(value = "/last-loan-order")
    public BaseResponse<IndexLoanOrderDto> getUserLastLoanOrder(@RequestBody LoanOrderDto loanOrderDto){
        BaseResponse<IndexLoanOrderDto> resp=new BaseResponse<>();
        try {
            resp.setData(loanOrderService.getUserLastLoanOrder(loanOrderDto));
        } catch (Exception e) {
            logger.error("/service/loan/last-loan-order error",e);
            resp.systemError();
        }
        return resp;
    }




    @GetMapping(value = "/loan/user-loan-orders")
    @ApiOperation(value = "get  loan order, return list object or null", notes = "查询最近一次被拒绝的用户借款订单和当前带审核状态的订单")
    public BaseResponse<LoanOrderRiskDto> getUserLoanOrders(@RequestParam("userId") Long userId){
        BaseResponse<LoanOrderRiskDto> response = new BaseResponse<>();
        try {
            LoanOrderRiskDto loanOrderRiskDto =  new LoanOrderRiskDto();
            LoanOrder lastRejectLoanOrder = loanService.selectRejectOrder(userId);
            LoanOrder currentApplyLoanOrder = loanService.selectReviewOrder(userId);
            loanOrderRiskDto.setCurrentApplyLoanOrder(currentApplyLoanOrder);
            loanOrderRiskDto.setLastRejectLoanOrder(lastRejectLoanOrder);
            response.setData(loanOrderRiskDto);
            return response;
        } catch (IdempotentException e) {
            logger.error("查询借款订单数据失败",e);
            response.systemError();
        }catch(Exception e){
            logger.error("查询借款订单数据失败",e);
            response.systemError();
        }
        return response;
    }

    @GetMapping(value = "/loan/get-loan-order-by-id")
    @ApiOperation(value = "get  loan order, return Order or null", notes = "根据订单Id查询订单")
    public BaseResponse<LoanOrder> getLoanOrderById(@RequestParam("id") Long id){
        BaseResponse<LoanOrder> response = new BaseResponse<>();
        try {
            LoanOrder loanOrder = loanService.selectLoanOrderById(id);
            response.setData(loanOrder);
            if(null==loanOrder){
                response.setCode(ResultMsg.RESULT_FAIL);
                response.setMsg("No Order");
            }
            return response;
        } catch (IdempotentException e) {
            logger.error("查询借款订单数据失败",e);
            response.systemError();
        }catch(Exception e){
            logger.error("查询借款订单数据失败",e);
            response.systemError();
        }
        return response;
    }

    @PostMapping(value = "/get-manual-review-loan-order")
    @ApiOperation(value = "get manual review loan order, return List or null", notes = "查询人工审核中的订单")
    public BaseResponse<PageInfo<ManualReviewLoanOrderDto>> getManualReviewLoanOrderList(@RequestBody PageReq pageReq){
        BaseResponse<PageInfo<ManualReviewLoanOrderDto>> response = new BaseResponse<>();
        //校验入参是否非空
        if(!pageReq.paramCheck(response)){
            return response;
        }
        try {
            // cat 日志收集
            EventInfo eventInfo = new EventInfo();
            eventInfo.setEventType("get_manual_review_order_list");
            CatUtils.info(eventInfo);
            PageInfo<ManualReviewLoanOrderDto>  list = loanService.selectManualReviewLoanOrderList(pageReq);
            response.setData(list);
            return response;
        } catch(Exception e){
            logger.error("查询人工审核中的订单失败",e);
            response.systemError();
        }
        return response;
    }

    @PostMapping(value = "/update-manual-status")
    @ApiOperation(value = "update manual review status", notes = "人工审核成功或失败 状态修改")
    public BaseResponse<LoanBaseResponse> updateManualReviewStatus(@RequestBody ManualReviewStatusReq manualReviewStatusReq){
        BaseResponse<LoanBaseResponse> response = new BaseResponse<>();
        //校验入参是否非空
        if(!manualReviewStatusReq.paramCheck(response)){
            return response;
        }
        try {
            // cat 日志收集
            EventInfo eventInfo = new EventInfo();
            eventInfo.put("id", manualReviewStatusReq.getBizSeqNo());
            eventInfo.setEventType("update_manual_review_status");
            CatUtils.info(eventInfo);
            loanService.updateManualReviewStatus(manualReviewStatusReq);
            return response;
        } catch(Exception e){
            logger.error("查询人工审核中的订单失败",e);
            response.serviceError(e.getMessage());
        }
        return response;
    }

    @GetMapping("get-user-id-by-biz-seq-no")
    public BaseResponse<Long>  getUserIdByOrderBizSeqNo(String bizSeqNo){
        BaseResponse<Long> response = new BaseResponse<Long>();
        try {
            response.setData(loanOrderService.getUserIdByOrderBizSeqNo(bizSeqNo));
        }catch (Exception e){
            response.systemError();
        }
        return response;
    }

    @PostMapping(value = "/get-unfreeze-orders")
    @ApiOperation(value = "get unfreeze orders by productid", notes = "人工审核失败或风控审核失败 状态订单查询")
    public BaseResponse<PageInfo<UnfreezeOrdersResponse>> getUnfreezeOrderList(@RequestBody UnfreezeOrdersReq unfreezeOrdersReq){
        BaseResponse<PageInfo<UnfreezeOrdersResponse>> response = new BaseResponse<>();
        //校验入参是否非空
        if(!unfreezeOrdersReq.paramCheck(response)){
            return response;
        }
        try {
            // cat 日志收集
            EventInfo eventInfo = new EventInfo();
            eventInfo.put("id", unfreezeOrdersReq.getId());
            eventInfo.setEventType("get unfreeze orders");
            CatUtils.info(eventInfo);
            response.setData(loanOrderService.getUnfreezeOrderList(unfreezeOrdersReq));
            return response;
        } catch(Exception e){
            logger.error("查询审核失败或风控失败的订单失败",e);
            response.serviceError(e.getMessage());
        }
        return response;
    }
}
