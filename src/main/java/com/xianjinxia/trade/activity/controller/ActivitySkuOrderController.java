package com.xianjinxia.trade.activity.controller;

import com.xianjinxia.trade.activity.dto.ActivitySkuOrderDto;
import com.xianjinxia.trade.activity.dto.UserAddressDto;
import com.xianjinxia.trade.activity.request.BuyApplyRequest;
import com.xianjinxia.trade.activity.request.PayApplyRequest;
import com.xianjinxia.trade.activity.request.SeckillOrderRepayCallbackRequest;
import com.xianjinxia.trade.activity.service.IActivitySkuOrderService;
import com.xianjinxia.trade.app.controller.BaseController;
import com.xianjinxia.trade.app.response.BaseResponse;
import com.xianjinxia.trade.app.response.mqapp.ResultMsg;
import com.xianjinxia.trade.shared.exception.LimitException;
import com.xianjinxia.trade.shared.exception.ServiceException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 活动订单请求处理器
 * @author ganminghui
 */
@RequestMapping("/service/activity/sku")
@RestController
public class ActivitySkuOrderController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ActivitySkuOrderController.class);
    @Autowired private IActivitySkuOrderService activitySkuOrderService;

    /** 测试限流的接口(可删除) */
    @PostMapping("/order/apply")
    public ResultMsg batchUpdateShoppingVirtualLoanStatus() {
        LOGGER.info("********{}*********","/apply");
        return successResp();
    }

    /**
     * 活动商品列表页查询商品销量
     * <pre>
     *     商品的销量由前端传递的商品ID去订单表查询支付成功的订单数据(status in 3,5)
     * </pre>
     */
    @GetMapping("/get/salecount")
    public BaseResponse getSaleCount(@RequestParam("productIds") String productIds){
        LOGGER.info("查询商品编号:{}的销量开始!!!", productIds);
        BaseResponse<Map<Integer,Integer>> baseResponse = new BaseResponse<>();
        try {
            baseResponse.setData(activitySkuOrderService.getSaleCount(productIds));
        } catch (ServiceException se) {
            LOGGER.error("biz check error {}", se);
            baseResponse.setCode(BaseResponse.ResponseCode.BIZ_CHECK_FAIL.getValue());
            baseResponse.setMsg(se.getMsg());
        } catch (Exception e) {
            LOGGER.error("/service/activity/sku/get/salecount has exception :{}", e);
            baseResponse.systemError();
        }
        return baseResponse;
    }

    /**
     *  活动商品详情页查询用户的地址、生成traceNo
     *  <pre>
     *      由于地址信息由城市+详细地址组成,不兼容分期商城的地址管理, 因此地址在该用户最后一笔订单中查询
     *  </pre>
     */
    @GetMapping("/get/address")
    public BaseResponse<UserAddressDto> getUserAddress(@RequestParam("userId") Long userId) {
        LOGGER.info("查询用户编号:{}的收货地址开始!!!", userId);
        BaseResponse<UserAddressDto> baseResponse = new BaseResponse<>();
        try {
            baseResponse.setData(activitySkuOrderService.getUserAddress(userId));
        } catch (ServiceException se) {
            LOGGER.error("biz check error {}", se);
            baseResponse.setCode(BaseResponse.ResponseCode.BIZ_CHECK_FAIL.getValue());
            baseResponse.setMsg(se.getMsg());
        } catch (Exception e) {
            LOGGER.error("/service/activity/sku/get/address has exception :{}", e);
            baseResponse.systemError();
        }
        return baseResponse;
    }

    /**
     *  查询我的商品订单
     */
    @GetMapping("/get/orders")
    public BaseResponse getAllActivitySkuOrder(@RequestParam("userId") Long userId){
        LOGGER.info("查询用户编号:{}的活动商品列表开始!!!", userId);
        BaseResponse<List<ActivitySkuOrderDto>> baseResponse = new BaseResponse<>();
        try {
            baseResponse.setData(activitySkuOrderService.getAllActivitySkuOrder(userId));
        } catch (ServiceException se) {
            LOGGER.error("biz check error {}", se);
            baseResponse.setCode(BaseResponse.ResponseCode.BIZ_CHECK_FAIL.getValue());
            baseResponse.setMsg(se.getMsg());
        } catch (Exception e) {
            LOGGER.error("/service/activity/sku/get/orders has exception :{}", e);
            baseResponse.systemError();
        }
        return baseResponse;
    }

    /**
     * 下单申请
     *
     * @param request
     * @return
     */
    @PostMapping("/order-buy-apply")
    public BaseResponse<Long> orderApply(@RequestBody BuyApplyRequest request) {
        LOGGER.info("立即抢购活动，下单申请，入参：" + request);
        BaseResponse<Long> baseResponse = new BaseResponse<>();
        try {
            Long orderId = activitySkuOrderService.orderApply(request);
            baseResponse.setData(orderId);
        } catch (ServiceException se) {
            LOGGER.error("biz check error {}", se);
            baseResponse.setCode(BaseResponse.ResponseCode.BIZ_CHECK_FAIL.getValue());
            baseResponse.setMsg(se.getMsg());
        } catch (Exception e) {
            LOGGER.error("/service/activity/sku/order-buy-apply has exception :{}", e);
            baseResponse.systemError();
        }

        return baseResponse;
    }

    @PostMapping("/order-pay-apply")
    public BaseResponse orderPayApply(@RequestBody PayApplyRequest request) {
        LOGGER.info("立即抢购活动，支付申请，入参：" + request);
        BaseResponse baseResponse = new BaseResponse<>();
        try {
            activitySkuOrderService.orderPayApply(request);
        } catch (ServiceException se) {
            LOGGER.error("biz check error {}", se);
            baseResponse.setCode(BaseResponse.ResponseCode.BIZ_CHECK_FAIL.getValue());
            baseResponse.setMsg(se.getMsg());
        } catch (Exception e) {
            LOGGER.error("/service/activity/sku/order-pay-apply has exception :{}", e);
            baseResponse.systemError();
        }

        return baseResponse;
    }

    /**
     * 同步订单状态
     * @param req
     * @return
     */
    @PostMapping("/update-order")
    public BaseResponse synchronizeActivityOrder(@RequestBody SeckillOrderRepayCallbackRequest req) {
        LOGGER.info("立即抢购活动，支付回调修改订单状态，入参：" + req);
        BaseResponse baseResponse = new BaseResponse<>();
        try {
            activitySkuOrderService.updateActivityOrder(req);
        } catch (ServiceException se) {
            LOGGER.error("biz check error {}", se);
            baseResponse.setCode(BaseResponse.ResponseCode.BIZ_CHECK_FAIL.getValue());
            baseResponse.setMsg(se.getMsg());
        } catch (Exception e) {
            LOGGER.error("/service/activity/sku/update-order has exception :{}", e);
            baseResponse.systemError();
        }
        return baseResponse;
    }

    @ExceptionHandler({LimitException.class})
    public String exception(LimitException e) { return StringUtils.isBlank(e.getMessage())?LimitException.DEFAULT_LIMITER_MSG : e.getMessage(); }
}