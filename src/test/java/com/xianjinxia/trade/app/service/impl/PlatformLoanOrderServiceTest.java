package com.xianjinxia.trade.app.service.impl;

import com.xianjinxia.trade.shared.domain.FinanceChannelConf;
import com.xianjinxia.trade.shared.domain.LoanOrder;
import com.xianjinxia.trade.shared.domain.PaymentOrder;
import com.xianjinxia.trade.shared.domain.Trace;
import com.xianjinxia.trade.app.dto.TradeDto;
import com.xianjinxia.trade.shared.enums.LoanOrderStatusEnum;
import com.xianjinxia.trade.shared.enums.LoanOrderTypeEnum;
import com.xianjinxia.trade.shared.enums.TraceOrderEventEnum;
import com.xianjinxia.trade.shared.mapper.FinanceChannelConfMapper;
import com.xianjinxia.trade.shared.mapper.LoanOrderMapper;
import com.xianjinxia.trade.shared.mapper.PaymentOrderMapper;
import com.xianjinxia.trade.shared.mapper.TraceMapper;
import com.xianjinxia.trade.shared.remote.OldCashmanRemoteService;
import com.xianjinxia.trade.app.request.ApplyLoanReq;
import com.xianjinxia.trade.app.response.BankInfoResponse;
import com.xianjinxia.trade.app.service.ILoanOrderService;
import com.xianjinxia.trade.shared.utils.GsonUtil;
import com.xianjinxia.trade.shared.utils.UniqueIdUtil;
import com.xjx.mqclient.service.MqClient;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;

@Rollback
public class PlatformLoanOrderServiceTest {

    @Mock
    private ILoanOrderService loanOrderService;
    @Mock
    private LoanOrderMapper loanOrderMapper;
    @Mock
    private TraceMapper traceMapper;
    @Mock
    private FinanceChannelConfMapper financeChannelConfMapper;
    @Mock
    private OldCashmanRemoteService oldCashmanRemoteService;
    @Mock
    private PaymentOrderMapper paymentOrderMapper;
    @Mock
    private MqClient mqClient;


    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @Transactional
    public void reciveRiskDataTest() {
        TradeDto tradeDto = new TradeDto();
        tradeDto.setAssetOrderId("lo20171017185813889tx");
        tradeDto.setCode("0000");
        LoanOrder loanOrder = new LoanOrder();
        loanOrder.setId(71l);
        loanOrder.setUserId(428l);
        loanOrder.setBizSeqNo("lo20171017185813889tx");
        loanOrder.setOrderType(LoanOrderTypeEnum.LOAN.getCode());
        loanOrder.setOrderAmount(500000);
        loanOrder.setFeeAmount(62500);
        loanOrder.setRepaymentAmount(530000);
        loanOrder.setPaymentAmount(437500);
        loanOrder.setPeriods(6);
        loanOrder.setProductId(1l);
        loanOrder.setProductCategory(2);
        loanOrder.setUserBankCardId(307l);
        loanOrder.setStatus(LoanOrderStatusEnum.NEW.getCode());
        loanOrder.setDataValid(true);
        loanOrder.setTraceNo("tn20171017174917096tx");
        given(loanOrderMapper.selectByBizNo(anyString())).willReturn(loanOrder);
        given(traceMapper.insert(anyObject())).willReturn(1);
        given(loanOrderMapper.updateOrderStatus(anyString(), anyString(), anyString())).willReturn(
                1);
        List<FinanceChannelConf> financeChannelConfs = new ArrayList<>();
        given(financeChannelConfMapper.selectAll()).willReturn(financeChannelConfs);
        BankInfoResponse resBankInfo = new BankInfoResponse();
        resBankInfo.setBankName("中国邮政");
        resBankInfo.setCardNo("21799520012168423");
        resBankInfo.setPhone("13311112222");
        resBankInfo.setBankNumber("403100000004");
        resBankInfo.setOpenName("任广翔");
        given(oldCashmanRemoteService.getBankInfoByBankIdAndUserId(anyObject())).willReturn(
                resBankInfo);
        given(paymentOrderMapper.insert(anyObject())).willReturn(1);
        given(paymentOrderMapper.insert(anyObject())).willReturn(1);
        given(paymentOrderMapper.insert(anyObject())).willReturn(1);
        PaymentOrder paymentOrder = new PaymentOrder();
        paymentOrder.setId(1l);
        given(paymentOrderMapper.selectByTradeOrderId(anyLong(), anyInt()))
                .willReturn(paymentOrder);
        loanOrderService.loanOrderRecord(tradeDto);
    }

    public LoanOrder getLoanOrder(){
        LoanOrder loanOrder = new LoanOrder();
        loanOrder.setOrderAmount(500000);
        loanOrder.setSource("1");
        loanOrder.setUserId(428L);
        return loanOrder ;
    }



    //用户最近被拒的订单+冷却时间和当前时间对比
    public Boolean rejectOrderPassTest(String quietPeriod)  {
        Long userId =  428L ;
        String[] status  = new String[]{ LoanOrderStatusEnum.REFUSED.getCode() };
        LoanOrder loanOrder = getLoanOrder();
        loanOrder.setStatus(LoanOrderStatusEnum.REFUSED.getCode());
        given(loanOrderMapper.selectUserOrderByStatus(userId ,status)).willReturn(loanOrder);
        return true ;
    }

    @Test
    @Transactional
    public Boolean hasNonUltimateOrderTest() {
        Long userId =  428L ;
        String[] status  = new String[]{ LoanOrderStatusEnum.NEW.getCode(),
                LoanOrderStatusEnum.LOANING.getCode() };
        given(loanOrderMapper.selectNonUltimateOrder(anyLong(),anyObject())).willReturn(1);
        return true ;
    }

    @Test
    @Transactional
    public void applyLoanTest() {
        ApplyLoanReq loanReq = new ApplyLoanReq();
        loanReq.setOrderAmount(new BigDecimal("500"));
        loanReq.setPeriods("6");
        loanReq.setProductId("1");
        loanReq.setQuietPeriod("30");
        loanReq.setUserId(427L);
        // 创建trace记录
        Trace trace = new Trace();
        trace.setEventTime(new Date());
        trace.setOrderEvent(TraceOrderEventEnum.LOAN_APPLY.getCode());
        trace.setEventText(TraceOrderEventEnum.LOAN_APPLY.getText());
        trace.setTraceData(GsonUtil.toGson(loanReq));
        trace.setTraceNo(UniqueIdUtil.getTraceNoUniqueId());
    }

    @Test
    @Transactional
    public void confirmLoanOrderTest() {

    }
}
