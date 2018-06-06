package com.xianjinxia.trade.shared.remote;

import com.alibaba.fastjson.JSON;
import com.xianjinxia.trade.shared.TradeApplication;
import com.xianjinxia.trade.shared.remote.dto.CashmanUserInfoDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = TradeApplication.class)
@Transactional
@Rollback
public class OldCashmanRemoteServiceTest {

    @Autowired
    private OldCashmanRemoteService oldCashmanRemoteService;

    @Test
    public void getUserInfoByUserId() throws Exception {
        CashmanUserInfoDto userInfoByUserId = oldCashmanRemoteService.getUserInfoByUserId(4010000L);
        System.out.println(JSON.toJSONString(userInfoByUserId));
    }

}