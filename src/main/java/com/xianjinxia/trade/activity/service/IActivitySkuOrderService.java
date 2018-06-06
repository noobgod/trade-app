package com.xianjinxia.trade.activity.service;

import com.xianjinxia.trade.activity.dto.ActivitySkuOrderDto;
import com.xianjinxia.trade.activity.dto.UserAddressDto;
import com.xianjinxia.trade.activity.request.BuyApplyRequest;
import com.xianjinxia.trade.activity.request.PayApplyRequest;
import com.xianjinxia.trade.activity.request.SeckillOrderRepayCallbackRequest;

import java.util.List;
import java.util.Map;

/**
 * 活动商品服务类
 * @author ganminghui
 */
public interface IActivitySkuOrderService {

    /**
     *  查询10个商品编号的销量
     *  @param productIds 待查询的产品编号列表
     *  @return 返回商品对应的销量 K 商品编号 V 商品销量
     */
    Map<Integer,Integer> getSaleCount(String productIds);

    /**
     * 查询用户的收货地址
     * @param userId 待查询的用户编号
     * @return 用户的收货地址
     */
    UserAddressDto getUserAddress(Long userId);


    /** 查询用户的所有商品订单
     *  @param userId 待查询的用户编号
     *  @return 返回订单列表
     * */
    List<ActivitySkuOrderDto> getAllActivitySkuOrder(Long userId);


    /**
     * 下单申请
     * @param request
     * @return
     */
    Long orderApply(BuyApplyRequest request);

    /**
     * 支付申请
     * @param request
     */
    void orderPayApply(PayApplyRequest request);

    /**
     * 修改订单信息
     * @param request
     */
    int updateActivityOrder(SeckillOrderRepayCallbackRequest request);
}