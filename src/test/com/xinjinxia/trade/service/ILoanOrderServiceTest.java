package com.xinjinxia.trade.service;

import com.github.pagehelper.PageInfo;
import com.xianjinxia.trade.shared.domain.LoanOrder;
import com.xianjinxia.trade.app.dto.LoanOrderDto;
import com.xianjinxia.trade.app.service.ILoanOrderService;
import com.xinjinxia.trade.BaseSpringTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author zhangjiayuan
 * @create 2017-11-01 14:12
 **/
public class ILoanOrderServiceTest extends BaseSpringTest {
    @Autowired
    ILoanOrderService loanOrderService ;

    @Test
    public void conditionalPagingTest(){
//        LoanOrder loanOrder=new LoanOrder();
//        loanOrder.setUserId(427L);
//        PageInfo<LoanOrderDto> paging = loanOrderService.conditionalPaging(loanOrder, 1, 10);
        LoanDetailReq req = new LoanDetialReq();
        req.setUserId(427L);
        req.setProductCategory(1);
        req.setPageNum(1);
        req.setPageSize(10);
        PageInfo<LoanOrderDto> paging = loanOrderService.conditionalPaging(req);
        log.info("查询到的数据:{}",paging);

    }

    @Test
    public void getByUserIdAndOrderIdTest(){
        LoanOrder loanOrder=new LoanOrder();
        loanOrder.setUserId(427L);
        loanOrder.setId(63L);
        LoanOrder byUserIdAndOrderId = loanOrderService.getByUserIdAndOrderId(loanOrder);
        log.info("查询到的数据:{}",byUserIdAndOrderId);

    }



}
