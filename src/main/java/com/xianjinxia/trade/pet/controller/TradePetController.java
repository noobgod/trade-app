package com.xianjinxia.trade.pet.controller;

import com.github.pagehelper.PageInfo;
import com.xianjinxia.trade.pet.request.BuyPetReq;
import com.xianjinxia.trade.pet.request.PetOrderInfoReq;
import com.xianjinxia.trade.pet.request.QuerySaleOrdersReq;
import com.xianjinxia.trade.pet.request.SalePetReq;
import com.xianjinxia.trade.pet.request.UnSalePetReq;
import com.xianjinxia.trade.pet.request.UserSaleOrdersReq;
import com.xianjinxia.trade.pet.response.PetBaseResponse;
import com.xianjinxia.trade.pet.response.PetBaseResponse.ResponseCode;
import com.xianjinxia.trade.pet.service.IPetOrderService;
import com.xianjinxia.trade.shared.domain.TrdSaleOrderRequest;
import com.xianjinxia.trade.shared.domain.TrdSettleRecord;
import com.xianjinxia.trade.shared.exception.ServiceException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Myron
 * <p>
 * 2017年9月8日
 */
@Api(tags = "trade_app pet controller")
@RestController
@RequestMapping("/service/pet")
public class TradePetController extends PetBaseController {

    private static final Logger logger = LoggerFactory.getLogger(TradePetController.class);

    @Autowired
    private IPetOrderService petOrderService;

    @ApiOperation(value = "get_pet_sell_list", httpMethod = "POST",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE, notes = "获取宠物出售列表")
    @ApiImplicitParam(name = "querySaleOrdersReq", value = "querySaleOrdersReq", required = true,
            dataType = "QuerySaleOrdersReq")
    @ApiResponse(response = PetBaseResponse.class, message = "baseResponse", code = 200)
    @PostMapping(value = "/sales")
    public PetBaseResponse<PageInfo<TrdSaleOrderRequest>> getSales(@RequestBody QuerySaleOrdersReq querySaleOrdersReq) {
        logger.info("获取出售列表参数: {}", querySaleOrdersReq);
        PetBaseResponse<PageInfo<TrdSaleOrderRequest>> baseResponse = new PetBaseResponse<>();
        PageInfo<TrdSaleOrderRequest> queryResult = null;

        try {
            queryResult = petOrderService.selectPageOrders(querySaleOrdersReq);
        } catch (ServiceException se) {
            logger.error("获取宠物出售列表出错", se);
            baseResponse.setCode(se.getCode());
            baseResponse.setMsg(se.getMsg());
        }

        baseResponse.setData(queryResult);
        return baseResponse;
    }

    @ApiOperation(value = "get_user_pet_order_list", httpMethod = "POST",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE, notes = "获取我的订单列表")
    @ApiImplicitParam(name = "userSaleOrdersReq", value = "userSaleOrdersReq", required = true,
            dataType = "UserSaleOrdersReq")
    @ApiResponse(response = PetBaseResponse.class, message = "baseResponse", code = 200)
    @PostMapping(value = "/user/orders")
    public PetBaseResponse<PageInfo<TrdSettleRecord>> userOrders(@RequestBody UserSaleOrdersReq userSaleOrdersReq) {
        logger.info("我的订单参数: {}", userSaleOrdersReq);
        PetBaseResponse<PageInfo<TrdSettleRecord>> baseResponse = new PetBaseResponse<>();
        PageInfo<TrdSettleRecord> queryResult = null;

        try {
            queryResult = petOrderService.selectOrdersByUserId(userSaleOrdersReq);
        } catch (ServiceException se) {
            logger.error("获取我的订单列表出错", se);
            baseResponse.setCode(se.getCode());
            baseResponse.setMsg(se.getMsg());
        }
        baseResponse.setData(queryResult);
        return baseResponse;
    }

    @ApiOperation(value = "sale_user_pet", httpMethod = "POST",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE, notes = "宠物出售")
    @ApiImplicitParam(name = "salePetReq", value = "salePetReq", required = true,
            dataType = "SalePetReq")
    @ApiResponse(response = PetBaseResponse.class, message = "baseResponse", code = 200)
    @PostMapping(value = "/user/sale")
    public PetBaseResponse userSale(@RequestBody SalePetReq salePetReq) {
        logger.info("宠物出售参数: {}", salePetReq);
        PetBaseResponse baseResponse = new PetBaseResponse<>();

        try {
            ResponseCode responseCode = petOrderService.salePet(salePetReq);
            baseResponse.setCode(responseCode.getValue());
            baseResponse.setMsg(responseCode.getDescription());
        } catch (ServiceException se) {
            logger.error("宠物出售出错", se);
            baseResponse.setCode(se.getCode());
            baseResponse.setMsg(se.getMsg());
        }
        return baseResponse;
    }

    @ApiOperation(value = "un_sale_user_pet", httpMethod = "POST",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE, notes = "取消宠物出售")
    @ApiImplicitParam(name = "unSalePetReq", value = "unSalePetReq", required = true,
            dataType = "UnSalePetReq")
    @ApiResponse(response = PetBaseResponse.class, message = "baseResponse", code = 200)
    @PostMapping(value = "/user/unsale")
    public PetBaseResponse userUnSale(@RequestBody UnSalePetReq unSalePetReq) {
        logger.info("取消宠物出售参数: {}", unSalePetReq);
        PetBaseResponse baseResponse = new PetBaseResponse<>();

        try {
            ResponseCode responseCode = petOrderService.unSalePet(unSalePetReq);
            baseResponse.setCode(responseCode.getValue());
            baseResponse.setMsg(responseCode.getDescription());
        } catch (ServiceException se) {
            logger.error("取消宠物出售出错", se);
            baseResponse.setCode(se.getCode());
            baseResponse.setMsg(se.getMsg());
        }
        return baseResponse;
    }

    @ApiOperation(value = "buy_pet", httpMethod = "POST",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE, notes = "购买宠物")
    @ApiImplicitParam(name = "buyPetReq", value = "buyPetReq", required = true,
            dataType = "BuyPetReq")
    @ApiResponse(response = PetBaseResponse.class, message = "baseResponse", code = 200)
    @PostMapping(value = "/buy")
    public PetBaseResponse createSettle(@RequestBody BuyPetReq buyPetReq) {
        logger.info("宠物购买参数: {}", buyPetReq);
        PetBaseResponse baseResponse = new PetBaseResponse<>();

        try {
            ResponseCode responseCode = petOrderService.createSettle(buyPetReq);
            baseResponse.setCode(responseCode.getValue());
            baseResponse.setMsg(responseCode.getDescription());
        } catch (ServiceException se) {
            logger.error("购买宠物出错", se);
            baseResponse.setCode(se.getCode());
            baseResponse.setMsg(se.getMsg());
        }
        return baseResponse;
    }

    @ApiOperation(value = "order_pet_detail", httpMethod = "POST",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE, notes = "宠物订单详情")
    @ApiImplicitParam(name = "orderInfoReq", value = "orderInfoReq", required = true,
            dataType = "PetOrderInfoReq")
    @ApiResponse(response = PetBaseResponse.class, message = "baseResponse", code = 200)
    @PostMapping(value = "/order/detail")
    public PetBaseResponse<TrdSaleOrderRequest> getPetOrderInfo(@RequestBody PetOrderInfoReq orderInfoReq) {
        logger.info("宠物订单详情参数: {}", orderInfoReq);
        PetBaseResponse<TrdSaleOrderRequest> baseResponse = new PetBaseResponse<>();
        TrdSaleOrderRequest orderRequest = null;

        try {
            orderRequest = petOrderService.getPetOrderInfo(orderInfoReq);
        } catch (ServiceException se) {
            logger.error("宠物订单详情出错", se);
            baseResponse.setCode(se.getCode());
            baseResponse.setMsg(se.getMsg());
        }
        baseResponse.setData(orderRequest);
        return baseResponse;
    }

}
