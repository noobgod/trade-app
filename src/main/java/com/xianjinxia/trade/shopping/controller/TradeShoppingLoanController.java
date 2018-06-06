package com.xianjinxia.trade.shopping.controller;


import com.dianping.cat.common.EventInfo;
import com.dianping.cat.utils.CatUtils;
import com.github.pagehelper.PageInfo;
import com.xianjinxia.trade.app.dto.IndexLoanOrderDto;
import com.xianjinxia.trade.app.dto.LoanOrderDto;
import com.xianjinxia.trade.app.request.LoanDetailReq;
import com.xianjinxia.trade.app.response.BaseResponse;
import com.xianjinxia.trade.app.response.BaseResponse.ResponseCode;
import com.xianjinxia.trade.app.response.mqapp.ResultMsg;
import com.xianjinxia.trade.app.service.ITraceService;
import com.xianjinxia.trade.shared.domain.TrdShoppingUserAddr;
import com.xianjinxia.trade.shopping.dto.ShoppingLoanOrderContractDto;
import com.xianjinxia.trade.shared.exception.IdempotentException;
import com.xianjinxia.trade.shared.exception.ServiceException;
import com.xianjinxia.trade.shared.service.LoanApplyChecker;
import com.xianjinxia.trade.shopping.dto.ShoppingLoanOrderDto;
import com.xianjinxia.trade.shopping.dto.ShoppingTraceDto;
import com.xianjinxia.trade.shopping.dto.UserReceiveAddressDto;
import com.xianjinxia.trade.shopping.request.app.ShoppingConfirmLoanReq;
import com.xianjinxia.trade.shopping.request.app.ShoppingConfirmReceiptLoanReq;
import com.xianjinxia.trade.shopping.request.app.ShoppingLoanOrderReq;
import com.xianjinxia.trade.shopping.request.app.ShoppingLogisticsOrderReq;
import com.xianjinxia.trade.shopping.request.app.ShoppingTraceReq;
import com.xianjinxia.trade.shopping.request.app.UserReceiveAddressDetailReq;
import com.xianjinxia.trade.shopping.request.app.UserReceiveAddressReq;
import com.xianjinxia.trade.shopping.response.bgd.ConfirmLoanOrderDetailResponse;
import com.xianjinxia.trade.shopping.response.bgd.ReceiveLoanOrderDetailResponse;
import com.xianjinxia.trade.shopping.service.IShoppingLoanService;
import com.xianjinxia.trade.shopping.service.IShoppingUserAddrService;
import com.xianjinxia.trade.shopping.service.ITrdShoppingLoanOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author chunliny
 * <p>
 * 2018年01月16日
 */
@Api(tags = "trade_app shopping loan controller")
@RestController
@RequestMapping("/service/shopping")
public class TradeShoppingLoanController {
	
    private static final Logger logger = LoggerFactory.getLogger(TradeShoppingLoanController.class);
	
    @Autowired
    private IShoppingLoanService shoppingLoanOrderService;

    @Autowired
    private ITrdShoppingLoanOrderService trdShoppingLoanOrderService;

    @Autowired
    private ITraceService traceService;
    
    @Autowired
    private IShoppingUserAddrService shoppingUserAddrService;

    @Autowired
    private LoanApplyChecker loanApplyChecker;

    /**
     * 立即抢购时,生成的 traceNo
     * @param shoppingTraceReq
     * @return
     */
	@PostMapping("/trace")
	public BaseResponse<ShoppingTraceDto> createTrace(@RequestBody ShoppingTraceReq shoppingTraceReq){
    	logger.info("请求交易参数:" + shoppingTraceReq);
        BaseResponse<ShoppingTraceDto> baseResponse = new BaseResponse<ShoppingTraceDto>();
  		if (shoppingTraceReq.paramCheck(baseResponse)) {
  			try {
  				EventInfo event = new EventInfo();
  				event.put("userId", shoppingTraceReq.getUserId());
  				event.setEventType("trade-shopping-trace");
  				CatUtils.info(event);
  				ShoppingTraceDto shoppingTraceDto = traceService.createTrace(shoppingTraceReq);
  				baseResponse.setData(shoppingTraceDto);
  			}catch (ServiceException se) {
  	            logger.error("biz check error {}", se.toString());
  	            baseResponse.setCode(ResponseCode.BIZ_CHECK_FAIL.getValue());
  	            baseResponse.setMsg(se.getMsg());
  	        }catch (Exception e) {
  				logger.error("service/shopping/trace  has  exception :{}", e);
  				baseResponse.systemError();
  			}
  		}
        return baseResponse;
	}


    /**
     * 立即申请时，订单入库
     * @param shoppingLoanOrderReq
     * @return
     */
	@PostMapping("/loan-apply")
    public BaseResponse<ShoppingLoanOrderDto> loanApply(@RequestBody ShoppingLoanOrderReq shoppingLoanOrderReq) {
        logger.info("请求交易参数:" + shoppingLoanOrderReq);
        BaseResponse<ShoppingLoanOrderDto> baseResponse = new BaseResponse<>();
		
			try {
				if (shoppingLoanOrderReq.paramCheck(baseResponse)
						&& shoppingLoanOrderReq.getShoppingProductDto()
								.paramCheck(baseResponse)
						&& shoppingLoanOrderReq.getUserBankCardDto()
								.paramCheck(baseResponse)) {
				EventInfo event = new EventInfo();
				event.put("userId", shoppingLoanOrderReq.getUserId());
				event.setEventType("trade-shopping-loan-apply");
				CatUtils.info(event);
				ShoppingLoanOrderDto shoppingLoanOrderDto = shoppingLoanOrderService.applyLoan(shoppingLoanOrderReq);
				
				baseResponse.setData(shoppingLoanOrderDto);
				}
			}catch (ServiceException se) {
	            logger.error("biz check error {}", se);
	            baseResponse.setCode(ResponseCode.BIZ_CHECK_FAIL.getValue());
	            baseResponse.setMsg(se.getMsg());
	        }catch (Exception e) {
				logger.error("service/shopping/loan-apply  has  exception :{}", e);
				baseResponse.systemError();
			}
        return baseResponse;
    }
    
    /**
     * 确认申请时，更新订单状态、冻结编号
     * @param shoppingConfirmLoanReq
     * @return
     */
	@PostMapping("/loan-confirm")
    public BaseResponse<Void> confirmLoan(@RequestBody ShoppingConfirmLoanReq shoppingConfirmLoanReq) {
        logger.info("请求交易参数:" + shoppingConfirmLoanReq);
        BaseResponse<Void> baseResponse = new BaseResponse<Void>();
		if (shoppingConfirmLoanReq.paramCheck(baseResponse)) {
			try {
				EventInfo eventInfo = new EventInfo();
	            eventInfo.setEventType("trade-shopping-confirm-loan");
	            eventInfo.setMessage("receive shopping confirmLoan request");
	            eventInfo.put("shoppingLoanOrderId", shoppingConfirmLoanReq.getShoppingLoanOrderId());
	            CatUtils.info(eventInfo);
				shoppingLoanOrderService.confirmLoan(shoppingConfirmLoanReq);
			}catch (ServiceException se) {
	            logger.error("biz check error {}", se.toString());
	            baseResponse.setCode(ResponseCode.BIZ_CHECK_FAIL.getValue());
	            baseResponse.setMsg(se.getMsg());
	        }catch (Exception e) {
				logger.error("service/shopping/confirmLoan  has  exception :{}", e);
				baseResponse.systemError();
			}
		}
        return baseResponse;
    }
    
    /**
     * 查询订单详情
     * @param shoppingLoanOrderId
     * @return
     */
    @GetMapping("/order-detail-receive")
    public BaseResponse<ReceiveLoanOrderDetailResponse> getReceivePageOrderDetail(@RequestParam("shoppingLoanOrderId") Long shoppingLoanOrderId) {
        logger.info("orderDetail方法调用 入参为：{}", shoppingLoanOrderId);
        BaseResponse<ReceiveLoanOrderDetailResponse> baseResponse = new BaseResponse<>();
        try {

			EventInfo eventInfo = new EventInfo();
			eventInfo.setEventType("trade-shopping-order-detail");
			eventInfo.put("shoppingLoanOrderId", shoppingLoanOrderId);
			CatUtils.info(eventInfo);

			ReceiveLoanOrderDetailResponse orderDetail = shoppingLoanOrderService.getOrderDetailForReceive(shoppingLoanOrderId);
			baseResponse.setData(orderDetail);
        }catch (ServiceException se) {
        	 baseResponse.setCode(ResponseCode.BIZ_CHECK_FAIL.getValue());
	         baseResponse.setMsg(se.getMsg());
        }catch(Exception e) {
        	logger.error("/service/shopping/order-detail error", e);
        	baseResponse.systemError();
        }
        return baseResponse;
    }

	/**
	 * 查询订单详情
	 * @param shoppingLoanOrderId
	 * @return
	 */
	@GetMapping("/order-detail-comfirm")
	public BaseResponse<ConfirmLoanOrderDetailResponse> getConfirmPageOrderDetail(@RequestParam("shoppingLoanOrderId") Long shoppingLoanOrderId) {
		logger.info("orderDetail方法调用 入参为：{}", shoppingLoanOrderId);
		BaseResponse<ConfirmLoanOrderDetailResponse> baseResponse = new BaseResponse<>();
		try {

			EventInfo eventInfo = new EventInfo();
			eventInfo.setEventType("trade-shopping-order-detail");
			eventInfo.put("shoppingLoanOrderId", shoppingLoanOrderId);
			CatUtils.info(eventInfo);

			ConfirmLoanOrderDetailResponse orderDetailForConfirm = shoppingLoanOrderService.getOrderDetailForConfirm(shoppingLoanOrderId);
			baseResponse.setData(orderDetailForConfirm);
		}catch (ServiceException se) {
			baseResponse.setCode(ResponseCode.BIZ_CHECK_FAIL.getValue());
			baseResponse.setMsg(se.getMsg());
		}catch(Exception e) {
			logger.error("/service/shopping/order-detail error", e);
			baseResponse.systemError();
		}
		return baseResponse;
	}

    
    /**
     * 订单物流发货
     * @return
     */
    @PostMapping("/loan-logistics-shipment")
    public BaseResponse<Void> logisticsShipmentLoan(@RequestBody ShoppingLogisticsOrderReq shoppingLogisticsOrderReq){
    	logger.info("orderShipment方法调用 入参为：{}", shoppingLogisticsOrderReq);
        BaseResponse<Void> baseResponse = new BaseResponse<>();
		if (shoppingLogisticsOrderReq.paramCheck(baseResponse)) {
			try {
				EventInfo eventInfo = new EventInfo();
				eventInfo.setEventType("trade-shopping-logisticsShipmentLoan");
				eventInfo.put("shoppingLoanOrderId", shoppingLogisticsOrderReq.getLogisticsOrderId());
				CatUtils.info(eventInfo);
				shoppingLoanOrderService.logisticsShipmentLoan(shoppingLogisticsOrderReq);
			} catch (ServiceException se) {
				baseResponse.setCode(ResponseCode.BIZ_CHECK_FAIL.getValue());
				baseResponse.setMsg(se.getMsg());
			} catch (Exception e) {
				logger.error("/service/shopping/logisticsShipmentLoan error", e);
				baseResponse.systemError();
			}
		}
        return baseResponse;
    }
    
    /**
     * 确认收货
     * @param shoppingConfirmReceiptLoanReq
     * @return
     */
    @PostMapping("/loan-confirm-receipt")
    public BaseResponse<Void> confirmReceiptLoan(@RequestBody ShoppingConfirmReceiptLoanReq shoppingConfirmReceiptLoanReq){
    	logger.info("orderShipment方法调用 入参为：{}", shoppingConfirmReceiptLoanReq);
        BaseResponse<Void> baseResponse = new BaseResponse<Void>();
		if (shoppingConfirmReceiptLoanReq.paramCheck(baseResponse)) {
			try {
				EventInfo eventInfo = new EventInfo();
				eventInfo.setEventType("trade-shopping-confirmReceiptLoan");
				eventInfo.put("shoppingLoanOrderId", shoppingConfirmReceiptLoanReq.getShoppingLoanOrderId());
				CatUtils.info(eventInfo);
				shoppingLoanOrderService.confirmReceiptLoan(shoppingConfirmReceiptLoanReq);
			} catch (ServiceException se) {
				baseResponse.setCode(ResponseCode.BIZ_CHECK_FAIL.getValue());
				baseResponse.setMsg(se.getMsg());
			} catch (Exception e) {
				logger.error("/service/shopping/confirmReceiptLoan error", e);
				baseResponse.systemError();
			}
		}
        return baseResponse;
    }
    
    // 设置默认收货地址
 	@PostMapping("/setDefaultReceiveAddress")
 	public BaseResponse<Void> setDefaultReceiveAddress(@RequestParam("id") Long id, @RequestParam("userId") Long userId) {
 		BaseResponse<Void> resp = new BaseResponse<Void>();
 		try {
 			EventInfo eventInfo = new EventInfo();
 			eventInfo.setEventType("set-default-receive-address");
 			CatUtils.info(eventInfo);
 			shoppingUserAddrService.updateToDefault(userId, id);
 		} catch (Exception e) {
 			logger.error("/service/shopping/setDefaultReceiveAddress", e);
 			resp.systemError();
 		}
 		return resp;
 	}
    
    // 查询收货地址
 	@PostMapping("/getReceiveAddress")
 	public BaseResponse<List<UserReceiveAddressDto>> getReceiveAddress(@RequestParam("userId") Long userId) {
 		BaseResponse<List<UserReceiveAddressDto>> resp = new BaseResponse<List<UserReceiveAddressDto>>();
 		try {
 			EventInfo eventInfo = new EventInfo();
 			eventInfo.setEventType("get-receive-address");
 			CatUtils.info(eventInfo);
 			resp.setData(shoppingUserAddrService.getByUserId(userId));
 		} catch (Exception e) {
 			logger.error("/service/shopping/getReceiveAddress", e);
 			resp.systemError();
 		}
 		return resp;
 	}
 	
    // 查询收货地址
 	@PostMapping("/getDetailReceiveAddress")
 	public BaseResponse<UserReceiveAddressDto> getDetailReceiveAddress(@RequestParam("id") Long id) {
 		BaseResponse<UserReceiveAddressDto> resp = new BaseResponse<UserReceiveAddressDto>();
 		try {
 			EventInfo eventInfo = new EventInfo();
 			eventInfo.setEventType("detail-receive-address");
 			CatUtils.info(eventInfo);
 			resp.setData(shoppingUserAddrService.getById(id));
 		} catch (Exception e) {
 			logger.error("/service/shopping/getDetailReceiveAddress", e);
 			resp.systemError();
 		}
 		return resp;
 	}
 	
 	// 新增收货地址
 	@PostMapping("/addReceiveAddress")
 	public BaseResponse<Long> addReceiveAddress(@RequestBody UserReceiveAddressReq userReceiveAddressReq) {
		BaseResponse<Long> resp = new BaseResponse<Long>();
		if (userReceiveAddressReq.paramCheck(resp)) {
			try {
				EventInfo eventInfo = new EventInfo();
				eventInfo.setEventType("receive-add-address");
				CatUtils.info(eventInfo);
				TrdShoppingUserAddr trdShoppingUserAddr = new TrdShoppingUserAddr();
				BeanUtils.copyProperties(userReceiveAddressReq, trdShoppingUserAddr);
		    	trdShoppingUserAddr.setIsDefault(false);
		    	trdShoppingUserAddr.setId(null);
				resp.setData(shoppingUserAddrService.add(trdShoppingUserAddr));
			} catch (Exception e) {
				logger.error("/service/shopping/getReceiveAddress", e);
				resp.systemError();
			}
		}
 		return resp;
 	}
 	
 	// 修改收货地址
 	@PostMapping("/updateReceiveAddress")
 	public BaseResponse<Void> updateReceiveAddress(@RequestBody UserReceiveAddressDetailReq userReceiveAddressDetailReq) {
		BaseResponse<Void> resp = new BaseResponse<Void>();
		if (userReceiveAddressDetailReq.paramCheck(resp)) {
			try {
				EventInfo eventInfo = new EventInfo();
				eventInfo.setEventType("receive-update-address");
				CatUtils.info(eventInfo);
				TrdShoppingUserAddr trdShoppingUserAddr = new TrdShoppingUserAddr();
				BeanUtils.copyProperties(userReceiveAddressDetailReq, trdShoppingUserAddr);
		    	trdShoppingUserAddr.setId(userReceiveAddressDetailReq.getId());
				shoppingUserAddrService.updateById(trdShoppingUserAddr);
			} catch (Exception e) {
				logger.error("/service/shopping/updateReceiveAddress", e);
				resp.systemError();
			}
		}
 		return resp;
 	}
 	
 	// 删除收货地址
 	@PostMapping("/delReceiveAddress")
 	public BaseResponse<Void> delReceiveAddress(@RequestParam("id") Long id) {
 		BaseResponse<Void> resp = new BaseResponse<Void>();
 		try {
 			EventInfo eventInfo = new EventInfo();
 			eventInfo.setEventType("receive-address");
 			CatUtils.info(eventInfo);
 			shoppingUserAddrService.del(id);
 		} catch (Exception e) {
 			logger.error("/service/shopping/delReceiveAddress", e);
 			resp.systemError();
 		}
 		return resp;
 	}


	@PostMapping("/user/loan-orders")
	@ApiOperation(value = "loan-orders",httpMethod = "POST",notes = "获取我的订单")
	@ApiImplicitParam(name = "req" ,value = "还款订单",required = true,dataType = "LoanDetailReq")
	@ApiResponse(response = BaseResponse.class,message = "baseResponse",code = 200)
	public BaseResponse<PageInfo<LoanOrderDto>> getUserLoanOrders(@RequestBody LoanDetailReq req) {
		BaseResponse<PageInfo<LoanOrderDto>> response = new BaseResponse<>();
		//校验入参是否非空
		if(!req.paramCheck(response)){
			return response;
		}
		try {
			// cat 日志收集
			EventInfo eventInfo = new EventInfo();
			eventInfo.put("userId", req.getUserId());
			eventInfo.setEventType("users-shopping-loan-orders");
			CatUtils.info(eventInfo);
			PageInfo<LoanOrderDto> pageInfo = trdShoppingLoanOrderService.conditionalPaging(req);
			response.setData(pageInfo);
			eventInfo.setMessage("user shopping loan orders is {"+req.toString()+"}");
			// cat日志收集结束
			eventInfo.endSend();
		}catch (Exception e){
			logger.error("查询用户商城订单列表失败",e);
			response.systemError();
		}
		return  response;
	}


	@GetMapping(value = "/get-loan-order-by-id")
	@ApiOperation(value = "get  loan order, return Order or null", notes = "根据订单Id查询订单")
	public BaseResponse<ShoppingLoanOrderContractDto> getLoanOrderById(@RequestParam("id") Long id){
		BaseResponse<ShoppingLoanOrderContractDto> response = new BaseResponse<>();
		try {
			ShoppingLoanOrderContractDto loanContractDto = trdShoppingLoanOrderService.getLoanContractDto(id);
			response.setData(loanContractDto);
			if(null==loanContractDto){
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

	@PostMapping(value = "/last-loan-order")
	public BaseResponse<IndexLoanOrderDto> getUserLastLoanOrder(@RequestBody LoanOrderDto loanOrderDto){
		BaseResponse<IndexLoanOrderDto> resp=new BaseResponse<>();
		try {
			resp.setData(shoppingLoanOrderService.getUserLastLoanOrder(loanOrderDto));
		} catch (Exception e) {
			logger.error("/service/loan/last-loan-order error",e);
			resp.systemError();
		}
		return resp;
	}


	@GetMapping(value = "/has-unfinished-orders")
	public BaseResponse<Boolean> isUserHasUnFinishedOrders(@RequestParam Long userId){
		BaseResponse<Boolean> resp=new BaseResponse<>();
		try {
			resp.setData(loanApplyChecker.checkForSmallLoan(userId));
		} catch (Exception e) {
			logger.error("/service/loan/has-unfinished-orders",e);
			resp.systemError();
		}
		return resp;
	}
}
