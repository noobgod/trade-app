package com.xianjinxia.trade.platform.service;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xianjinxia.trade.app.response.BaseResponse;
import com.xianjinxia.trade.app.service.IMqMessageService;
import com.xianjinxia.trade.platform.dto.CheckLoanDto;
import com.xianjinxia.trade.platform.dto.CheckLoanRequest;
import com.xianjinxia.trade.platform.dto.LoanApplyMqDto;
import com.xianjinxia.trade.platform.dto.LoanOrderDetailDto;
import com.xianjinxia.trade.platform.dto.TraceDto;
import com.xianjinxia.trade.platform.dto.TrdPlatformLoanApplyDto;
import com.xianjinxia.trade.platform.remote.OpenApiRemoteService;
import com.xianjinxia.trade.platform.request.BackOrderRequest;
import com.xianjinxia.trade.platform.request.CancelOrderReq;
import com.xianjinxia.trade.platform.request.LoanApplyRequest;
import com.xianjinxia.trade.platform.request.LoanFeeDetailRequest;
import com.xianjinxia.trade.platform.request.LoanOrderDetailReq;
import com.xianjinxia.trade.platform.request.OpenApiConfirmLoanRequest;
import com.xianjinxia.trade.platform.request.PlatformConfirmLoanRequest;
import com.xianjinxia.trade.platform.request.TraceReq;
import com.xianjinxia.trade.platform.request.UserOrderRequest;
import com.xianjinxia.trade.platform.response.BackOrderResp;
import com.xianjinxia.trade.platform.response.CheckLoanResponse;
import com.xianjinxia.trade.platform.response.LoanOrderStatusResponse;
import com.xianjinxia.trade.platform.response.UserOrderResp;
import com.xianjinxia.trade.platform.status.LoanOrderStatus;
import com.xianjinxia.trade.platform.status.PlatformTraceOrderEventEnum;
import com.xianjinxia.trade.platform.task.ConfirmLoanTask;
import com.xianjinxia.trade.shared.constant.LoanOrderConstants;
import com.xianjinxia.trade.shared.constant.QueueConst;
import com.xianjinxia.trade.shared.domain.PlatformFeeDetail;
import com.xianjinxia.trade.shared.domain.Trace;
import com.xianjinxia.trade.shared.domain.TrdPlatformLoanOrder;
import com.xianjinxia.trade.shared.enums.IdempotentTypeEnum;
import com.xianjinxia.trade.shared.enums.LoanOrderSourceEnum;
import com.xianjinxia.trade.shared.enums.LoanOrderTypeEnum;
import com.xianjinxia.trade.shared.exception.ServiceException;
import com.xianjinxia.trade.shared.exception.SqlUpdateException;
import com.xianjinxia.trade.shared.idempotent.IdempotentService;
import com.xianjinxia.trade.shared.mapper.PlatformFeeDetailMapper;
import com.xianjinxia.trade.shared.mapper.TraceMapper;
import com.xianjinxia.trade.shared.mapper.TrdPlatformLoanOrderMapper;
import com.xianjinxia.trade.shared.page.PageObj;
import com.xianjinxia.trade.shared.remote.LoanarkAppRemoteService;
import com.xianjinxia.trade.shared.remote.OldCashmanRemoteService;
import com.xianjinxia.trade.shared.remote.dto.CashmanUserInfoDto;
import com.xianjinxia.trade.shared.remote.dto.ProductInfoDto;
import com.xianjinxia.trade.shared.remote.dto.UserInfoDto;
import com.xianjinxia.trade.shared.utils.AppUtil;
import com.xianjinxia.trade.shared.utils.BeanPropertyUtils;
import com.xianjinxia.trade.shared.utils.JsonUtils;
import com.xianjinxia.trade.shared.utils.MoneyUtil;
import com.xianjinxia.trade.shared.utils.UniqueIdUtil;
import com.xjx.mqclient.pojo.MqMessage;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Component
public class PlatformLoanOrderServiceImpl implements PlatformLoanOrderService {

    private static final Logger logger = LoggerFactory.getLogger(PlatformLoanOrderServiceImpl.class);

    @Autowired
    private TrdPlatformLoanOrderMapper trdPlatformLoanOrderMapper;

    @Autowired
    private TraceMapper traceMapper;

    @Autowired
    private LoanarkAppRemoteService loanarkAppRemoteService;

    @Autowired
    private OldCashmanRemoteService oldCashmanRemoteService;

    @Autowired
    private OpenApiRemoteService openApiRemoteService;

    @Autowired
    private IdempotentService idempotentService;

    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Autowired
    private PlatformFeeDetailMapper platformFeeDetailMapper;

    @Autowired
    private IMqMessageService mqMessageService;


    @Override
    @Transactional
    public TrdPlatformLoanApplyDto loanApply(LoanApplyRequest loanApplyRequest) {

        // 验证用户是否有未完成的订单
        List<TrdPlatformLoanOrder> trdPlatformLoanOrders = trdPlatformLoanOrderMapper.selectByUserId(loanApplyRequest.getUserId(), loanApplyRequest.getProductCode());
        for (Iterator<TrdPlatformLoanOrder> iterator = trdPlatformLoanOrders.iterator(); iterator.hasNext(); ) {
            TrdPlatformLoanOrder trdPlatformLoanOrder = iterator.next();
            if (!LoanOrderStatus.isAllowApplyStatus(trdPlatformLoanOrder.getStatus())){
                throw new ServiceException("您当前还有一笔金额未完成的订单, 产品名称:" +trdPlatformLoanOrder.getProductName());
            }
        }

        // 1.初始化TrdPlatformLoanOrder
        String traceNo = UniqueIdUtil.getTraceNoUniqueId();//创建unique trace no
        String orderNo = UniqueIdUtil.getLoanOrderUniqueId();//创建unique order no

        // 1.1 获取产品信息，并校验产品的最大限额
        ProductInfoDto productInfoDto = loanarkAppRemoteService.getProductInfoByCode(loanApplyRequest.getProductCode());
        if (productInfoDto.getMaxAmt().subtract(loanApplyRequest.getOrderAmount()).intValue() < 0){
            String errorMsg = String.format("订单金额[%d]已超出产品的最大限额[%d]", loanApplyRequest.getOrderAmount().intValue(),productInfoDto.getMaxAmt().intValue());
            throw new ServiceException(BaseResponse.ResponseCode.BIZ_CHECK_FAIL.getValue(), errorMsg);
        }

        if (productInfoDto.getMinAmt().subtract(loanApplyRequest.getOrderAmount()).intValue() > 0){
            String errorMsg = String.format("订单金额[%d]小于产品的最小限额[%d]", loanApplyRequest.getOrderAmount().intValue(),productInfoDto.getMinAmt().intValue());
            throw new ServiceException(BaseResponse.ResponseCode.BIZ_CHECK_FAIL.getValue(), errorMsg);
        }

        if (loanApplyRequest.getOrderAmount().doubleValue() % 100 != 0){
            String errorMsg = String.format("订单金额[%s]不是100的整数倍", loanApplyRequest.getOrderAmount().doubleValue());
            throw new ServiceException(BaseResponse.ResponseCode.BIZ_CHECK_FAIL.getValue(), errorMsg);
        }



        // 1.2 获取用户信息
        CashmanUserInfoDto userInfoDto = oldCashmanRemoteService.getUserInfoByUserId(loanApplyRequest.getUserId());
        // 不需要校验cashman用户的额度信息
//        if (userInfoDto.getAvailableAmt() < loanApplyRequest.getOrderAmount().intValue()){
//            String errorMsg = String.format("订单金额[%d]已超出用户的可借款额度[%d]", loanApplyRequest.getOrderAmount().intValue(),userInfoDto.getAvailableAmt());
//            throw new ServiceException(BaseResponse.ResponseCode.BIZ_CHECK_FAIL.getValue(), errorMsg);
//        }


        TrdPlatformLoanOrder trdPlatformLoanOrder = new TrdPlatformLoanOrder(loanApplyRequest.getUserId(), orderNo, MoneyUtil.changeYuanToCent(loanApplyRequest.getOrderAmount()), MoneyUtil.changeYuanToCent(loanApplyRequest.getFeeAmount()), MoneyUtil.changeYuanToCent(loanApplyRequest.getInterestAmount()), MoneyUtil.changeYuanToCent(loanApplyRequest.getPaymentAmount()), MoneyUtil.changeYuanToCent(loanApplyRequest.getRepaymentAmount()), loanApplyRequest.getApplyTerm(), productInfoDto.getName(), userInfoDto.getUserPhone(), userInfoDto.getRealname(), loanApplyRequest.getTerminal(), new Date(), traceNo, loanApplyRequest.getMerchantCode(), loanApplyRequest.getIsReloan(),loanApplyRequest.getApplyTermUnit().toString(), loanApplyRequest.getProductCode(),userInfoDto.getIdNumber());


        trdPlatformLoanOrderMapper.insert(trdPlatformLoanOrder);

        if(loanApplyRequest.getFeeDetails()!=null){
            for(LoanFeeDetailRequest feeDetail:loanApplyRequest.getFeeDetails()){
                platformFeeDetailMapper.insert(new PlatformFeeDetail(feeDetail.getFeeName(),feeDetail.getFeeType(),
                        feeDetail.getCollectMode(),feeDetail.getFeeAmount(),trdPlatformLoanOrder.getId()));
            }
        }
        logger.info("初始化借款申请订单成功，订单编号：{}", orderNo);

        // 2.新增trace
        Trace trace = new Trace(traceNo, PlatformTraceOrderEventEnum.PLATFORM_LOAN_APPLY_SUCCESS, new Date(), JSON.toJSONString(trdPlatformLoanOrder));
        traceMapper.insert(trace);


        // 3.发送队列通知loanark-app
        LoanApplyMqDto loanApplyMqDto = new LoanApplyMqDto();
        loanApplyMqDto.setUserId(trdPlatformLoanOrder.getUserId());
        loanApplyMqDto.setServiceCompany(trdPlatformLoanOrder.getServiceCompany());
        loanApplyMqDto.setProductCode(trdPlatformLoanOrder.getProductCode());
        loanApplyMqDto.setOrderNo(trdPlatformLoanOrder.getOrderNo());
        loanApplyMqDto.setApplyAmount(trdPlatformLoanOrder.getOrderAmount().longValue());
        MqMessage mqMessage = new MqMessage();
        mqMessage.setQueueName(QueueConst.APPLY_LOAN_NOTIFY_QUEUE);
        mqMessage.setMessage(JSON.toJSONString(loanApplyMqDto));
        mqMessageService.sendMessage(mqMessage);

        // 4.返回值
        TrdPlatformLoanApplyDto result = new TrdPlatformLoanApplyDto(orderNo, trdPlatformLoanOrder.getCreatedTime().getTime(), loanApplyRequest.getApplyTerm(), loanApplyRequest.getApplyTermUnit(), loanApplyRequest.getOrderAmount().intValue(), loanApplyRequest.getProductCode(), loanApplyRequest.getMerchantCode(), loanApplyRequest.getUserId(), loanApplyRequest.getIsReloan());



        return result;
    }

    @Override
    @Transactional
    public void loanApplyCallback(String orderNo, boolean pushResult, boolean bizResult) {
        TrdPlatformLoanOrder order = trdPlatformLoanOrderMapper.selectByOrderNo(orderNo);
        LoanOrderStatus loanOrderStatus = LoanOrderStatus.getEnumByValue(order.getStatus());

        // 推送失败，需要判断重试的次数是否等于最大重试次数
        if (!pushResult) {
            // 推送失败, 当推送次数等于3的时候，把订单状态置为推送失败（终态），小于3的情况下则递增加1
            // 1、修改订单状态 2、记录trace
            if (order.getRetryTimes().intValue() == LoanOrderConstants.MAX_RETRY_PUSH_TIMES) {
            	this.updateOrderStatusAndInsertTrace(orderNo, LoanOrderStatus.PUSH_FAIL, LoanOrderStatus.NEW, LoanOrderStatus.PUSH_FAIL.getDesc(), PlatformTraceOrderEventEnum.PLATFORM_LOAN_FAIL, "");
            }
            if (order.getRetryTimes().intValue() < LoanOrderConstants.MAX_RETRY_PUSH_TIMES) {
                this.updateLoanOrderRetryTimes(orderNo, order.getRetryTimes() + 1, order.getRetryTimes());
            }
            return;
        }

        // 推送成功，需要判断第三方的业务校验是否也通过
        if (loanOrderStatus.getValue().equals(LoanOrderStatus.NEW_PUSH_SUCCESS.getValue())) {
            logger.info("借款申请订单[{}]已经推送成功, 忽略此次操作", orderNo);
            return;
        }

        // 推送成功，需要判断第三方的业务校验是否也通过
        if (loanOrderStatus.getValue().equals(LoanOrderStatus.CHECK_FAIL.getValue())) {
            logger.info("借款申请订单[{}]的第三方业务校验失败状态，忽略此次操作", orderNo);
            return;
        }


        if (bizResult){
            // 推送成功, 第三方业务校验成功
            this.updateLoanOrderStatus(orderNo, LoanOrderStatus.NEW_PUSH_SUCCESS, LoanOrderStatus.NEW , LoanOrderStatus.NEW_PUSH_SUCCESS.getDesc());
        } else {
            // 推送成功，第三方业务校验失败
            //this.updateLoanOrderStatus(orderNo, LoanOrderStatus.CHECK_FAIL, LoanOrderStatus.NEW , LoanOrderStatus.CHECK_FAIL.getDesc());
        	this.updateOrderStatusAndInsertTrace(orderNo, LoanOrderStatus.CHECK_FAIL, LoanOrderStatus.NEW, LoanOrderStatus.CHECK_FAIL.getDesc(), PlatformTraceOrderEventEnum.PLATFORM_LOAN_FAIL, "");
        }
    }

    @Override
    public List<TrdPlatformLoanApplyDto> getLoanOrderApplyRetryList() {
        List<TrdPlatformLoanOrder> trdPlatformLoanOrders = trdPlatformLoanOrderMapper.selectByStatusAndRetryTimes(LoanOrderStatus.NEW.getValue(), LoanOrderConstants.MAX_RETRY_PUSH_TIMES);
        List<TrdPlatformLoanApplyDto> trdPlatformLoanApplyDtos = new ArrayList<>();
        for (Iterator<TrdPlatformLoanOrder> iterator = trdPlatformLoanOrders.iterator(); iterator.hasNext(); ) {
            TrdPlatformLoanOrder trdPlatformLoanOrder = iterator.next();

            TrdPlatformLoanApplyDto trdPlatformLoanApplyDto = new TrdPlatformLoanApplyDto(trdPlatformLoanOrder.getOrderNo(), trdPlatformLoanOrder.getCreatedTime().getTime(), trdPlatformLoanOrder.getTerm(), Integer.parseInt(trdPlatformLoanOrder.getTermUnit()), MoneyUtil.changeCentToYuan(trdPlatformLoanOrder.getOrderAmount().intValue()).intValue(), trdPlatformLoanOrder.getProductCode(), trdPlatformLoanOrder.getServiceCompany(), trdPlatformLoanOrder.getUserId(), trdPlatformLoanOrder.getIsReloan());

            trdPlatformLoanApplyDtos.add(trdPlatformLoanApplyDto);
        }
        return trdPlatformLoanApplyDtos;
    }





    @Override
    public void updateOrderStatusAndInsertTrace(String orderNo, LoanOrderStatus targetStatus, LoanOrderStatus originStatus, String remark, PlatformTraceOrderEventEnum platformTraceOrderEventEnum, String traceData) {
        TrdPlatformLoanOrder order = trdPlatformLoanOrderMapper.selectByOrderNo(orderNo);
        if (order == null) {
            logger.error("没找到该订单号{}的订单,订单为空", orderNo);
            throw new ServiceException(BaseResponse.ResponseCode.BIZ_CHECK_FAIL.getValue(), "没找到该订单号" + orderNo + "的订单,订单为空");
        }
        this.updateLoanOrderStatus(orderNo, targetStatus, originStatus, remark);
        this.insertTrace(order.getTraceNo(), platformTraceOrderEventEnum, traceData);
    }

    @Override
    public  void updateLoanOrderStatus(String orderNo, LoanOrderStatus targetStatus, LoanOrderStatus originStatus, String remark) {
        int count = trdPlatformLoanOrderMapper.updateLoanOrderStatus(orderNo, targetStatus.getValue(), originStatus.getValue(), null, remark);
        if (count != 1) {
            throw new SqlUpdateException("trd_platform_loan_order订单状态更新失败");
        }
    }

    @Override
    @Transactional
    public void updateOrderStatusAndTime(String orderNo, LoanOrderStatus targetStatus, LoanOrderStatus originStatus, Date updateTime, String remark) {
        int count = trdPlatformLoanOrderMapper.updateLoanOrderStatus(orderNo, targetStatus.getValue(), originStatus.getValue(), updateTime, remark);
        if (count != 1) {
            throw new SqlUpdateException("trd_platform_loan_order订单状态更新失败");
        }
    }

    private void updateLoanOrderRetryTimes(String orderNo,Integer targetRetryTimes,Integer originRetryTimes) {
        int count = trdPlatformLoanOrderMapper.updateLoanOrderRetryTimes(orderNo, targetRetryTimes, originRetryTimes);
        if (count != 1) {
            throw new SqlUpdateException("trd_platform_loan_order订单状态更新失败");
        }
    }

    @Override
    @Transactional
    public void insertTrace(String traceNo, PlatformTraceOrderEventEnum platformTraceOrderEventEnum, String traceData) {
        // 1.1 新增trace
        Trace trace = new Trace(traceNo, platformTraceOrderEventEnum.getCode(), platformTraceOrderEventEnum.getText(), new Date(), traceData);
        int count = traceMapper.insert(trace);
        if (count != 1) {
            throw new SqlUpdateException("Trace插入失败");
        }
    }

    @Override
    public PageObj<BackOrderResp> getOrders(BackOrderRequest orderRequest) {
        PageHelper.startPage(orderRequest.getPageNum(), orderRequest.getPageSize());
        List<TrdPlatformLoanOrder> list = trdPlatformLoanOrderMapper.getLoanOrderByCondition(orderRequest);
        PageInfo<TrdPlatformLoanOrder> pages = ((Page<TrdPlatformLoanOrder>) list).toPageInfo();
        List<BackOrderResp> resps = new ArrayList<>(list.size());
        for (int i = 0; i < list.size(); i++) {
            BackOrderResp backOrder = new BackOrderResp();
            BeanPropertyUtils.copyProperties(list.get(i), backOrder);
            backOrder.setOrderAmount(MoneyUtil.changeCentToYuan(list.get(i).getOrderAmount()));
            resps.add(backOrder);
        }
        return new PageObj<>(pages.getPageNum(), pages.getPageSize(),
                pages.getTotal(), pages.getPages(), resps);
    }

    @Override
    public PageObj<UserOrderResp> getUserOrders(UserOrderRequest userOrderRequest) {
        PageHelper.startPage(userOrderRequest.getPageNum(), userOrderRequest.getPageSize());
        List<TrdPlatformLoanOrder> list = trdPlatformLoanOrderMapper.getLoanOrderByUserId(userOrderRequest);
        PageInfo<TrdPlatformLoanOrder> pages = ((Page<TrdPlatformLoanOrder>) list).toPageInfo();
        List<UserOrderResp> resps = new ArrayList<>(list.size());
        for (int i = 0; i < list.size(); i++) {
            UserOrderResp userOrderResp = new UserOrderResp();
            BeanPropertyUtils.copyProperties(list.get(i), userOrderResp);
            userOrderResp.setOrderAmount(MoneyUtil.changeCentToYuan(list.get(i).getOrderAmount()));
            userOrderResp.setLoanTime(list.get(i).getCreatedTime());
            resps.add(userOrderResp);
        }
        return new PageObj<>(pages.getPageNum(), pages.getPageSize(),
                pages.getTotal(), pages.getPages(), resps);
    }

    @Override
    @Transactional
    public void confirmLoan(PlatformConfirmLoanRequest confirmLoanReq) {
        //幂等校验
        idempotentService.idempotentCheck(IdempotentTypeEnum.CONFIRM_LOAN, confirmLoanReq);

        TrdPlatformLoanOrder trdPlatformLoanOrder=trdPlatformLoanOrderMapper.selectOrderByUserIdOrderNo(confirmLoanReq.getOrderNo(),confirmLoanReq.getUserId());
        if(trdPlatformLoanOrder==null){
            logger.error("can't find platform loanOrder by orderNo and userId");
            throw new ServiceException("订单不存在");
        }

        //更新状态，下次执行时间，重置retryTimes为0
        int result = trdPlatformLoanOrderMapper.updateStatusAndNextRetryTimeAndRetryTimes(confirmLoanReq.getOrderNo(),
                LoanOrderStatus.CONFIRM_PUSHING.getValue(), LoanOrderStatus.CONFIRM_PRE.getValue(),
                AppUtil.getNextRetryTime(new Date(), 0), 0);
        if (result != 1) {
            logger.error("update platform loanOrder status from CONFIRM_PRE to CONFIRM_PUSHING error,orderNo:{}",confirmLoanReq.getOrderNo());
            throw new ServiceException("更新借款记录失败，无法从CONFIRM_PRE状态更新到CONFIRM_PUSHING" );
        }
        //添加trace记录
        traceMapper.insert(new Trace(trdPlatformLoanOrder.getTraceNo(), PlatformTraceOrderEventEnum.PLATFORM_LOAN_AUDIT.getCode(),
                PlatformTraceOrderEventEnum.PLATFORM_LOAN_AUDIT.getText(), new Date(), null));

        //填充数据，传递给openapi
        OpenApiConfirmLoanRequest openApiConfirmLoanRequest=new OpenApiConfirmLoanRequest(
                confirmLoanReq.getOrderNo(), MoneyUtil.changeCentToYuan(trdPlatformLoanOrder.getOrderAmount()),
                trdPlatformLoanOrder.getTerm(),trdPlatformLoanOrder.getUserBankCardId(),trdPlatformLoanOrder.getIsReloan()==true?1:0
        );

        try {
            threadPoolTaskExecutor.execute(
                    new ConfirmLoanTask(trdPlatformLoanOrder.getServiceCompany(), openApiConfirmLoanRequest)
            );
        } catch (Exception e) {
            logger.error("putoToOpenApi error,wait job retry", e);
        }

    }

    @Override
    public Boolean orderIsBelongToUser(String orderNo, Long userId){
        TrdPlatformLoanOrder order = trdPlatformLoanOrderMapper.selectOrderByUserIdOrderNo(orderNo ,userId);
        if(order == null){
            return false;
        } else {
            return true;
        }
    }

    @Override
    public LoanOrderDetailDto getLoanOrderDetail(LoanOrderDetailReq loanOrderDetailReq) {
        TrdPlatformLoanOrder trdPlatformLoanOrder = trdPlatformLoanOrderMapper.selectByOrderNo(loanOrderDetailReq.getOrderNo());
        if (trdPlatformLoanOrder == null) {
            throw new ServiceException(BaseResponse.ResponseCode.PARAM_CHECK_FAIL.getValue(), "传入的订单号【"+loanOrderDetailReq.getOrderNo()+"】不存在,请检查");
        }
        if (trdPlatformLoanOrder.getUserId().longValue() != loanOrderDetailReq.getUserId().longValue()) {
            throw new ServiceException(BaseResponse.ResponseCode.PARAM_CHECK_FAIL.getValue(), "用户id和订单不匹配");
        }
        String status = trdPlatformLoanOrder.getStatus();
        // 调用第三方接口获取订单最新状态
        LoanOrderStatusResponse loanOrderStatusResponse = null;
        LoanOrderStatus loanOrderStatus = null;
        boolean isInPreOrderStatus = false;
		try {
			loanOrderStatusResponse = openApiRemoteService.getOrderStatus(trdPlatformLoanOrder.getServiceCompany(),
					loanOrderDetailReq.getOrderNo());
			loanOrderStatus = LoanOrderStatus.getEnumByPartnerOrderStatus(loanOrderStatusResponse.getOrder_status());
			logger.info("loanOrderStatusResponse:{}",loanOrderStatusResponse);
			logger.info("获取最新订单状态getLoanOrderDetail,oldStatus={},newStatus={}",status,loanOrderStatusResponse!=null?loanOrderStatusResponse.getOrder_status():null);
			isInPreOrderStatus =  isInPreOrderStatus(loanOrderStatus.getPreStatus(),trdPlatformLoanOrder.getStatus());
		} catch (Exception e) {
			logger.error("从第三方获取订单状态失败", e);
		}
		
        //状态发生变化了，更新成最新的状态
        if (isInPreOrderStatus && loanOrderStatusResponse!=null && loanOrderStatus!=null && !LoanOrderStatus.noNeedGetStatus(status) && !StringUtils.equals(status, loanOrderStatus.getValue())) {
            status = loanOrderStatus.getValue();
            logger.info("从第三方获取到的最新状态为:{}",status);
            if (LoanOrderStatus.isNeedRecordTrace(loanOrderStatus.getValue())) {
            	PlatformTraceOrderEventEnum platformTraceOrderEventEnum = PlatformTraceOrderEventEnum.getEnumByCode(loanOrderStatus.getTraceStatus().getCode());
                try {
                    Trace trace = new Trace(trdPlatformLoanOrder.getTraceNo(), platformTraceOrderEventEnum.getCode(), platformTraceOrderEventEnum.getText(), new Date(), JsonUtils.toJSONString(trdPlatformLoanOrder));
                    idempotentService.idempotentCheck(IdempotentTypeEnum.ORDER_TRACE, trace);
                    int insertNum = traceMapper.insert(trace);
                    if (insertNum <= 0) {
                        logger.error("插入trace表失败,订单order_no:{}，traceNo:{}", trdPlatformLoanOrder.getOrderNo(), trdPlatformLoanOrder.getTraceNo());
                    }
                } catch (Exception e) {
                    logger.error("插入trace异常", e);
                }
            }
            int updateNum = trdPlatformLoanOrderMapper.updateLoanOrderStatus(trdPlatformLoanOrder.getOrderNo(), loanOrderStatus.getValue(), trdPlatformLoanOrder.getStatus(), new Date(loanOrderStatusResponse.getUpdate_time()*1000L), null);
            if (updateNum <= 0) {
                logger.error("更新借款订单状态失败,订单order_no:{}", trdPlatformLoanOrder.getOrderNo());
            }
        }
        LoanOrderDetailDto loanOrderDetailDto = new LoanOrderDetailDto();
        loanOrderDetailDto.setOrderAmount(MoneyUtil.changeCentToYuan(trdPlatformLoanOrder.getOrderAmount()));
        loanOrderDetailDto.setPaymentAmount(MoneyUtil.changeCentToYuan(trdPlatformLoanOrder.getPaymentAmount()));
        loanOrderDetailDto.setRepaymentAmount(MoneyUtil.changeCentToYuan(trdPlatformLoanOrder.getRepaymentAmount()));
        loanOrderDetailDto.setFeeAmount(MoneyUtil.changeCentToYuan(trdPlatformLoanOrder.getFeeAmount()));
        loanOrderDetailDto.setInterestAmount(MoneyUtil.changeCentToYuan(trdPlatformLoanOrder.getInterestAmount()));
        loanOrderDetailDto.setTotalFeeAmount(MoneyUtil.changeCentToYuan(trdPlatformLoanOrder.getFeeAmount()+trdPlatformLoanOrder.getInterestAmount()));
        loanOrderDetailDto.setBankName(trdPlatformLoanOrder.getBankName());
        loanOrderDetailDto.setBankCardNoLastFour(trdPlatformLoanOrder.getBankCardNoLastFour());
        loanOrderDetailDto.setTerm(trdPlatformLoanOrder.getTerm());
        loanOrderDetailDto.setTermUnit(trdPlatformLoanOrder.getTermUnit());
        loanOrderDetailDto.setLoanTime(trdPlatformLoanOrder.getCreatedTime());
        loanOrderDetailDto.setStatus(status);
        loanOrderDetailDto.setServiceCompany(trdPlatformLoanOrder.getServiceCompany());
        loanOrderDetailDto.setProductCode(trdPlatformLoanOrder.getProductCode());
        loanOrderDetailDto.setUserBankCardId(trdPlatformLoanOrder.getUserBankCardId());
        return loanOrderDetailDto;
    }
    
    /***
     * 校验订单的前置状态
     * @param newStatus
     * @param preStatusList
     * @param oldStatus
     */
    private boolean isInPreOrderStatus(List<LoanOrderStatus> preStatusList,String oldStatus) {
    	boolean flag = false;
    	for(LoanOrderStatus loanOrderStatus:preStatusList) {
    		if(StringUtils.equals(loanOrderStatus.getValue(), oldStatus)) {
    			flag = true;
    		}
    	}
    	return flag;
	}


    @Override
    public List<TraceDto> getTrace(TraceReq traceReq) {
        List<TraceDto> traceDtoList = new ArrayList<TraceDto>();
        List<Trace> traceList = traceMapper.selectTraceListByOrderNo(traceReq);
        for (Trace trace : traceList) {
            TraceDto traceDto = new TraceDto();
            BeanUtils.copyProperties(trace, traceDto);
            traceDtoList.add(traceDto);
        }
        return traceDtoList;
    }

	@Override
	@Transactional
	public void cancelOrder(CancelOrderReq cancelOrderReq) {
	    TrdPlatformLoanOrder trdPlatformLoanOrder = trdPlatformLoanOrderMapper.selectByOrderNo(cancelOrderReq.getOrderNo());
        if (trdPlatformLoanOrder == null) {
            throw new ServiceException(BaseResponse.ResponseCode.PARAM_CHECK_FAIL.getValue(), "传入的订单号【"+cancelOrderReq.getOrderNo()+"】不存在,请检查");
        }
        if (trdPlatformLoanOrder.getUserId().longValue() != cancelOrderReq.getUserId().longValue()) {
            throw new ServiceException(BaseResponse.ResponseCode.PARAM_CHECK_FAIL.getValue(), "用户id和订单不匹配");
        }
        if(!LoanOrderStatus.isCancelStatus(trdPlatformLoanOrder.getStatus())) {
        	throw new ServiceException(BaseResponse.ResponseCode.BIZ_CHECK_FAIL.getValue(), "订单状态为【"+trdPlatformLoanOrder.getStatus()+"】,此状态不能取消");
        }
        Trace trace = new Trace(trdPlatformLoanOrder.getTraceNo(), PlatformTraceOrderEventEnum.PLATFORM_CANCEL.getCode(), PlatformTraceOrderEventEnum.PLATFORM_CANCEL.getText(), new Date(), JsonUtils.toJSONString(trdPlatformLoanOrder));
        idempotentService.idempotentCheck(IdempotentTypeEnum.ORDER_TRACE, trace);
        int insertNum = traceMapper.insert(trace);
        if (insertNum <= 0) {
            logger.error("插入trace表失败,订单order_no:{}，traceNo:{}", trdPlatformLoanOrder.getOrderNo(), trdPlatformLoanOrder.getTraceNo());
            throw new SqlUpdateException("插入trace表失败");
        }
        int updateNum = trdPlatformLoanOrderMapper.updateLoanOrderStatus(cancelOrderReq.getOrderNo(), LoanOrderStatus.CANCEL.getValue(), trdPlatformLoanOrder.getStatus(), null, null);
        if (updateNum <= 0) {
            logger.error("更新借款订单状态失败,订单order_no:{}", cancelOrderReq.getOrderNo());
            throw new SqlUpdateException("更新借款订单状态失败");
        }
	}


    @Override
    public CheckLoanResponse checkLoan(CheckLoanRequest checkLoanRequest) {
        CheckLoanDto checkLoanDto=trdPlatformLoanOrderMapper.getOrderNoByUserIdAndServiceCompanyLast(
                checkLoanRequest.getUserId(),checkLoanRequest.getServiceCompany());
        if(checkLoanDto==null){
            return new CheckLoanResponse(Boolean.TRUE);
        }
        if(isUnderway(checkLoanDto.getStatus())){
            return new CheckLoanResponse(checkLoanDto.getOrderNo(),Boolean.FALSE, checkLoanDto.getStatus(),
                   checkLoanDto.getCreatedTime(),checkLoanDto.getUserBankCardId());
        }
        return new CheckLoanResponse(Boolean.TRUE);
    }

    /**
     * 订单状态是否在运行中
     * @param status
     * @return 在运行中返回true ,否则false
     */
    private boolean isUnderway(String status){
        //若订单状态是终态，返回false
        if(LoanOrderStatus.REFUSED.getValue().equals(status)
                || LoanOrderStatus.WITHDRAW_FAIL.getValue().equals(status)
                || LoanOrderStatus.SETTLED.getValue().equals(status)
                || LoanOrderStatus.CANCEL.getValue().equals(status)
                || LoanOrderStatus.PUSH_FAIL.getValue().equals(status)
                || LoanOrderStatus.FAIL.getValue().equals(status)  ){

            return false;
        }
        return true;
    }
}
