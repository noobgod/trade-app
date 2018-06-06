package com.xianjinxia.trade.app.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xianjinxia.trade.app.dto.ManualReviewLoanOrderDto;
import com.xianjinxia.trade.app.dto.QuotaGiveBackDto;
import com.xianjinxia.trade.app.dto.SmsDto;
import com.xianjinxia.trade.app.request.ApplyLoanReq;
import com.xianjinxia.trade.app.request.ConfirmLoanReq;
import com.xianjinxia.trade.app.request.ManualReviewStatusReq;
import com.xianjinxia.trade.app.request.PageReq;
import com.xianjinxia.trade.app.response.ApplyLoanResponse;
import com.xianjinxia.trade.app.response.BaseResponse;
import com.xianjinxia.trade.app.response.ConfirmLoanResponse;
import com.xianjinxia.trade.app.service.ILoanOrderService;
import com.xianjinxia.trade.app.service.ILoanService;
import com.xianjinxia.trade.app.service.IMqMessageService;
import com.xianjinxia.trade.platform.request.SyncLoanOrderReq;
import com.xianjinxia.trade.shared.constant.QueueConst;
import com.xianjinxia.trade.shared.constant.SmsConstant;
import com.xianjinxia.trade.shared.domain.LoanOrder;
import com.xianjinxia.trade.shared.domain.Trace;
import com.xianjinxia.trade.shared.enums.IdempotentTypeEnum;
import com.xianjinxia.trade.shared.enums.LoanCodeMsgEnum;
import com.xianjinxia.trade.shared.enums.LoanOrderStatusEnum;
import com.xianjinxia.trade.shared.enums.ManualReviewPassEnum;
import com.xianjinxia.trade.shared.enums.ProductCategoryEnum;
import com.xianjinxia.trade.shared.enums.TraceOrderEventEnum;
import com.xianjinxia.trade.shared.exception.ServiceException;
import com.xianjinxia.trade.shared.exception.SqlUpdateException;
import com.xianjinxia.trade.shared.idempotent.IdempotentService;
import com.xianjinxia.trade.shared.mapper.LoanOrderMapper;
import com.xianjinxia.trade.shared.mapper.TraceMapper;
import com.xianjinxia.trade.shared.service.ISmsService;
import com.xianjinxia.trade.shared.service.LoanApplyChecker;
import com.xianjinxia.trade.shared.utils.DateUtil;
import com.xianjinxia.trade.shared.utils.GsonUtil;
import com.xianjinxia.trade.shared.utils.JsonUtils;
import com.xianjinxia.trade.shared.utils.UniqueIdUtil;
import com.xjx.mqclient.pojo.MqMessage;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author fanmaowen
 * @version 1.0
 * @title ConfirmLoanOrderService.java
 * @created 2017年9月12日
 */
@Service
public class LoanService implements ILoanService {

    private static final Logger logger = LoggerFactory.getLogger(LoanService.class);


    @Autowired
    private LoanOrderMapper loanOrderMapper;

    @Autowired
    private TraceMapper traceMapper;

    @Autowired
    private ILoanOrderService iLoanOrderService;

    @Autowired
    private IMqMessageService mqMessageService;

    @Autowired
    private ISmsService smsService;

    @Autowired
    private IdempotentService idempotentService;

    @Autowired
    private LoanApplyChecker loanApplyChecker;

    @Override
    public BaseResponse<ApplyLoanResponse> applyLoan(ApplyLoanReq applyLoanReq) throws Exception {
        BaseResponse<ApplyLoanResponse> response = new BaseResponse<>();
        logger.info("trade apply_loan params:{}", applyLoanReq);
        ApplyLoanResponse applyLoanResponse = new ApplyLoanResponse(LoanCodeMsgEnum.SUCCESS);

        //TODO 临时注释修改
        //loanApplyChecker.check(applyLoanReq.getUserId(), Integer.parseInt(applyLoanReq.getQuietPeriod()));


        if (hasNonUltimateOrder(applyLoanReq.getUserId())) {
            applyLoanResponse = new ApplyLoanResponse(LoanCodeMsgEnum.CHECK_ULTIMATE_ORDER);
            response.setData(applyLoanResponse);
            return response;
        }
        if (!rejectOrderPass(applyLoanReq.getUserId(), applyLoanReq.getQuietPeriod())) {
            applyLoanResponse = new ApplyLoanResponse(LoanCodeMsgEnum.CHECK_REJECTORDER);
            response.setData(applyLoanResponse);
            logger.info(LoanCodeMsgEnum.CHECK_REJECTORDER.getValue());
            return response;
        }
        // 创建trace记录
        Trace trace = new Trace();
        trace.setEventTime(new Date());
        trace.setOrderEvent(TraceOrderEventEnum.LOAN_APPLY.getCode());
        trace.setEventText(TraceOrderEventEnum.LOAN_APPLY.getText());
        trace.setTraceData(GsonUtil.toGson(applyLoanReq));
        trace.setTraceNo(UniqueIdUtil.getTraceNoUniqueId());
        traceMapper.insert(trace);
        logger.info("生成trace表:{}", trace);
        applyLoanResponse.setTraceNo(trace.getTraceNo());
        response.setData(applyLoanResponse);
        return response;
    }

    @Override
    public BaseResponse<ConfirmLoanResponse> confirmLoanOrder(ConfirmLoanReq confirmLoanReq) {// throws Exception {
        BaseResponse<ConfirmLoanResponse> response = new BaseResponse<>();
        ConfirmLoanResponse confirmLoanResponse = new ConfirmLoanResponse(LoanCodeMsgEnum.SUCCESS);
        if (hasNonUltimateOrder(confirmLoanReq.getUserId())) {
            confirmLoanResponse = new ConfirmLoanResponse(LoanCodeMsgEnum.CHECK_ULTIMATE_ORDER);
            response.setData(confirmLoanResponse);
            logger.info(LoanCodeMsgEnum.CHECK_ULTIMATE_ORDER.getValue());
            orderSendMq(confirmLoanReq.getUserId(), confirmLoanReq.getTraceNo(), confirmLoanReq.getProductCategory(), confirmLoanReq.getOrderAmount().multiply(new BigDecimal(100)).intValue(), QueueConst.RETURN_QUOTA);
            return response;
        }
        if (!hasTraceNo(confirmLoanReq.getTraceNo())) {
            logger.info("{} 在 trd_trace 表中 不存在", confirmLoanReq.getTraceNo());
            throw new ServiceException(confirmLoanReq.getTraceNo() + " 在 trd_trace 表中 不存在");
        }
        if (!rejectOrderPass(confirmLoanReq.getUserId(), confirmLoanReq.getQuietPeriod())) {
            confirmLoanResponse = new ConfirmLoanResponse(LoanCodeMsgEnum.CHECK_REJECTORDER);
            response.setData(confirmLoanResponse);
            logger.info(LoanCodeMsgEnum.CHECK_REJECTORDER.getValue());
            orderSendMq(confirmLoanReq.getUserId(), confirmLoanReq.getTraceNo(), confirmLoanReq.getProductCategory(), confirmLoanReq.getOrderAmount().multiply(new BigDecimal(100)).intValue(), QueueConst.RETURN_QUOTA);
            return response;
        }
        logger.info("trade confirm_loan params:{}", confirmLoanReq);
        response = iLoanOrderService.insertLoanOrderAndSendMq(confirmLoanReq, response, confirmLoanResponse);
        return response;
    }

    //是否有非最终态的订单
    public Boolean hasNonUltimateOrder(Long userId) {
        Set<String> finalStatusSet = LoanOrderStatusEnum.getByFinalStatus(false);
        String[] finalStatusArray = finalStatusSet.toArray(new String[finalStatusSet.size()]);//toArray();//toArray(new String[finalStatus.size()]);//new String[]{LoanOrderStatusEnum.APPROVALING.getCode(), LoanOrderStatusEnum.LOANING.getCode(), LoanOrderStatusEnum.MANUALREVIEWING.getCode(), LoanOrderStatusEnum.MANUALREVIEW_SUCCESS.getCode()};
        Integer nonUltimateOrderSize = loanOrderMapper.selectNonUltimateOrder(userId, finalStatusArray);
        return nonUltimateOrderSize > 0;
    }

    //用户最近被拒的订单+冷却时间和当前时间对比
    public Boolean rejectOrderPass(Long userId, String quietPeriod) {
        LoanOrder loanOrder = selectRejectOrder(userId);
        if (loanOrder != null) {
            //计算剩余冷却时间
            int intervalDay = DateUtil.daysBetween(new Date(), DateUtil.addDay(loanOrder.getReviewFailTime(), Integer.parseInt(quietPeriod)));
            return intervalDay <= 0;
        } else {
            return true;
        }
    }

    //用户最近被拒的订单
    @Override
    public LoanOrder selectRejectOrder(Long userId) {
        String[] status = new String[]{LoanOrderStatusEnum.REFUSED.getCode(), LoanOrderStatusEnum.MANUAL_REFUSED.getCode()};
        return loanOrderMapper.selectUserOrderByStatus(userId, status);
    }

    //用户待审核的订单
    @Override
    public LoanOrder selectReviewOrder(Long userId) {
        String[] status = new String[]{LoanOrderStatusEnum.NEW.getCode()};
        return loanOrderMapper.selectUserOrderByStatus(userId, status);
    }

    @Override
    public PageInfo<ManualReviewLoanOrderDto> selectManualReviewLoanOrderList(PageReq pageReq) {
        logger.info("params:{}", pageReq.toString());
        PageHelper.startPage(pageReq.getPageNum(), pageReq.getPageSize());
        List<ManualReviewLoanOrderDto> list = loanOrderMapper.selectLoanOrderListByStatus(LoanOrderStatusEnum.MANUAL_REVIEWING.getCode());
        Page<ManualReviewLoanOrderDto> page = (Page<ManualReviewLoanOrderDto>) list;
        logger.info("result:{}", page);
        return page.toPageInfo();
    }

    @Override
    @Transactional
    public void updateManualReviewStatus(ManualReviewStatusReq manualReviewStatusReq) {
        logger.info("updateManualReviewStatus  params:{}", manualReviewStatusReq);
        LoanOrder loanOrder = null;
        loanOrder = loanOrderMapper.selectLoanOrderByBizSeqNoAndStatus(manualReviewStatusReq.getBizSeqNo(), LoanOrderStatusEnum.MANUAL_APPROVED.getCode());
        if (loanOrder != null) {
            logger.info("订单业务流水号" + manualReviewStatusReq.getBizSeqNo() + "订单人工审核已修改成功");
            return;
        }
        loanOrder = loanOrderMapper.selectLoanOrderByBizSeqNoAndStatus(manualReviewStatusReq.getBizSeqNo(), LoanOrderStatusEnum.MANUAL_REVIEWING.getCode());
        if (loanOrder == null) {
            throw new ServiceException("订单业务流水号" + manualReviewStatusReq.getBizSeqNo() + "订单人工审核前状态不对");
        }
        if (manualReviewStatusReq.getManualReviewIsPass() == ManualReviewPassEnum.MANUAL_REVIEW_PASS.getCode()) {
            int count = loanOrderMapper.updateOrderStatus(manualReviewStatusReq.getBizSeqNo(), LoanOrderStatusEnum.MANUAL_APPROVED.getCode(), LoanOrderStatusEnum.MANUAL_REVIEWING.getCode());
            if (count != 1) {
                throw new ServiceException("订单业务流水号" + manualReviewStatusReq.getBizSeqNo() + "的订单人工审核状态修改错误");
            }
        } else if (manualReviewStatusReq.getManualReviewIsPass() == ManualReviewPassEnum.MANUAL_REVIEW_FAIL.getCode()) {
            onManualReviewFailure(manualReviewStatusReq, loanOrder);
        }

    }

    private void onManualReviewFailure(ManualReviewStatusReq manualReviewStatusReq, LoanOrder loanOrder) {
        int count = loanOrderMapper.updateOrderStatus(manualReviewStatusReq.getBizSeqNo(), LoanOrderStatusEnum.MANUAL_REFUSED.getCode(), LoanOrderStatusEnum.MANUAL_REVIEWING.getCode());
        if (count != 1) {
            throw new ServiceException("订单业务流水号" + manualReviewStatusReq.getBizSeqNo() + "的订单人工审核状态修改错误");
        }
        int updateOrderReviewFailTime = loanOrderMapper.updateOrderReviewFailTime(manualReviewStatusReq.getBizSeqNo(), new Date());
        if (updateOrderReviewFailTime != 1) {
            logger.error("更新借款订单审核失败时间失败,订单bizSeqNo:{}", manualReviewStatusReq.getBizSeqNo());
            throw new SqlUpdateException("更新借款订单审核失败时间失败");
        }
        // 若风控结果为失败 调用cashman接口 归还用户额度（mq消息）
        QuotaGiveBackDto quotaGiveBackDto = new QuotaGiveBackDto(loanOrder.getUserId(), loanOrder.getOrderAmount(), loanOrder.getTraceNo(), loanOrder.getProductCategory());

        mqMessageService.sendMessage(new MqMessage(JsonUtils.toJSONString(quotaGiveBackDto), QueueConst.RETURN_QUOTA));
        logger.info("人工审核结果为失败,归还用户额度。用户id:{},借款bizSeqNo:{}", loanOrder.getUserId(), manualReviewStatusReq.getBizSeqNo());

        //TODO 如何区分是因为用户额度的原因造成的人工审核失败--需要与其他系统确认
        //		smsService.sendSms(new SmsDto(loanOrder.getBizSeqNo(), loanOrder.getUserPhone(), SmsConstant.SMS_EXCEED_LIMITED_ID,
        //				String.valueOf(loanOrder.getOrderAmount())));


    }


    //是否有非最终态的订单
    public Boolean hasTraceNo(String traceNo) {
        Integer nonUltimateOrderSize = traceMapper.selectTraceNoCount(traceNo);
        return nonUltimateOrderSize > 0;
    }

    //订单有关发送MQ消息
    @Override
    public void orderSendMq(Long userId, String traceNo, int productCategory, int amount, String queueName) {
        QuotaGiveBackDto dto = new QuotaGiveBackDto();
        dto.setUserId(userId);
        dto.setProductCategory(productCategory);
        dto.setMoneyAmount(amount);
        dto.setSeqNo(traceNo);
        mqMessageService.sendMessage(new MqMessage(GsonUtil.toGson(dto), queueName));
    }

    @Override
    public int updateLoanStatusByID(Long loanOrderId) {
        return loanOrderMapper.updateStatByIdAndStat(LoanOrderStatusEnum.SETTLED.getCode(), loanOrderId, LoanOrderStatusEnum.LOAN_SUCCESS.getCode());
    }

    @Override
    public LoanOrder selectLoanOrderById(Long id) {
        return loanOrderMapper.selectLoanOrderById(id);
    }

    @Override
    @Transactional
    public void syncOrderStatus(SyncLoanOrderReq syncLoanOrderReq) {
        int bigAmountProduct = ProductCategoryEnum.BIGAMOUNT.getCode().intValue();
        if (syncLoanOrderReq.getProductCategory().intValue() != bigAmountProduct){
            logger.info("非大额借款产品的订单状态同步请求，忽略此请求，请求参数：{}", syncLoanOrderReq.toString());
        }

        //保证接口幂等
        idempotentService.idempotentCheck(IdempotentTypeEnum.SYNC_ORDER_STATUS_TO_TRADE, syncLoanOrderReq);

        LoanOrder loanOrder = loanOrderMapper.selectByPrimaryKey(syncLoanOrderReq.getLoanOrderId());
        if (loanOrder == null) {
            throw new ServiceException(BaseResponse.ResponseCode.BIZ_CHECK_FAIL.getValue(), "订单id为【" + syncLoanOrderReq + "】不存在");
        }
        String traceNo = loanOrder.getTraceNo();
        String newStatus = syncLoanOrderReq.getStatus();
        String oldStatus = loanOrder.getStatus();
        Long loanOrderId = syncLoanOrderReq.getLoanOrderId();

        //修改订单状态，插入trace
        updateOrderStatusAndInsertTrace(loanOrderId, newStatus, oldStatus, traceNo);

        //风控审核拒绝或者人工审核拒绝，需要恢复额度，并且发送短信通知用户审核失败
        if (StringUtils.equals(newStatus, LoanOrderStatusEnum.REFUSED.getCode()) || StringUtils.equals(newStatus, LoanOrderStatusEnum.MANUAL_REFUSED.getCode())||StringUtils.equals(newStatus, LoanOrderStatusEnum.LOAN_FAIL.getCode())) {
            // 若风控结果为失败 调用cashman接口 归还用户额度（mq消息）
            QuotaGiveBackDto quotaGiveBackDto = new QuotaGiveBackDto(loanOrder.getUserId(), loanOrder.getOrderAmount(), loanOrder.getTraceNo(), loanOrder.getProductCategory());
            mqMessageService.sendMessage(new MqMessage(JsonUtils.toJSONString(quotaGiveBackDto), QueueConst.RETURN_QUOTA));
            logger.info("风控结果为失败,归还用户额度。用户id:{},借款bizSeqNo:{}", loanOrder.getUserId(), loanOrder.getBizSeqNo());
            //cashman-app同步订单状态-放款失败不需要发送短信通知
//            if(!StringUtils.equals(newStatus, LoanOrderStatusEnum.LOAN_FAIL.getCode())){
//                //短信通知风控审核失败
//                Boolean isSendShortMsgSuccess = smsService.sendSms(new SmsDto(loanOrder.getBizSeqNo(), loanOrder.getUserPhone(), SmsConstant.SMS_RISK_FAILED_ID, "",null==loanOrder.getMerchantNo()?"":loanOrder.getMerchantNo()));
//                if (!isSendShortMsgSuccess) {
//                    logger.info("Send short msg for loan-check-fail failed,bizSeqNo is " + loanOrder.getBizSeqNo());
//                }
//            }

        }

    }

    public void updateOrderStatusAndInsertTrace(Long id, String newStatus, String oldStatus, String traceNo) {
        LoanOrderStatusEnum orderStatusEnum = LoanOrderStatusEnum.getByCode(newStatus);
        if (orderStatusEnum == null) {
            throw new ServiceException(BaseResponse.ResponseCode.BIZ_CHECK_FAIL.getValue(), "传入的订单状态有误");
        }
        if ((StringUtils.equals(newStatus,LoanOrderStatusEnum.LOAN_SUCCESS.getCode()) && StringUtils.equals(oldStatus,LoanOrderStatusEnum.OVERDUE.getCode()))
                || LoanOrderStatusEnum.canUpdate(newStatus, oldStatus)) {
            int updateNum = 0;
            if (LoanOrderStatusEnum.REFUSED.getCode().equals(newStatus)) {
                updateNum = loanOrderMapper.updateOrderStatusById(id, newStatus, oldStatus, new Date());
            } else {
                updateNum = loanOrderMapper.updateOrderStatusById(id, newStatus, oldStatus, null);
            }

            if (updateNum != 1) {
                throw new ServiceException(BaseResponse.ResponseCode.BIZ_CHECK_FAIL.getValue(), "更新订单状态失败");
            }
        } else {
            logger.error("订单前置状态有误：newStatus={},oldStatus={}", newStatus, oldStatus);
            throw new ServiceException(BaseResponse.ResponseCode.BIZ_CHECK_FAIL.getValue(), "订单前置状态有误");
        }

        //插入trace
        TraceOrderEventEnum traceEnum = orderStatusEnum.getTraceOrderEventEnum();
        if (traceEnum != null) {
            Trace trace = new Trace(traceNo, traceEnum.getCode(), traceEnum.getText(), new Date(), "");
            traceMapper.insert(trace);
        }

    }
}
