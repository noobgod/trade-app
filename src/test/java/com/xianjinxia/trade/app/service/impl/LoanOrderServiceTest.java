package com.xianjinxia.trade.app.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xianjinxia.trade.app.dto.LoanOrderDto;
import com.xianjinxia.trade.app.dto.QuotaGiveBackDto;
import com.xianjinxia.trade.app.dto.TraceDto;
import com.xianjinxia.trade.app.service.ILoanOrderService;
import com.xianjinxia.trade.app.service.IMqMessageService;
import com.xianjinxia.trade.shared.TradeApplication;
import com.xianjinxia.trade.shared.domain.JobLock;
import com.xianjinxia.trade.shared.domain.LoanOrder;
import com.xianjinxia.trade.shared.domain.Trace;
import com.xianjinxia.trade.shared.mapper.JobLockMapper;
import com.xianjinxia.trade.shared.mapper.LoanOrderMapper;
import com.xianjinxia.trade.shared.mapper.TraceMapper;
import com.xianjinxia.trade.shared.utils.GsonUtil;
import com.xjx.mqclient.pojo.MqMessage;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = TradeApplication.class)
public class LoanOrderServiceTest {

    @Autowired
    private ILoanOrderService loanOrderService;


    @Autowired
    private LoanOrderMapper loanOrderMapper;

    @Autowired
    private IMqMessageService mqMessageService;

    @Autowired
    private TraceMapper traceMapper;
    @Test
    public void conditionalPagingTest(){
        LoanOrder loanOrder = new LoanOrder();
        loanOrder.setUserId(8774683l);
        loanOrder.setProductCategory(2);
        PageHelper.startPage(1, 5);
        List<LoanOrderDto> list = loanOrderMapper.selectSelective(loanOrder);
        Page<LoanOrderDto> page = (Page<LoanOrderDto>) list;
        System.out.println(JSON.toJSONString(page));
    }


    @Test
    public void orderSendMqTest(){
        QuotaGiveBackDto dto = new QuotaGiveBackDto();
        dto.setUserId(416L);
        dto.setProductCategory(2);
        dto.setMoneyAmount(200);
        dto.setSeqNo("4521");
        mqMessageService.sendMessage(new MqMessage(GsonUtil.toGson(dto),"trd_return_quota"));
    }
}