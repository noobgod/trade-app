package com.xianjinxia.trade.platform.service;

import com.alibaba.fastjson.JSON;
import com.xianjinxia.trade.platform.dto.TrdPlatformLoanApplyDto;
import com.xianjinxia.trade.platform.dto.TrdPlatformLoanOrderDto;
import com.xianjinxia.trade.platform.request.LoanApplyRequest;
import com.xianjinxia.trade.shared.TradeApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TradeApplication.class)
@Transactional
@Rollback
public class PlatformLoanOrderServiceImplTest {

    @Autowired
    private PlatformLoanOrderService platformLoanOrderService;

    @Test
    public void loanApply() throws Exception {
        TrdPlatformLoanOrderDto platformLoanOrderDto = new TrdPlatformLoanOrderDto();
        platformLoanOrderDto.setUserId(12l);
        platformLoanOrderDto.setIsReloan(true);
        platformLoanOrderDto.setProductCode("XJX1129");
        platformLoanOrderDto.setOrderAmount(new BigDecimal(100000));
        platformLoanOrderDto.setFeeAmount(new BigDecimal(15000));
        platformLoanOrderDto.setPaymentAmount(new BigDecimal(85000));
        platformLoanOrderDto.setRepaymentAmount(new BigDecimal(110000));
        platformLoanOrderDto.setTerm(7);
        platformLoanOrderDto.setTermUnit(1);
        platformLoanOrderDto.setTerminal("02");
        LoanApplyRequest loanApplyRequest=new LoanApplyRequest();
        loanApplyRequest.setUserId(121L);
        loanApplyRequest.setIsReloan(true);
        loanApplyRequest.setProductCode("XJX1129");
        loanApplyRequest.setOrderAmount(new BigDecimal(100000));
        loanApplyRequest.setFeeAmount(new BigDecimal(15000));
        loanApplyRequest.setInterestAmount(new BigDecimal(5000));
        loanApplyRequest.setPaymentAmount(new BigDecimal(85000));
        loanApplyRequest.setRepaymentAmount(new BigDecimal(110000));
        loanApplyRequest.setApplyTerm(7);
        loanApplyRequest.setApplyTermUnit(1);
        loanApplyRequest.setMerchantCode("01");
        loanApplyRequest.setTerminal("01");
        TrdPlatformLoanApplyDto loanApplyResultDto = platformLoanOrderService.loanApply(loanApplyRequest);

        Assert.notNull(loanApplyResultDto.getOrderNo(), "loan-apply result:order no must not be null");
        Assert.notNull(loanApplyResultDto.getOrderTime(), "loan-apply result:order created time must not be null");
    }

    @Test
    public void loanApplyCallback() throws Exception {
    }


    @Test
    public void getLoanOrderApplyRetryList(){
        List<TrdPlatformLoanApplyDto> loanOrderApplyRetryList = platformLoanOrderService.getLoanOrderApplyRetryList();
        System.out.println(JSON.toJSONString(loanOrderApplyRetryList));
    }
    @Test
    public void loanAuditCallback() throws Exception {
    }

    @Test
    public void syncOrderStatus() throws Exception {
    }

    @Test
    public void getOrders() throws Exception {
    }

    @Test
    public void getUserOrders() throws Exception {
    }

    @Test
    public void confirmLoan() throws Exception {
    }

    @Test
    public void bindCardRecall() throws Exception {
    }

    @Test
    public void getLoanOrderDetail() throws Exception {
    }

    @Test
    public void getTrace() throws Exception {
    }

}