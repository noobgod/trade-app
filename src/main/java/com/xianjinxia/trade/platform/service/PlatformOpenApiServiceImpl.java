package com.xianjinxia.trade.platform.service;

import com.alibaba.fastjson.JSON;
import com.dianping.cat.common.EventInfo;
import com.dianping.cat.utils.CatUtils;
import com.xianjinxia.trade.app.response.BaseResponse;
import com.xianjinxia.trade.platform.dto.TrdPlatformOrderAuditDto;
import com.xianjinxia.trade.platform.request.BindCardResultReq;
import com.xianjinxia.trade.platform.request.LoanOrderStatusReq;
import com.xianjinxia.trade.platform.request.ReceiveOrderAuditReq;
import com.xianjinxia.trade.platform.status.BankCodeEnum;
import com.xianjinxia.trade.platform.status.BindCardStatus;
import com.xianjinxia.trade.platform.status.LoanOrderStatus;
import com.xianjinxia.trade.platform.status.OrderAuditResults;
import com.xianjinxia.trade.platform.status.PlatformTraceOrderEventEnum;
import com.xianjinxia.trade.shared.domain.Trace;
import com.xianjinxia.trade.shared.domain.TrdPlatformLoanOrder;
import com.xianjinxia.trade.shared.enums.IdempotentTypeEnum;
import com.xianjinxia.trade.shared.exception.ServiceException;
import com.xianjinxia.trade.shared.exception.SqlUpdateException;
import com.xianjinxia.trade.shared.idempotent.IdempotentService;
import com.xianjinxia.trade.shared.mapper.TraceMapper;
import com.xianjinxia.trade.shared.mapper.TrdPlatformLoanOrderMapper;
import com.xianjinxia.trade.shared.utils.JsonUtils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Component
public class PlatformOpenApiServiceImpl implements PlatformOpenApiService {

    private static final Logger logger = LoggerFactory.getLogger(PlatformOpenApiServiceImpl.class);

    @Autowired
    private TrdPlatformLoanOrderMapper trdPlatformLoanOrderMapper;

    @Autowired
    private TraceMapper traceMapper;

    @Autowired
    private IdempotentService idempotentService;

    @Autowired
    private PlatformLoanOrderService platformLoanOrderService;


    /**
     * 获取审核结果-第三方回调--更新订单状态并落trace表
     *
     * @param receiveOrderAuditReq
     */
    @Override
    @Transactional
    public void loanAuditCallback(ReceiveOrderAuditReq receiveOrderAuditReq) {
        logger.info("100020接收到的参数：{}", receiveOrderAuditReq.toString());
        EventInfo eventInfo = new EventInfo();
        eventInfo.setEventType("100020");
        eventInfo.setMessage("loan audit callback");
        eventInfo.put("orderNo", receiveOrderAuditReq.getOrderNo());
        CatUtils.info(eventInfo);

        if(!receiveOrderAuditReq.paramCheck()){
            throw new ServiceException(BaseResponse.ResponseCode.PARAM_CHECK_FAIL.getValue(), BaseResponse.ResponseCode.PARAM_CHECK_FAIL.getDescription());
        }
        if (!OrderAuditResults.APPROVE.getCode().equals(receiveOrderAuditReq.getConclusion())&&(!OrderAuditResults.REFUSED.getCode().equals(receiveOrderAuditReq.getConclusion()))) {
            logger.info("100020请求状态参数错误，不在范围内,该状态status:{}",receiveOrderAuditReq.getConclusion());
            throw new ServiceException(BaseResponse.ResponseCode.BIZ_CHECK_FAIL.getValue(), "请求状态参数错误，不在范围内");
        }
        idempotentService.idempotentCheck(IdempotentTypeEnum.RECEIVE_ORDER_AUDIT, receiveOrderAuditReq);
        //根据订单编号查询订单信息
//        String orderNo = receiveOrderAuditReq.getOrderNo();
//        TrdPlatformOrderAuditDto loanOrder = trdPlatformLoanOrderMapper.selectStatusByOrderNo(orderNo);
//        if (!LoanOrderStatus.CONFIRM_PUSH_SUCCESS.getValue().equals(loanOrder.getStatus())) {
//            logger.info("该借款订单已经审核过,bizSeqNo:{},该订单状态status:{}", receiveOrderAuditReq.getOrderNo(), loanOrder.getStatus());
//            return;
//        }
        // 判断审核结果是否通过--预留，暂时注释
//        if (OrderAuditResults.APPROVE.getCode().equals(receiveOrderAuditReq.getConclusion())) {
//            platformLoanOrderService.updateOrderStatusAndTime(orderNo, LoanOrderStatus.APPROVED, LoanOrderStatus.CONFIRM_PUSH_SUCCESS,new Date(receiveOrderAuditReq.getApprovalTime()*1000), null == (receiveOrderAuditReq.getRemark()) ? LoanOrderStatus.APPROVED.getDesc() : receiveOrderAuditReq.getRemark());
//            platformLoanOrderService.insertTrace(loanOrder.getTraceNo(), PlatformTraceOrderEventEnum.PLATFORM_RISK_DATA_PASS, JSON.toJSONString(receiveOrderAuditReq));
//        } else if (OrderAuditResults.REFUSED.getCode().equals(receiveOrderAuditReq.getConclusion())) {
//            platformLoanOrderService.updateOrderStatusAndTime(orderNo, LoanOrderStatus.REFUSED, LoanOrderStatus.CONFIRM_PUSH_SUCCESS,new Date(receiveOrderAuditReq.getApprovalTime()*1000), null == (receiveOrderAuditReq.getRemark()) ? LoanOrderStatus.REFUSED.getDesc() : receiveOrderAuditReq.getRemark());
//            platformLoanOrderService.insertTrace(loanOrder.getTraceNo(), PlatformTraceOrderEventEnum.PLATFORM_RISK_DATA_FAIL, JSON.toJSONString(receiveOrderAuditReq));
//        }
    }


    /**
     * 第三方机构调用，用于更新订单状态
     */
    @Override
    @Transactional
    public void syncOrderStatus(LoanOrderStatusReq loanOrderStatusReq) {
        logger.info("100050 syncOrderStatus入参为:{}", loanOrderStatusReq);
        if (!loanOrderStatusReq.paramCheck()) {
            throw new ServiceException(BaseResponse.ResponseCode.PARAM_CHECK_FAIL.getValue(), BaseResponse.ResponseCode.PARAM_CHECK_FAIL.getDescription());
        }
        //加入接口幂等性校验
        idempotentService.idempotentCheck(IdempotentTypeEnum.SYNC_ORDER_STATUS, loanOrderStatusReq);

        TrdPlatformOrderAuditDto platformOrderAuditDto = trdPlatformLoanOrderMapper.selectStatusByOrderNo(loanOrderStatusReq.getOrder_no());
        if(platformOrderAuditDto == null) {
        	logger.info("platformOrderAuditDto is null,orderNo={}",loanOrderStatusReq.getOrder_no());
        	throw new ServiceException(BaseResponse.ResponseCode.PARAM_CHECK_FAIL.getValue(), "订单号为：【"+loanOrderStatusReq.getOrder_no()+"】的订单不存在");
        }
        LoanOrderStatus loanOrderStatus = LoanOrderStatus.getEnumByPartnerOrderStatus(loanOrderStatusReq.getOrder_status());
        if (loanOrderStatus == null) {
            throw new ServiceException(BaseResponse.ResponseCode.BIZ_CHECK_FAIL.getValue(), "订单状态值不正确，无此订单状态");
        }
       
        logger.info("订单状态修改变更信息：orderNo={},oldStatus={},newStatus={}",loanOrderStatusReq.getOrder_no(),platformOrderAuditDto.getStatus(),loanOrderStatus.getValue());
        if(StringUtils.equals(platformOrderAuditDto.getStatus(), loanOrderStatus.getValue())) {
        	logger.info("orderNo={},status={},状态已经为最新状态",loanOrderStatusReq.getOrder_no(),loanOrderStatus.getValue());
        	return;
        }
        
        //校验订单的前置状态
        checkPreOrderStatus(loanOrderStatusReq.getOrder_status(),loanOrderStatus.getPreStatus(),platformOrderAuditDto.getStatus());

        if (LoanOrderStatus.isNeedRecordTrace(loanOrderStatus.getValue())) {
        	PlatformTraceOrderEventEnum platformTraceOrderEventEnum = PlatformTraceOrderEventEnum.getEnumByCode(loanOrderStatus.getTraceStatus().getCode());
            int insertNum = traceMapper.insert(new Trace(platformOrderAuditDto.getTraceNo(), platformTraceOrderEventEnum.getCode(), platformTraceOrderEventEnum.getText(), new Date(), JsonUtils.toJSONString(loanOrderStatusReq)));
            if (insertNum <= 0) {
                logger.error("插入trace表失败,订单order_no:{}，traceNo:{}", loanOrderStatusReq.getOrder_no(), platformOrderAuditDto.getTraceNo());
                throw new SqlUpdateException("插入trace表失败");
            }
        }
        int updateNum = trdPlatformLoanOrderMapper.updateLoanOrderStatus(loanOrderStatusReq.getOrder_no(), loanOrderStatus.getValue(), platformOrderAuditDto.getStatus(), new Date(loanOrderStatusReq.getUpdate_time()*1000L), null);
        if (updateNum <= 0) {
            logger.error("更新借款订单状态失败,订单order_no:{}", loanOrderStatusReq.getOrder_no());
            throw new SqlUpdateException("更新借款订单状态失败");
        }
    }

    
    /***
     * 校验订单的前置状态
     * @param newStatus
     * @param preStatusList
     * @param oldStatus
     */
    private void checkPreOrderStatus(String newStatus,List<LoanOrderStatus> preStatusList,String oldStatus) {
    	boolean flag = false;
    	for(LoanOrderStatus loanOrderStatus:preStatusList) {
    		if(StringUtils.equals(loanOrderStatus.getValue(), oldStatus)) {
    			flag = true;
    		}
    	}
    	if(!flag) {
    		throw new ServiceException(BaseResponse.ResponseCode.BIZ_CHECK_FAIL.getValue(), "【"+newStatus+"】状态的前置状态应该为："+preStatusList+"，但是实际状态为【"+oldStatus+"】");
    	}

	}


    /**
     * 第三方通知平台 绑卡结果
     *
     * @param bindCardResultReq
     */
    @Override
    @Transactional
    public void bindCardRecall(BindCardResultReq bindCardResultReq) {
        logger.info("100010 bindCardRecall入参为:{}", bindCardResultReq);
        EventInfo eventInfo = new EventInfo();
        eventInfo.setEventType("100010");
        eventInfo.setMessage("bind card");
        eventInfo.put("orderNo", bindCardResultReq.getOrder_no());
        CatUtils.info(eventInfo);
        if (!bindCardResultReq.paramCheck()) {
            throw new ServiceException(BaseResponse.ResponseCode.PARAM_CHECK_FAIL.getValue(), BaseResponse.ResponseCode.PARAM_CHECK_FAIL.getDescription());
        }
        idempotentService.idempotentCheck(IdempotentTypeEnum.BIND_CARD_RECALL, bindCardResultReq);
        TrdPlatformLoanOrder order = trdPlatformLoanOrderMapper.selectByOrderNo(bindCardResultReq.getOrder_no());
        if (order == null) {
            logger.error("没找到该订单号{}的订单,订单为空", bindCardResultReq.getOrder_no());
            throw new ServiceException(BaseResponse.ResponseCode.BIZ_CHECK_FAIL.getValue(), "没找到该订单号" + bindCardResultReq.getOrder_no() + "的订单,订单为空");
        }
        if (order.getStatus().equals(LoanOrderStatus.CONFIRM_PRE.getValue())){
            logger.info("该订单号{}的订单已绑卡成功了, 忽略此次操作", bindCardResultReq.getOrder_no());
            return;
        }
        if (bindCardResultReq.getBind_status() == BindCardStatus.FAILURE.getCode()) {
        	platformLoanOrderService.updateOrderStatusAndInsertTrace(bindCardResultReq.getOrder_no(), LoanOrderStatus.FAIL,
                    LoanOrderStatus.NEW_PUSH_SUCCESS, "绑卡失败原因:" + bindCardResultReq.getFail_reason(), PlatformTraceOrderEventEnum.PLATFORM_BIND_CARD_FAIL, bindCardResultReq.toString());
        	logger.info("绑卡失败原因:" + bindCardResultReq.getFail_reason());
        	return;
        }
        if (bindCardResultReq.getBind_status() == BindCardStatus.SUCCESS.getCode()) {
            platformLoanOrderService.updateOrderStatusAndInsertTrace(bindCardResultReq.getOrder_no(), LoanOrderStatus.CONFIRM_PRE,
                    LoanOrderStatus.NEW_PUSH_SUCCESS, null, PlatformTraceOrderEventEnum.PLATFORM_BIND_CARD_SUCCESS, bindCardResultReq.toString());
            order.setBankCardNoLastFour(bindCardResultReq.getBank_card());
            String bankName = BankCodeEnum.getEnumByCode(bindCardResultReq.getBank_code());
            if(bankName == null){
                throw new ServiceException(BaseResponse.ResponseCode.BIZ_CHECK_FAIL.getValue(), "没有对应的" + bindCardResultReq.getOrder_no() + "的订单,订单为空");
            }
            order.setBankName(bankName);
            order.setUserBankCardId(bindCardResultReq.getBank_card_id());
            order.setStatus(LoanOrderStatus.CONFIRM_PRE.getValue());
            int result = trdPlatformLoanOrderMapper.updateByPrimaryKeySelective(order);
            if (result != 1) {
                throw new SqlUpdateException("update  loanOrder bind card status error,orderNo:" + bindCardResultReq.getOrder_no());
            }
            return;
        }
    }
}
