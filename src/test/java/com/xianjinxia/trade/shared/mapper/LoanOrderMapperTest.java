package com.xianjinxia.trade.shared.mapper;

import com.xianjinxia.trade.shared.domain.LoanOrder;
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
public class LoanOrderMapperTest {

    @Autowired
    private LoanOrderMapper loanOrderMapper;

    private Long addLoanOrder(){
        LoanOrder loanOrder=new LoanOrder();
        loanOrder.setStatus("01");
        loanOrder.setBizSeqNo("123");
        loanOrder.setBankName("china bank");
        loanOrder.setCreatedTime(new Date());
        loanOrder.setCreatedUser("mjh");
        loanOrder.setDataValid(true);
        loanOrder.setFeeAmount(25000);
        loanOrder.setLastFourBankCardNo("1234");
        loanOrder.setOrderAmount(500000);
        loanOrder.setOrderType("01");
        loanOrder.setPaymentAmount(450000);
        loanOrder.setPeriods(4);
        loanOrder.setUserType(true);
        loanOrder.setProductCategory(2);
        loanOrder.setProductId(1L);
        loanOrder.setRepaymentAmount(0);
        loanOrder.setSource("a");
        loanOrder.setUkToken("123");
        loanOrder.setTraceNo("trace123");
        loanOrder.setUserName("123456");
        loanOrder.setTerminal("f");
        loanOrder.setUserBankCardId(111L);
        loanOrder.setUserId(555L);
        loanOrder.setUserPhone("123456");
        loanOrderMapper.insert(loanOrder);

        return loanOrder.getId();
    }

    @Test
    @Transactional
    public void updateStatByIdAndStat() throws Exception {
        Long id=addLoanOrder();
        int result=loanOrderMapper.updateStatByIdAndStat("02",id,"01");
        LoanOrder lo=loanOrderMapper.selectByPrimaryKey(id);
        Assert.assertEquals(1L,result);
        Assert.assertEquals("02",lo.getStatus());
    }

    @Test
    @Transactional
    public void selectNonUltimateOrder() throws Exception {
        Long id=addLoanOrder();
        int result=loanOrderMapper.selectNonUltimateOrder(555L , new String[]{"01"} );
        LoanOrder lo=loanOrderMapper.selectByPrimaryKey(id);
        Assert.assertEquals(1L,result);
        Assert.assertEquals("01",lo.getStatus());
    }


    @Test
    @Transactional
    public void selectUserOrderByStatus() throws Exception {
        Long id=addLoanOrder();
        LoanOrder lo1=loanOrderMapper.selectUserOrderByStatus(555L , new String[]{"01"} );
        Assert.assertEquals(lo1.getId(),id);
    }

}