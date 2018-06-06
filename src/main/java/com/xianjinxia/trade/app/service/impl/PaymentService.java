package com.xianjinxia.trade.app.service.impl;

import com.alibaba.fastjson.JSON;
import com.xianjinxia.trade.shared.constant.LoanOrderConstants;
import com.xianjinxia.trade.shared.exception.ServiceException;
import com.xianjinxia.trade.shared.exception.SqlUpdateException;
import com.xianjinxia.trade.shared.constant.QueueConst;
import com.xianjinxia.trade.shared.domain.LoanOrder;
import com.xianjinxia.trade.shared.domain.PaymentOrder;
import com.xianjinxia.trade.shared.domain.Trace;
import com.xianjinxia.trade.app.dto.QuotaGiveBackDto;
import com.xianjinxia.trade.shared.enums.LoanOrderStatusEnum;
import com.xianjinxia.trade.shared.enums.PaymentOrderStatusEnum;
import com.xianjinxia.trade.shared.enums.TraceOrderEventEnum;
import com.xianjinxia.trade.shared.mapper.TraceMapper;
import com.xianjinxia.trade.app.request.PaymentResult;
import com.xianjinxia.trade.shared.mapper.LoanOrderMapper;
import com.xianjinxia.trade.shared.mapper.PaymentOrderMapper;
import com.xianjinxia.trade.app.service.IMqMessageService;
import com.xianjinxia.trade.app.service.IPaymentService;
import com.xianjinxia.trade.shared.service.ISmsService;
import com.xjx.mqclient.pojo.MqMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by MJH on 2017/9/1.
 */
@Service
public class PaymentService implements IPaymentService {

    private static Logger logger = LoggerFactory.getLogger(PaymentService.class);

    @Autowired
    private LoanOrderMapper loanOrderMapper;

    @Autowired
    private PaymentOrderMapper paymentOrderMapper;

    @Autowired
    private TraceMapper traceMapper;

    @Autowired
    private IMqMessageService mqMessageService;

    @Override
    @Transactional
    public void processPayResult(PaymentResult paymentResult) {


//        PaymentOrder paymentOrder = paymentOrderMapper.getLoanOrderIdAndStatusByPaymentSeqNo(paymentResult.getPaymentOrderSeqNo());
//        //若状态已经被修改，说明之前已经处理过
//        if(!PaymentOrderStatusEnum.DOING.getStatus().equals(paymentOrder.getStatus())){
//            throw new IdempotentException("request has already process");
//        }
        logger.info("支付中心放款成功后请求参数为："+paymentResult.toString());
        //过滤掉sourceId非大额的请求
        if(null==paymentResult.getSourceId()||("".equals(paymentResult.getSourceId()))|| !(LoanOrderConstants.APPLICATION_PAYMENT_SOURCE.equals(paymentResult.getSourceId()))){
            logger.info("非大额请求请求，sourceId为："+
        null == paymentResult.getSourceId()?"is null":paymentResult.getSourceId());
            return;
        }
        LoanOrder loanOrder = loanOrderMapper.selectByBizNo(paymentResult.getPaymentOrderSeqNo());

        //状态码若为成功
        if (PaymentResult.CodeEnum.SUCCESS.getCode().equals(paymentResult.getCode())) {
            handlePaymentSuccess(paymentResult, loanOrder.getId());
        } else if(PaymentResult.CodeEnum.FAIL.getCode().equals(paymentResult.getCode())) {
            handlePaymentFail(paymentResult, loanOrder.getId());
        }
    }

    private void handlePaymentFail(PaymentResult paymentResult, Long loanOrderId) {
        updateDataByPayment(paymentResult, loanOrderId, LoanOrderStatusEnum.LOAN_FAIL.getCode(), PaymentOrderStatusEnum.FAIL.getStatus());

        LoanOrder loanOrder = loanOrderMapper.getUserMoneyById(loanOrderId); //查询出归还额度所需的数据

        Trace trace = new Trace();
        trace.setOrderEvent(TraceOrderEventEnum.PAYMENT_FAIL.getCode());
        trace.setEventText(TraceOrderEventEnum.PAYMENT_FAIL.getText());
        trace.setCreatedTime(new Date());
        trace.setTraceNo(loanOrder.getTraceNo());
        trace.setEventTime(paymentResult.getBankPayTime());
        traceMapper.insert(trace); //插入追踪表 记录


        //发送MQ消息
        QuotaGiveBackDto quotaGiveBackDto = new QuotaGiveBackDto(loanOrder.getUserId(),
                loanOrder.getOrderAmount(), loanOrder.getTraceNo(),loanOrder.getProductCategory());
        //若是失败，调用Mq client客户端发送消息给 userapp 归还额度
        mqMessageService.sendMessage(new MqMessage(JSON.toJSONString(quotaGiveBackDto),QueueConst.RETURN_QUOTA));

        logger.info("放款失败恢复额度,{}",quotaGiveBackDto);

        //短信通知放款失败--放到cashman-app中
//        Boolean isSendShortMsgSuccess =smsService.sendSms(new SmsDto(loanOrder.getBizSeqNo(), loanOrder.getUserPhone(), SmsConstant.SMS_LOAN_FAILED_ID,
//                String.valueOf(loanOrder.getOrderAmount()),null==loanOrder.getMerchantNo()?"":loanOrder.getMerchantNo()));
//        if(!isSendShortMsgSuccess){
//            logger.error("Send short msg for loan-failed failed,bizSeqNo is "+loanOrder.getBizSeqNo());
//        }
    }

    private void handlePaymentSuccess(PaymentResult paymentResult, Long loanOrderId) {
        updateDataByPayment(paymentResult, loanOrderId, LoanOrderStatusEnum.LOAN_SUCCESS.getCode(), PaymentOrderStatusEnum.SUCCESS.getStatus());

        String traceNo = loanOrderMapper.getTraceNoById(loanOrderId);
        Trace trace = new Trace();
        trace.setOrderEvent(TraceOrderEventEnum.PAYMENT_SUCCESS.getCode());
        trace.setEventText(TraceOrderEventEnum.PAYMENT_SUCCESS.getText());
        trace.setCreatedTime(new Date());
        trace.setTraceNo(traceNo);
        traceMapper.insert(trace); //插入追踪表 记录

        LoanOrder loanOrder = loanOrderMapper.getById(loanOrderId);
        int count = loanOrderMapper.updateOrderLoanTime(loanOrder.getId(),paymentResult.getBankPayTime());
        if(count != 1){
            throw new ServiceException("更新  id 为    "+loanOrder.getId()+"   订单表实际放款时间 失败");
        }
//        PaymentMessage paymentMessage = new PaymentMessage();
//        BeanPropertyUtils.copyProperties(loanOrder, paymentMessage);
//        paymentMessage.setPaymentTime(paymentResult.getBankPayTime());
//        paymentMessage.setTrdLoanOrderId(loanOrder.getId());
//        paymentMessage.setLoanCaptionInfo(paymentResult.getLoanCaptionInfo());
        //发送MQ消息
//        mqMessageService.sendMessage(new MqMessage(JSON.toJSONString(paymentMessage),QueueConst.PAYMENT_SUCESS_RESULT_QUEUE));

//        //短信通知放款成功--放到cashman-app中
//        Boolean isSendShortMsgSuccess =smsService.sendSms(new SmsDto(loanOrder.getBizSeqNo(), loanOrder.getUserPhone(), SmsConstant.SMS_LOAN_SUCCESS_ID,
//                loanOrder.getLastFourBankCardNo(),null==loanOrder.getMerchantNo()?"":loanOrder.getMerchantNo()));
//        if(!isSendShortMsgSuccess){
//            logger.error("Send short msg for loan-success failed,bizSeqNo is "+loanOrder.getBizSeqNo());
//        }

    }

    /**
     * 放款后，根据结果 更新 loan_order 和 payment_order表
     * @param result                支付中心返回的结果
     * @param loanOrderId           loanOrder表 ID
     * @param newLoanOrderStatus    更新后的loanOrder表状态
     * @param newPaymentOrderStatus 更新后的paymentOrder表状态
     */
    private void updateDataByPayment(PaymentResult result, Long loanOrderId, String newLoanOrderStatus,
                                     String newPaymentOrderStatus) {
        int updateLoanOrderNo = loanOrderMapper.updateStatByIdAndStat(newLoanOrderStatus,
                loanOrderId, LoanOrderStatusEnum.LOANING.getCode());
        if (updateLoanOrderNo != 1) {
            throw new SqlUpdateException(String.format("update loanOrder fail,loan_order id:%d", loanOrderId));
        }

//        PaymentOrder paymentOrder = getPaymentOrder(result);
//        int updatePaymentOrderNo = paymentOrderMapper.updateByPaymentSeqNoAndStat(newPaymentOrderStatus, paymentOrder);
//        if (updatePaymentOrderNo != 1) {
//            throw new SqlUpdateException(String.format("update payment_order fail,payment_order seq_no:%s", result.getPaymentOrderNo()));
//        }

    }


    /**
     * 通过支付中心返回的结果 构建PaymentOrder对象，paymentOrder的状态是 放款中
     * @param result paymentResult
     * @return paymentOrder
     */
    private PaymentOrder getPaymentOrder(PaymentResult result) {
        PaymentOrder paymentOrder = new PaymentOrder();
        paymentOrder.setPaymentSeqNo(result.getPaymentOrderSeqNo());
        paymentOrder.setStatus(PaymentOrderStatusEnum.DOING.getStatus());
        paymentOrder.setRetMsg(result.getMsg());
        paymentOrder.setPaymentOrderNo(result.getPaymentOrderNo());
        paymentOrder.setPaymentTime(result.getBankPayTime());
        return paymentOrder;
    }
}
