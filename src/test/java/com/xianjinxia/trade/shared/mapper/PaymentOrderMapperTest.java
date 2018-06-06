package com.xianjinxia.trade.shared.mapper;

import com.xianjinxia.trade.shared.domain.PaymentOrder;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;


@RunWith(SpringRunner.class)
@SpringBootTest
public class PaymentOrderMapperTest {

    @Autowired
    private PaymentOrderMapper paymentOrderMapper;

    private String paymentSeqNo = "aaa";
    private Long trdOrderId = 123456L;
    private String status = "01";

    private Long createPaymentOrder() {
        PaymentOrder po = new PaymentOrder();
        po.setFinanceChannelFlag("aaa");
        po.setPaymentAmount(200000);
        po.setPaymentOrderType(1);
        po.setPaymentSeqNo(paymentSeqNo);
        po.setPaymentChannel("2");
        po.setRcvBankAcctName("1");
        po.setRcvBankCardNo("2");
        po.setRcvBankUnionNo("3");
        po.setStatus(status);
        po.setTrdOrderId(trdOrderId);
        po.setDataValid(true);
        paymentOrderMapper.insert(po);
        return po.getId();
    }

    @Test
    @Transactional
    public void getLoanOrderIdAndStatusByPaymentSeqNo() throws Exception {
        createPaymentOrder();
        PaymentOrder po = paymentOrderMapper.getLoanOrderIdAndStatusByPaymentSeqNo(paymentSeqNo);
        Assert.assertEquals(trdOrderId, po.getTrdOrderId());
        Assert.assertEquals(status, po.getStatus());
    }

    @Test
    @Transactional
    public void updateByPaymentSeqNoAndStat()  {
        Long id=createPaymentOrder();
        Date d=new Date();
        String orderNo="123";
        String msg="success";
        String newStatus="02";
        PaymentOrder po=new PaymentOrder();
        po.setPaymentSeqNo(paymentSeqNo);
        po.setStatus(status);
        po.setRetMsg(msg);
        po.setPaymentOrderNo(orderNo);
        po.setPaymentTime(d);
        int result=paymentOrderMapper.updateByPaymentSeqNoAndStat(newStatus,po);
        Assert.assertEquals(1,result);
        PaymentOrder  paymentOrder=paymentOrderMapper.selectByPrimaryKey(id);
        Assert.assertEquals(msg,paymentOrder.getRetMsg());
        Assert.assertEquals(orderNo,paymentOrder.getPaymentOrderNo());
        Assert.assertEquals(newStatus,paymentOrder.getStatus());
    }

}