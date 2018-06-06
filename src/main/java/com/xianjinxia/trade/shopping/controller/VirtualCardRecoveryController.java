package com.xianjinxia.trade.shopping.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.xianjinxia.trade.shopping.service.ITrdShoppingLoanCardInfoService;

/**
 * @service 搜卡接口提供
 * @author wenkk
 * @date 2018/04/17
 */
@RequestMapping("/service/virtualCardRecovery")
@RestController
public class VirtualCardRecoveryController{
    private static Logger logger= LoggerFactory.getLogger(VirtualCardRecoveryController.class);
    @Autowired
    private ITrdShoppingLoanCardInfoService shoppingTradeRemoteService;

    @PostMapping("/getCardList")
    public String getCardList(@RequestBody String data) {
        String resultData = null;
        try {
            logger.info("getCardList, request parameter:{}", data);
            resultData = shoppingTradeRemoteService.selectByNoAndPassword(data);
        } catch (Exception e) {
            logger.error("/service/virtualCardRecovery/getCardList error", e);
        }
        return resultData;
    }
}
