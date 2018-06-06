package com.xianjinxia.trade.app.service.impl;

import java.util.Date;
import java.util.List;

import com.xianjinxia.trade.shared.domain.Trace;
import com.xianjinxia.trade.shared.enums.*;
import com.xianjinxia.trade.shared.exception.SqlUpdateException;
import com.xianjinxia.trade.shared.mapper.LoanOrderMapper;
import com.xianjinxia.trade.shared.mapper.PaymentOrderMapper;
import com.xianjinxia.trade.app.service.IMqMessageService;
import com.xianjinxia.trade.shared.mapper.TraceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.xianjinxia.trade.shared.constant.Globals;
import com.xianjinxia.trade.shared.constant.QueueConst;
import com.xianjinxia.trade.shared.domain.FinanceChannelConf;
import com.xianjinxia.trade.shared.domain.LoanOrder;
import com.xianjinxia.trade.shared.domain.PaymentOrder;
import com.xianjinxia.trade.shared.mapper.FinanceChannelConfMapper;
import com.xianjinxia.trade.shared.remote.OldCashmanRemoteService;
import com.xianjinxia.trade.app.request.BankInfoReq;
import com.xianjinxia.trade.app.request.LoanParamsReq;
import com.xianjinxia.trade.app.response.BankInfoResponse;
import com.xianjinxia.trade.app.service.IPayCenterService;
import com.xianjinxia.trade.shared.utils.JsonUtils;
import com.xianjinxia.trade.shared.utils.PaymentSignUtil;
import com.xianjinxia.trade.shared.utils.UniqueIdUtil;
import com.xjx.mqclient.pojo.MqMessage;

@Service
public class PayCenterServiceImpl implements IPayCenterService {


    private static final Logger logger = LoggerFactory.getLogger(PayCenterServiceImpl.class);

    @Autowired
    private FinanceChannelConfMapper financeChannelConfMapper;

    @Autowired
    private IMqMessageService mqMessageService;

    @Autowired
    private OldCashmanRemoteService oldCashmanRemoteService;

    @Autowired
    private PaymentOrderMapper paymentOrderMapper ;

    @Autowired
    private LoanOrderMapper loanOrderMapper;

    @Autowired
    private TraceMapper traceMapper;

    @Override
    @Transactional
    public void paymentRequest(LoanOrder loanOrder) {
        // 5.修改订单状态为放款中
        // 插入trace表
        traceMapper.insert(new Trace(loanOrder.getTraceNo(), TraceOrderEventEnum.PAYMENTING.getCode(),
                TraceOrderEventEnum.PAYMENTING.getText(), new Date(), JsonUtils.toJSONString(loanOrder)));
        // 更新订单状态为放款中
        int count = loanOrderMapper.updateOrderStatus(loanOrder.getBizSeqNo(),LoanOrderStatusEnum.LOANING.getCode(),LoanOrderStatusEnum.LOANING.getCode());
        if (count != 1) {
            logger.error("更新借款订单状态失败,订单bizSeqNo:[{}]", loanOrder.getBizSeqNo());
            throw new SqlUpdateException("更新借款订单状态失败");
        }

        // 1.查询支付路由信息
        FinanceChannelConf financeChannelConf = getFinanceChannelConf(loanOrder);

        // 2.获取银行卡信息
        BankInfoResponse bankInfo = getBankCardInfo(loanOrder.getUserId(), loanOrder.getUserBankCardId());

        // 3. 支付订单表插入记录
        PaymentOrder paymentOrder = createPaymentOrder(loanOrder, financeChannelConf, bankInfo);

        paymentOrderMapper.insert(paymentOrder);

        logger.info("创建支付请求订单：{}", paymentOrder);

        // 4. 调用支付中心
        LoanParamsReq payCenterRequestParam = createPayCenterRequestParam(loanOrder, paymentOrder,
                bankInfo, financeChannelConf);
        mqMessageService.sendMessage(new MqMessage(JsonUtils.toJSONString(payCenterRequestParam),
                QueueConst.TRADE_QUEUE));



    }

    /**
     * 从cashman 获取银行卡信息
     */
    private BankInfoResponse getBankCardInfo(Long userId, Long userBankCardId) {
        BankInfoReq info = new BankInfoReq();
        info.setUserBankCardId(userBankCardId);
        info.setUserId(userId);
        BankInfoResponse resBankInfo = oldCashmanRemoteService.getBankInfoByBankIdAndUserId(info);
        return resBankInfo;
    }

    private FinanceChannelConf getFinanceChannelConf(LoanOrder loanOrder) {
        List<FinanceChannelConf> financeChannelConfs = financeChannelConfMapper.selectAll();
        FinanceChannelConf financeChannelConf = financeChannelConfs.get(0);
        logger.info("选择放款资金渠道[{}]", financeChannelConf);
        // TODO 替换为匹配的渠道
        return financeChannelConf;
    }

    private PaymentOrder createPaymentOrder(LoanOrder loanOrder,
            FinanceChannelConf financeChannelConf, BankInfoResponse bankInfo) {
        PaymentOrder order = new PaymentOrder();
        String createSerialNo = UniqueIdUtil.getPaymentOrderUniqueId();
        order.setPaymentAmount(loanOrder.getPaymentAmount());
        order.setPaymentSeqNo(createSerialNo);
        order.setCreatedUser("sys");
        order.setDataValid(DataValidEnum.DATA_VALID_YES.getCode());
        order.setFinanceChannelFlag(financeChannelConf.getChannelFlag().toString());
        order.setRcvBankCardNo(bankInfo.getCardNo());
        order.setRcvBankUnionNo(bankInfo.getBankNumber());
        order.setRcvBankAcctName(bankInfo.getOpenName());
        order.setTrdOrderId(loanOrder.getId());
        order.setPaymentOrderType(PaymentOrderTypeEnum.TRADE.getCode());
        order.setPaymentRemark(PaymentOrderTypeEnum.TRADE.getValue());
        order.setStatus(PaymentOrderStatusEnum.DOING.getStatus());
        order.setPaymentChannel(PaymentOrderChannelEnum.PAYMENT_CENTER.getCode());
        return order;
    }

    private LoanParamsReq createPayCenterRequestParam(LoanOrder loanOrder,
            PaymentOrder paymentOrder, BankInfoResponse bankInfo,
            FinanceChannelConf financeChannelConf) {
        LoanParamsReq loanParams = new LoanParamsReq();
        loanParams.setBizId(paymentOrder.getPaymentSeqNo());
        loanParams.setBizType(Globals.TRADE_BIZ_TYPE);
        loanParams.setRequestSource(Globals.TRADE_SOURCE_ID);
        loanParams.setLoanAmount(paymentOrder.getPaymentAmount());
        loanParams.setBankCardNo(bankInfo.getCardNo());
        loanParams.setBankCode(bankInfo.getBankNumber());
        loanParams.setBankName(bankInfo.getBankName());
        loanParams.setCardHolder(bankInfo.getOpenName());
        loanParams.setPhoneNo(bankInfo.getPhone());
        loanParams.setLoanUser(loanOrder.getUserId().toString());
        loanParams.setRouteStrategy(financeChannelConf.getChannelFlag());
        String sign = PaymentSignUtil.sign(paymentOrder.getPaymentSeqNo(),
                        paymentOrder.getPaymentAmount(), bankInfo.getCardNo());
        loanParams.setSign(sign);
        return loanParams;
    }
}
