package com.xianjinxia.trade.platform.controller;

import com.dianping.cat.common.EventInfo;
import com.dianping.cat.utils.CatUtils;
import com.xianjinxia.trade.app.response.BaseResponse;
import com.xianjinxia.trade.app.response.BaseResponse.ResponseCode;
import com.xianjinxia.trade.platform.dto.CheckLoanRequest;
import com.xianjinxia.trade.platform.dto.LoanOrderDetailDto;
import com.xianjinxia.trade.platform.dto.TraceDto;
import com.xianjinxia.trade.platform.dto.TrdPlatformLoanApplyDto;
import com.xianjinxia.trade.platform.request.CancelOrderReq;
import com.xianjinxia.trade.platform.request.LoanApplyRecallRequest;
import com.xianjinxia.trade.platform.request.LoanApplyRequest;
import com.xianjinxia.trade.platform.request.LoanOrderDetailReq;
import com.xianjinxia.trade.platform.request.PlatformConfirmLoanRequest;
import com.xianjinxia.trade.platform.request.TraceReq;
import com.xianjinxia.trade.platform.request.UserOrderRequest;
import com.xianjinxia.trade.platform.response.CheckLoanResponse;
import com.xianjinxia.trade.platform.response.UserOrderResp;
import com.xianjinxia.trade.platform.service.PlatformLoanOrderService;
import com.xianjinxia.trade.shared.exception.IdempotentException;
import com.xianjinxia.trade.shared.exception.ServiceException;
import com.xianjinxia.trade.shared.page.PageObj;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/service/platform")
public class PlatformLoanOrderController {

    private static final Logger logger = LoggerFactory.getLogger(DispatcherController.class);

    @Autowired
    private PlatformLoanOrderService platformLoanOrderService;

    /**
     * 借款申请 - 发起借款申请请求
     *
     * @param loanApplyRequest 借款申请请求对象{@link com.xianjinxia.trade.platform.request.LoanApplyRequest}
     *
     * @return 借款申请的内部请求结果（此时还未推送给合作机构） {@link com.xianjinxia.trade.platform.dto.TrdPlatformLoanApplyDto}
     */
    @RequestMapping(value = "/loan-apply", method = RequestMethod.POST)
    public BaseResponse<TrdPlatformLoanApplyDto> loanApply(@RequestBody LoanApplyRequest loanApplyRequest) {
        BaseResponse<TrdPlatformLoanApplyDto> response = new BaseResponse<>();
        try {
            if (!loanApplyRequest.paramCheck(response)) {
                return response;
            }
            TrdPlatformLoanApplyDto loanApplyResultDto = platformLoanOrderService.loanApply(loanApplyRequest);
            response.setData(loanApplyResultDto);
        } catch (ServiceException se) {
            logger.error("biz check error {}", se.toString());
            response.setCode(ResponseCode.BIZ_CHECK_FAIL.getValue());
            response.setMsg(se.getMsg());
        } catch (Exception e) {
            logger.error("system error {}", e);
            response.systemError();
        }
        return response;
    }

    /**
     * 借款申请 - 接收借款申请推送结果，由内部服务loanark-app调用
     *
     * @param loanApplyRecallRequest 借款申请接收请求结果的对象{@link com.xianjinxia.trade.platform.request.LoanApplyRecallRequest}
     *
     * @return 内部服务的响应对象, 当code为"00"表示成功,其余为失败 {@link com.xianjinxia.trade.app.response.BaseResponse}
     */
    @RequestMapping(value = "/loan-apply-recall", method = RequestMethod.POST)
    public BaseResponse<Void> loanApplyReceive(@RequestBody LoanApplyRecallRequest loanApplyRecallRequest) {
        BaseResponse<Void> response = new BaseResponse();
        try {
            platformLoanOrderService.loanApplyCallback(loanApplyRecallRequest.getOrderNo(), loanApplyRecallRequest.getPushResult(),loanApplyRecallRequest.getBizResult());
        } catch (ServiceException se) {
            logger.error("biz check error {}", se);
            response.setCode(ResponseCode.BIZ_CHECK_FAIL.getValue());
            response.setMsg(se.getMsg());
        } catch (Exception e) {
            logger.error("system error {}", e);
            response.systemError();
        }
        return response;
    }

    /**
     * 查询未推送成功的借款申请订单，系统默认会重试3次推送给第三方
     * <p>
     * 每次推送的时retry_times递增加1
     * <p>
     * 推送3次（retry_times=3）都失败的情况下，订单状态将会变为"推送失败"（终态）, "推送失败"的订单将不再返回给调用方
     *
     * @return 获取未推送成功的借款申请订单 {@link com.xianjinxia.trade.platform.dto.TrdPlatformLoanApplyDto}
     */
    @RequestMapping(value = "/loan-apply-retry-list", method = RequestMethod.GET)
    public BaseResponse<List<TrdPlatformLoanApplyDto>> getLoanApplyRetryList() {
        BaseResponse<List<TrdPlatformLoanApplyDto>> response = new BaseResponse<>();
        try {
            List<TrdPlatformLoanApplyDto> loanOrderApplyRetryList = platformLoanOrderService.getLoanOrderApplyRetryList();
            response.setData(loanOrderApplyRetryList);
        } catch (ServiceException se) {
            logger.error("biz check error {}", se);
            response.setCode(ResponseCode.BIZ_CHECK_FAIL.getValue());
            response.setMsg(se.getMsg());
        } catch (Exception e) {
            logger.error("system error {}", e);
            response.systemError();
        }
        return response;
    }


    /**
     * 用户查询订单接口
     *
     * @param userOrderRequest 查询参数对象
     *
     * @return
     */
    @PostMapping("/user/orders")
    public BaseResponse<PageObj<UserOrderResp>> userOrders(@RequestBody UserOrderRequest userOrderRequest) {
        BaseResponse<PageObj<UserOrderResp>> response = new BaseResponse<>();
        try {
            EventInfo eventInfo = new EventInfo();
            eventInfo.setEventType("searchUserOrders");
            eventInfo.put("userId", userOrderRequest.getUserId());
            CatUtils.info(eventInfo);
            response.setData(platformLoanOrderService.getUserOrders(userOrderRequest));
        } catch (Exception e) {
            logger.error("/service/paltform/user/orders error", e);
            response.systemError();
        }
        return response;
    }

    /**
     * 订单确认接口
     *
     * @param confirmLoanReq
     *
     * @return
     */
    @PostMapping("/confirm-loan")
    public BaseResponse<Void> confirmLoan(@RequestBody PlatformConfirmLoanRequest confirmLoanReq) {
        BaseResponse<Void> response = new BaseResponse<>();
        try {
            EventInfo eventInfo = new EventInfo();
            eventInfo.setEventType("confirmLoan");
            eventInfo.setMessage("receive confirmLoan request");
            eventInfo.put("orderNo", confirmLoanReq.getOrderNo());
            CatUtils.info(eventInfo);

            if (!confirmLoanReq.paramCheck(response)) {
                return response;
            }

            platformLoanOrderService.confirmLoan(confirmLoanReq);
        } catch (IdempotentException e) {
            logger.info("IdempotentException", e);
        }catch(ServiceException e){
            logger.error("/service/paltform/confirm-loan error,msg:{}",e.getMsg(),e);
            response.serviceError(e.getMsg());
        }catch(Exception e) {
            logger.error("/service/paltform/confirm-loan error,msg:{}",e.getMessage(), e);
            response.systemError();
        }
        return response;

    }

    /**
     * 查询订单详情，给loanark-web调用
     *
     * @param loanOrderDetailReq
     *
     * @return
     */
    @PostMapping("/order/detail")
    public BaseResponse<LoanOrderDetailDto> orderDetail(@RequestBody LoanOrderDetailReq loanOrderDetailReq) {
        logger.info("orderDetail方法调用 入参为：{}", loanOrderDetailReq);
        BaseResponse<LoanOrderDetailDto> response = new BaseResponse<LoanOrderDetailDto>();
        try {
            if (loanOrderDetailReq.paramCheck(response)) {
                LoanOrderDetailDto loanOrderDetail = platformLoanOrderService.getLoanOrderDetail(loanOrderDetailReq);
                response.setData(loanOrderDetail);
            }
        }catch(IdempotentException ide) {
        	logger.error("IdempotentException", ide);
        	return response;
        }catch (ServiceException se) {
            logger.error("/service/paltform/order/detail error", se);
            response = new BaseResponse<LoanOrderDetailDto>(ResponseCode.SYS_ERROR.getValue(), se.getMessage());
        }catch(Exception e) {
        	logger.error("/service/paltform/order/detail error", e);
            response.systemError();
        }
        return response;
    }


    /**
     * 查询订单进度，给loanark-web调用
     *
     * @param traceReq
     *
     * @return
     */
    @PostMapping("/order/trace")
    public BaseResponse<List<TraceDto>> orderTrace(@RequestBody TraceReq traceReq) {
        logger.info("orderTrace方法调用 入参为：{}", traceReq);
        BaseResponse<List<TraceDto>> response = new BaseResponse<>();
        try {
            if (traceReq.paramCheck(response)) {
                List<TraceDto> traceList = platformLoanOrderService.getTrace(traceReq);
                response.setData(traceList);
            }
        } catch (Exception e) {
            logger.error("/service/paltform/order/trace error", e);
            response.systemError();
        }
        return response;
    }


    /**
     * 查询这个订单是否属于这个用户，给loanark-web调用
     *
     * @param userId * @param orderNo
     *
     * @return
     */
    @GetMapping("/order/orderBelongToUser")
    public BaseResponse<Boolean> orderBelongToUser(@RequestParam("userId") Long userId, @RequestParam("orderNo") String orderNo) {
        logger.info("orderBelongToUser方法调用 入参为：{},{}", userId, orderNo);
        BaseResponse<Boolean> response = new BaseResponse<Boolean>();
        try {
            if (userId != null && !StringUtils.isEmpty(orderNo)) {
                Boolean result = platformLoanOrderService.orderIsBelongToUser(orderNo, userId);
                response.setData(result);
            }
        } catch (Exception e) {
            logger.error("/service/paltform/order/orderBelongToUser  error", e);
            response.systemError();
        }
        return response;
    }


    /**
     * 取消订单，给loanark-web调用
     *
     * @param cancelOrderReq
     *
     * @return
     */
    @PostMapping("/order/cancel")
    public BaseResponse<Void> cancelOrder(@RequestBody CancelOrderReq cancelOrderReq) {
        BaseResponse<Void> response = new BaseResponse<>();
        try {
            if (cancelOrderReq.paramCheck(response)) {
                platformLoanOrderService.cancelOrder(cancelOrderReq);
            }
          }catch(IdempotentException ide) {
        	logger.error("IdempotentException", ide);
        	return response;
         }catch (ServiceException se) {
            logger.error("/service/paltform/order/cancel error", se);
            response = new BaseResponse<Void>(ResponseCode.SYS_ERROR.getValue(), se.getMessage());
        }catch(Exception e) {
        	logger.error("/service/paltform/order/cancel error", e);
            response.systemError();
        }
        return response;
    }

    @PostMapping("/check-loan")
    public BaseResponse<CheckLoanResponse> checkLoan(@RequestBody CheckLoanRequest checkLoanRequest) {
        BaseResponse<CheckLoanResponse> response = new BaseResponse<>();
        try {
            if (!checkLoanRequest.paramCheck(response)) {
                return response;
            }
            response.setData(platformLoanOrderService.checkLoan(checkLoanRequest));
        } catch (Exception e) {
            logger.error("/service/platform/checkLoan error", e);
            response.systemError();
        }
        return response;
    }

}
