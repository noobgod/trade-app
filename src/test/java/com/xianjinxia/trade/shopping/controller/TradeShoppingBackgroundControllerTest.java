package com.xianjinxia.trade.shopping.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.xianjinxia.trade.app.response.BaseResponse;
import com.xianjinxia.trade.shared.TradeApplication;
import com.xianjinxia.trade.shared.domain.TrdShoppingLoanOrder;
import com.xianjinxia.trade.shared.domain.TrdShoppingLogisticsOrder;
import com.xianjinxia.trade.shopping.request.bgd.ShoppingLogisticsOrderEditRequest;
import com.xianjinxia.trade.shopping.response.bgd.RepaymentPlanDto;
import com.xianjinxia.trade.shopping.response.bgd.ShoppingLoanOrderDto;
import com.xianjinxia.trade.shopping.response.bgd.ShoppingLogisticsOrderDto;
import com.xianjinxia.trade.shopping.response.bgd.ShoppingProductOrderDto;
import com.xianjinxia.trade.shopping.service.IShoppingLoanService;
import com.xianjinxia.trade.shopping.service.ITrdShoppingLoanOrderService;
import com.xianjinxia.trade.shopping.service.ITrdShoppingLogisticsOrderService;
import com.xianjinxia.trade.shopping.service.ITrdShoppingProductOrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = TradeApplication.class)
@Transactional
//@Rollback
public class TradeShoppingBackgroundControllerTest {

    @Autowired
    private IShoppingLoanService shoppingLoanService;

    @Autowired
    private ITrdShoppingLoanOrderService trdShoppingLoanOrderService;

    @Autowired
    private ITrdShoppingProductOrderService trdShoppingProductOrderService;

    @Autowired
    private ITrdShoppingLogisticsOrderService trdShoppingLogisticsOrderService;


    @Test
    public void getLoanOrderList(){
        ShoppingLoanOrderDto params = new ShoppingLoanOrderDto();
        params.setPageNum(1);
        params.setPageSize(10);
        PageInfo<TrdShoppingLoanOrder> loanOrderPageList = trdShoppingLoanOrderService.getLoanOrderPageList(params);
        List<TrdShoppingLoanOrder> trdShoppingLoanOrderList = loanOrderPageList.getList();
        List<ShoppingLoanOrderDto> shoppingLoanOrderDtos = new ArrayList<>(trdShoppingLoanOrderList.size());
        for (Iterator<TrdShoppingLoanOrder> iterator = trdShoppingLoanOrderList.iterator(); iterator.hasNext(); ) {
            TrdShoppingLoanOrder trdShoppingLoanOrder = iterator.next();
            ShoppingLoanOrderDto shoppingLoanOrderDto = new ShoppingLoanOrderDto();
            BeanUtils.copyProperties(trdShoppingLoanOrder, shoppingLoanOrderDto);
            shoppingLoanOrderDto.setCreatedTime(trdShoppingLoanOrder.getCreatedAt().getTime());
            shoppingLoanOrderDto.setUpdatedTime(trdShoppingLoanOrder.getUpdatedAt().getTime());
            shoppingLoanOrderDtos.add(shoppingLoanOrderDto);
        }

        PageInfo<ShoppingLoanOrderDto> pageInfo = new PageInfo<>();
        BeanUtils.copyProperties(loanOrderPageList, pageInfo);
        pageInfo.setList(shoppingLoanOrderDtos);

        System.out.println(JSON.toJSONString(pageInfo));
    }

    @Test
    public void getRepaymentPlanByLoanOrderId(){
        String loanOrderId = "1";
        List<RepaymentPlanDto> repaymentPlanList = trdShoppingLoanOrderService.getRepaymentPlanList(loanOrderId);
        System.out.println(JSON.toJSONString(repaymentPlanList));
    }

    @Test
    public void getProductOrderList(){
        ShoppingProductOrderDto params = new ShoppingProductOrderDto();
        params.setPageNum(1);
        params.setPageSize(10);
        PageInfo<ShoppingProductOrderDto> productOrderPageList = trdShoppingProductOrderService.getProductOrderPageList(params);
        System.out.println(JSON.toJSONString(productOrderPageList));
    }

    @Test
    public void getProductOrder(){
        String productOrderId = "60";
        ShoppingProductOrderDto shoppingProductOrderDto = trdShoppingProductOrderService.getById(Long.parseLong(productOrderId));
        System.out.println(JSON.toJSONString(shoppingProductOrderDto));
    }

    @Test
    public void getLogisticsOrderList(){
        ShoppingLogisticsOrderDto params = new ShoppingLogisticsOrderDto();
        params.setPageNum(1);
        params.setPageSize(10);
        PageInfo<ShoppingLogisticsOrderDto> trdShoppingLogisticsOrderPageInfo = trdShoppingLogisticsOrderService.getLogisticsOrderPageList(params);
        System.out.println(JSON.toJSONString(trdShoppingLogisticsOrderPageInfo));
    }

    @Test
    public void getLogisticsOrder(){
        String logisticsOrderId = "64";
        ShoppingLogisticsOrderDto shoppingLogisticsOrderDto = trdShoppingLogisticsOrderService.getShoppingLogisticsOrderDtoById(Long.parseLong(logisticsOrderId));
        System.out.println(JSON.toJSONString(shoppingLogisticsOrderDto));
    }

    @Test
    public void editLogisticsOrder(){
        ShoppingLogisticsOrderEditRequest req = new ShoppingLogisticsOrderEditRequest();
        req.setId(64l);
        req.setVenderId("1");
        req.setVenderName("Mi");
        req.setLogisticsCompany("顺丰");
        req.setSendPostNo("200200");
        req.setLogisticsPrice(12);
        req.setLogisticsNo("123123123KXA1");
        req.setSendTime(new Date().getTime());
//        private Long id;
//        private Long shoppingLoanOrderId;
//        private String venderId;//供应商ID
//        private String venderName;//供应商名称
//        private String logisticsNo;//物流编号
//        private String sendPostNo;//物流邮编
//        private String logisticsCompany;//物流公司
//        private Integer logisticsPrice;//物流费用
//        private Long sendTime;//发货时间

        TrdShoppingLogisticsOrder trdShoppingLogisticsOrder = new TrdShoppingLogisticsOrder();
        BeanUtils.copyProperties(req, trdShoppingLogisticsOrder);
        trdShoppingLogisticsOrderService.updateLogisticsOrder(trdShoppingLogisticsOrder, false);


        String logisticsOrderId = "64";
        ShoppingLogisticsOrderDto shoppingLogisticsOrderDto = trdShoppingLogisticsOrderService.getShoppingLogisticsOrderDtoById(Long.parseLong(logisticsOrderId));
        System.out.println(JSON.toJSONString(shoppingLogisticsOrderDto));

    }

    @Test
    public void deliver(){
        ShoppingLogisticsOrderEditRequest req = new ShoppingLogisticsOrderEditRequest();
        req.setId(64l);
        req.setVenderId("1");
        req.setVenderName("Mi");
        req.setLogisticsCompany("顺丰");
        req.setSendPostNo("200200");
        req.setLogisticsPrice(12);
        req.setLogisticsNo("123123123KXA1");
        req.setSendTime(new Date().getTime());
        TrdShoppingLogisticsOrder trdShoppingLogisticsOrder = new TrdShoppingLogisticsOrder();
        BeanUtils.copyProperties(req, trdShoppingLogisticsOrder);
        trdShoppingLogisticsOrder.setSendTime(new Date(req.getSendTime()));
        trdShoppingLogisticsOrderService.updateLogisticsOrder(trdShoppingLogisticsOrder, true);

        String logisticsOrderId = "64";
        ShoppingLogisticsOrderDto shoppingLogisticsOrderDto = trdShoppingLogisticsOrderService.getShoppingLogisticsOrderDtoById(Long.parseLong(logisticsOrderId));
        System.out.println(JSON.toJSONString(shoppingLogisticsOrderDto));

    }


}