package com.xianjinxia.trade.shopping.service;

import com.xianjinxia.trade.shared.TradeApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = TradeApplication.class)
@Transactional
//@Rollback
public class ITrdShoppingLoanCardInfoServiceTest {

    @Autowired
    private ITrdShoppingLoanCardInfoService trdShoppingLoanCardInfoService;


    @Test
    public void getByOrderId() {
        List result = trdShoppingLoanCardInfoService.getByOrderId(new Long(111));
        System.out.println(result.toArray().toString());

    }


}