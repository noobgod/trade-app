package com.xianjinxia.trade.app.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xianjinxia.trade.app.dto.IndexLoanOrderDto;
import com.xianjinxia.trade.app.dto.LoanOrderDto;
import com.xianjinxia.trade.app.dto.QuotaGiveBackDto;
import com.xianjinxia.trade.app.dto.TradeDto;
import com.xianjinxia.trade.app.request.ConfirmLoanReq;
import com.xianjinxia.trade.app.request.LoanDetailReq;
import com.xianjinxia.trade.app.request.UnfreezeOrdersReq;
import com.xianjinxia.trade.app.response.BaseResponse;
import com.xianjinxia.trade.app.response.ConfirmLoanResponse;
import com.xianjinxia.trade.app.response.UnfreezeOrdersResponse;
import com.xianjinxia.trade.app.service.ILoanOrderService;
import com.xianjinxia.trade.app.service.IMqMessageService;
import com.xianjinxia.trade.app.service.IPayCenterService;
import com.xianjinxia.trade.shared.constant.Globals;
import com.xianjinxia.trade.shared.constant.QueueConst;
import com.xianjinxia.trade.shared.domain.LoanOrder;
import com.xianjinxia.trade.shared.domain.Trace;
import com.xianjinxia.trade.shared.enums.LoanOrderStatusEnum;
import com.xianjinxia.trade.shared.enums.LoanOrderTypeEnum;
import com.xianjinxia.trade.shared.enums.TraceOrderEventEnum;
import com.xianjinxia.trade.shared.exception.SqlUpdateException;
import com.xianjinxia.trade.shared.idempotent.IdempotentService;
import com.xianjinxia.trade.shared.mapper.LoanOrderMapper;
import com.xianjinxia.trade.shared.mapper.TraceMapper;
import com.xianjinxia.trade.shared.utils.JsonUtils;
import com.xianjinxia.trade.shared.utils.MoneyUtil;
import com.xianjinxia.trade.shared.utils.UniqueIdUtil;
import com.xjx.mqclient.pojo.MqMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *
 * @author liuzhifang
 *
 *         2017年9月1日
 */
@Service
public class LoanOrderService implements ILoanOrderService {

    private static final Logger logger = LoggerFactory.getLogger(LoanOrderService.class);

    @Autowired
    private LoanOrderMapper loanOrderMapper;

    @Autowired
    private IMqMessageService mqMessageService;

    @Autowired
    private TraceMapper traceMapper;

    @Autowired
    private IPayCenterService payCenterService;

    @Autowired
    private IdempotentService idempotentService;
    //分页查询订单列表
    @Override
    public PageInfo<LoanOrderDto> conditionalPaging(LoanDetailReq req){
        LoanOrder loanOrder = new LoanOrder();
        loanOrder.setUserId(req.getUserId());
        loanOrder.setProductCategory(req.getProductCategory());
        if(null!=req.getMerchantNo()&&(!"".equals(req.getMerchantNo()))){
            loanOrder.setMerchantNo(req.getMerchantNo());
        }
        logger.info("params:{}", loanOrder.toString());
        PageHelper.startPage(req.getPageNum(), req.getPageSize());
        List<LoanOrderDto> list = loanOrderMapper.selectSelective(loanOrder);
        Page<LoanOrderDto> page = (Page<LoanOrderDto>) list;
        logger.info("result:{}", page);
        return page.toPageInfo();
    }

    /**
     * 根据业务id查询借款订单，落单，通知支付中心放款
     */
    @Transactional
    @Override
    public void loanOrderRecord(TradeDto tradeDto) {
        LoanOrder loanOrder = loanOrderMapper.selectByBizNo(tradeDto.getAssetOrderId());
        if(null==loanOrder){
            logger.error("输入参数错误，业务流水号对应订单不存在，请重新输入");
            return;
        }
        if (!loanOrder.getStatus().equals(LoanOrderStatusEnum.NEW.getCode())) {
            logger.info("该借款订单已经审核过,bizSeqNo:{},该订单状态status:{}", tradeDto.getAssetOrderId(),loanOrder.getStatus());
            return;
        }

        // 判断风控回调结果是否成功
        if (TradeDto.RISK_SUCC.equals(tradeDto.getCode())) {
            onRiskSuccess(loanOrder, tradeDto);

        } else if (TradeDto.RISK_FAILED.equals(tradeDto.getCode())) {
            onRiskFailure(loanOrder, tradeDto);
        }

    }

    // 风控结果为成功
    private void onRiskSuccess(LoanOrder loanOrder, TradeDto tradeDto) {
        // 插入trace表
        traceMapper.insert(new Trace(loanOrder.getTraceNo(), TraceOrderEventEnum.MANUAL_REVIEWING.getCode(),
                TraceOrderEventEnum.MANUAL_REVIEWING.getText(), new Date(), JsonUtils.toJSONString(tradeDto)));
        // 更新订单状态为放款中,更新remark为风控分数
        int count = loanOrderMapper.updateOrderStatusAndRemark(loanOrder.getBizSeqNo(),
                        LoanOrderStatusEnum.MANUAL_APPROVED.getCode(),
                        LoanOrderStatusEnum.MANUAL_REVIEWING.getCode(), tradeDto.getMessage());
        if (count != 1) {
            logger.error("更新借款订单状态失败,订单bizSeqNo:[{}]", loanOrder.getBizSeqNo());
            throw new SqlUpdateException("更新借款订单状态失败");
        }
        // 调用支付中心进行支付
        //payCenterService.paymentRequest(loanOrder);

    }

    // 风控结果为失败
    private void onRiskFailure(LoanOrder loanOrder, TradeDto tradeDto) {
        // 插入trace表
        traceMapper.insert( new Trace(loanOrder.getTraceNo(),
                TraceOrderEventEnum.RISK_DATA_FAIL.getCode(),
                TraceOrderEventEnum.RISK_DATA_FAIL.getText(),
                new Date(), JsonUtils.toJSONString(tradeDto)));
        // 更新订单状态为审核失败,更新风控失败原因remark
        int count =
                loanOrderMapper.updateOrderStatusAndRemark(loanOrder.getBizSeqNo(),
                        LoanOrderStatusEnum.MANUAL_REFUSED.getCode(),
                        LoanOrderStatusEnum.MANUAL_REVIEWING.getCode(), tradeDto.getMessage());
        if (count != 1) {
            logger.error("更新借款订单状态失败,订单bizSeqNo:{}", loanOrder.getBizSeqNo());
            throw new SqlUpdateException("更新借款订单状态失败");
        }
        int updateOrderReviewFailTime = loanOrderMapper.updateOrderReviewFailTime(loanOrder.getBizSeqNo(),null==tradeDto.getReviewFailTime()?new Date():new Date(tradeDto.getReviewFailTime()));
        if (1!=updateOrderReviewFailTime ) {
            logger.error("更新借款订单审核失败时间失败,订单bizSeqNo:{}", loanOrder.getBizSeqNo());
            throw new SqlUpdateException("更新借款订单审核失败时间失败");
        }
        // 若风控结果为失败 调用cashman接口 归还用户额度（mq消息）
        QuotaGiveBackDto quotaGiveBackDto = new QuotaGiveBackDto(loanOrder.getUserId(),
                loanOrder.getOrderAmount(),loanOrder.getTraceNo(),loanOrder.getProductCategory() );

        mqMessageService.sendMessage(new MqMessage(JsonUtils.toJSONString(quotaGiveBackDto),
                QueueConst.RETURN_QUOTA));
        logger.info("风控结果为失败,归还用户额度。用户id:{},借款bizSeqNo:{}",
                loanOrder.getUserId(),loanOrder.getBizSeqNo());
        //短信通知风控审核失败--短信发送在cashman-app中处理
//        Boolean isSendShortMsgSuccess = smsService.sendSms(new SmsDto(loanOrder.getBizSeqNo(), loanOrder.getUserPhone(), SmsConstant.SMS_RISK_FAILED_ID,
//                "",null==loanOrder.getMerchantNo()?"":loanOrder.getMerchantNo()));
//        if(!isSendShortMsgSuccess){
//            logger.warn("Send short msg for loan-check-fail failed,bizSeqNo is "+loanOrder.getBizSeqNo());
//        }
    }

    @Override
    public LoanOrder getByUserIdAndOrderId(LoanOrder order) {
        return loanOrderMapper.getByUserIdAndOrderId(order);
    }



    @Override
    public IndexLoanOrderDto getUserLastLoanOrder(LoanOrderDto loanOrderDto) {
        LoanOrder loanOrder= loanOrderMapper.getLastLoanOrderByUserIdAndMerchantNo(loanOrderDto.getUserId(),loanOrderDto.getMerchantNo());
        if(loanOrder==null){
            return null;
        }

        // 支付中心放款失败，用户取消，风控推送失败，异常 状态的订单用户应当可以继续借款
        if (loanOrder.getStatus().equals(LoanOrderStatusEnum.LOAN_FAIL.getCode()) ||
                loanOrder.getStatus().equals(LoanOrderStatusEnum.CANCEL.getCode()) ||
                loanOrder.getStatus().equals(LoanOrderStatusEnum.PUSH_FAIL.getCode())||
                loanOrder.getStatus().equals(LoanOrderStatusEnum.FAIL.getCode())){
                    return null;
        }

        return new IndexLoanOrderDto(loanOrder.getId(), loanOrder.getStatus(),loanOrder.getProductId(),
                loanOrder.getProductCategory(), loanOrder.getPeriods(),
                MoneyUtil.changeCentToYuan(loanOrder.getOrderAmount()), loanOrder.getReviewFailTime());
    }

    @Override
    @Transactional
    public BaseResponse<ConfirmLoanResponse> insertLoanOrderAndSendMq(ConfirmLoanReq confirmLoanReq ,BaseResponse<ConfirmLoanResponse> response ,ConfirmLoanResponse confirmLoanResponse){//} throws Exception {
        // 1.创建订单
        LoanOrder loanOrder = new LoanOrder();
        BeanUtils.copyProperties(confirmLoanReq,loanOrder);
        loanOrder.setUserType(confirmLoanReq.getUserType());
        loanOrder.setOrderAmount(confirmLoanReq.getOrderAmount().multiply(new BigDecimal(100)).intValue());
        loanOrder.setFeeAmount(confirmLoanReq.getFeeAmount().multiply(new BigDecimal(100)).intValue());
        loanOrder.setUkToken(Globals.zeroStr);
        loanOrder.setPaymentAmount(confirmLoanReq.getPaymentAmount().multiply(new BigDecimal(100)).intValue());
        loanOrder.setRepaymentAmount(confirmLoanReq.getRepaymentAmount().multiply(new BigDecimal(100)).intValue());
        loanOrder.setInterestAmount(confirmLoanReq.getInterestAmount().multiply(new BigDecimal(100)).intValue());
        loanOrder.setBizSeqNo(UniqueIdUtil.getLoanOrderUniqueId());
        loanOrder.setOrderType(LoanOrderTypeEnum.LOAN.getCode());
        loanOrder.setStatus(LoanOrderStatusEnum.NEW.getCode());
        loanOrder.setProductCategory(confirmLoanReq.getProductCategory());
        loanOrder.setBankName(confirmLoanReq.getBankName());
        loanOrder.setLastFourBankCardNo(confirmLoanReq.getLastFourBankCardNo());
        loanOrderMapper.insert(loanOrder);
        logger.info("生成贷款订单:{}", loanOrder);

        // 2.创建trace记录
        //Trace trace = new Trace(loanOrder.getTraceNo(), TraceOrderEventEnum.LOAN_CONFIRM.getCode(),
        //        TraceOrderEventEnum.LOAN_CONFIRM.getText(), new Date(), GsonUtil.toGson(confirmLoanReq));
        //加入接口幂等性校验
        //idempotentService.idempotentCheck(IdempotentTypeEnum.CONFIRM_LOAN, trace);

        //traceMapper.insert(trace);
        //logger.info("生成trace表:{}", trace);

        confirmLoanResponse.setOrderId(String.valueOf(loanOrder.getId()));
        response.setData(confirmLoanResponse);
        //给用户发送短信，通知申请提交成功--短信在cashman-app中发送
        //        Boolean isSendShortMsgSuccess = smsService.sendSms(new SmsDto(loanOrder.getBizSeqNo(), loanOrder.getUserPhone(), SmsConstant.SMS_APPLY_SUCCESS_ID,
//                "",null==loanOrder.getMerchantNo()?"":loanOrder.getMerchantNo()));
//        if(!isSendShortMsgSuccess){
//            logger.warn("Send short msg for app-success is failed,bizSeqNo is "+loanOrder.getBizSeqNo());
//        }
        //发送消息通知cashman-app生成还款业务订单和还款计划
        mqMessageService.sendMessage(new MqMessage(JsonUtils.toJSONString(loanOrder),
                QueueConst.ORDER_CONFIRM_SUCCESS_QUEUE));

        return response;
    }
    @Override
    public Long getUserIdByOrderBizSeqNo(String bizSeqNo){
        return loanOrderMapper.getUserIdByOrderBizSeqNo(bizSeqNo);
    }

    @Override
    public PageInfo<UnfreezeOrdersResponse> getUnfreezeOrderList(UnfreezeOrdersReq unfreezeOrdersReq) {
        logger.info("params:{}", unfreezeOrdersReq.toString());
        //待解冻订单状态--人工审核失败 -风控审核失败
        String[] status  = new String[]{ LoanOrderStatusEnum.REFUSED.getCode() , LoanOrderStatusEnum.MANUAL_REFUSED.getCode() };
        PageHelper.startPage(unfreezeOrdersReq.getPageNum(), unfreezeOrdersReq.getPageSize());
        List<UnfreezeOrdersResponse> unfreezeOrdersResponses = loanOrderMapper.selectUnfreezeOrders(unfreezeOrdersReq.getId(),status,unfreezeOrdersReq.getUnfreezeTime());
        Page<UnfreezeOrdersResponse> page = (Page<UnfreezeOrdersResponse>) unfreezeOrdersResponses;
        logger.info("result:{}", page);
        return page.toPageInfo();
    }
}
