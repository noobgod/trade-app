package com.xianjinxia.trade.app.service.impl;

import com.xianjinxia.trade.shared.exception.IdempotentException;
import com.xianjinxia.trade.shared.constant.Globals;
import com.xianjinxia.trade.shared.domain.LoanOrder;
import com.xianjinxia.trade.shared.domain.PaymentOrder;
import com.xianjinxia.trade.shared.mapper.LoanOrderMapper;
import com.xianjinxia.trade.shared.mapper.PaymentOrderMapper;
import com.xianjinxia.trade.shared.mapper.TraceMapper;
import com.xianjinxia.trade.app.request.PaymentResult;
import com.xianjinxia.trade.app.service.IPaymentService;
import com.xjx.mqclient.service.MqClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.mockito.BDDMockito.*;
import java.util.Date;


@RunWith(SpringRunner.class)
@SpringBootTest
public class PaymentServiceTest {

    @Autowired
    private IPaymentService paymentService;

    @MockBean
    private MqClient mqClient;

    @MockBean
    private LoanOrderMapper loanOrderMapper;

    @MockBean
    private PaymentOrderMapper paymentOrderMapper;

    @MockBean
    private TraceMapper traceMapper;

    @Test
    @Transactional
    public void processPayResultBySuccess() throws Exception {
        PaymentResult paymentResult=new PaymentResult();
        paymentResult.setSourceId(Globals.TRADE_SOURCE_ID);
        paymentResult.setBankPayTime(new Date());
        paymentResult.setCode("1000");
        paymentResult.setMsg("放款成功");
        paymentResult.setPaymentOrderNo("paymentcenter123");
        paymentResult.setPaymentOrderSeqNo("lo123");
        paymentResult.setTradeType("01");
        paymentResult.setPaymentUpdateTime(new Date());

        PaymentOrder po=new PaymentOrder();
        po.setTrdOrderId(12345L);
        po.setStatus("01");
        given(paymentOrderMapper.getLoanOrderIdAndStatusByPaymentSeqNo(anyString())).willReturn(po);
        given(loanOrderMapper.updateStatByIdAndStat(anyString(),anyLong(),anyString())).willReturn(1);
        given(paymentOrderMapper.updateByPaymentSeqNoAndStat(anyString(),anyObject())).willReturn(1);
        given(loanOrderMapper.getTraceNoById(anyLong())).willReturn("000");
        given(traceMapper.insert(anyObject())).willReturn(1);

        given(loanOrderMapper.getById(anyLong())).willReturn(new LoanOrder());
        doNothing().when(mqClient).sendMessage(anyObject());

        paymentService.processPayResult(paymentResult);
    }

    @Test
    @Transactional
    public void processPayResultByFail() throws Exception {
        PaymentResult paymentResult=new PaymentResult();
        paymentResult.setSourceId(Globals.TRADE_SOURCE_ID);
        paymentResult.setCode("4000");
        paymentResult.setMsg("放款失败");
        paymentResult.setPaymentOrderSeqNo("lo123");
        paymentResult.setTradeType("01");

        PaymentOrder po=new PaymentOrder();
        po.setTrdOrderId(12345L);
        po.setStatus("01");
        given(paymentOrderMapper.getLoanOrderIdAndStatusByPaymentSeqNo(anyString())).willReturn(po);
        given(loanOrderMapper.updateStatByIdAndStat(anyString(),anyLong(),anyString())).willReturn(1);
        given(paymentOrderMapper.updateByPaymentSeqNoAndStat(anyString(),anyObject())).willReturn(1);
        given(loanOrderMapper.getUserMoneyById(anyLong())).willReturn(new LoanOrder());
        given(traceMapper.insert(anyObject())).willReturn(1);

        given(loanOrderMapper.getById(anyLong())).willReturn(new LoanOrder());
        paymentService.processPayResult(paymentResult);
    }


    @Test
    @Transactional
    public void processPayResultForIdempotent() throws Exception {
        PaymentResult paymentResult=new PaymentResult();
        paymentResult.setSourceId(Globals.TRADE_SOURCE_ID);
        paymentResult.setBankPayTime(new Date());
        paymentResult.setCode("1000");
        paymentResult.setMsg("放款成功");
        paymentResult.setPaymentOrderNo("paymentcenter123");
        paymentResult.setPaymentOrderSeqNo("lo123");
        paymentResult.setTradeType("01");
        paymentResult.setPaymentUpdateTime(new Date());

        PaymentOrder po=new PaymentOrder();
        po.setTrdOrderId(12345L);
        po.setStatus("02");
        given(paymentOrderMapper.getLoanOrderIdAndStatusByPaymentSeqNo(anyString())).willReturn(po);
        try{
            paymentService.processPayResult(paymentResult);
        }catch(IdempotentException ex){
            System.out.println("test pass");
        }

    }

}