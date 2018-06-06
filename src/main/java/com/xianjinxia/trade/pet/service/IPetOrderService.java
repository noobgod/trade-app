package com.xianjinxia.trade.pet.service;

import com.github.pagehelper.PageInfo;
import com.xianjinxia.trade.pet.dto.BaseAssetRespDto;
import com.xianjinxia.trade.pet.request.BuyPetReq;
import com.xianjinxia.trade.pet.request.PetOrderInfoReq;
import com.xianjinxia.trade.pet.request.QuerySaleOrdersReq;
import com.xianjinxia.trade.pet.request.SalePetReq;
import com.xianjinxia.trade.pet.request.UnSalePetReq;
import com.xianjinxia.trade.pet.request.UserSaleOrdersReq;
import com.xianjinxia.trade.pet.response.AssetBaseResponse;
import com.xianjinxia.trade.pet.response.PetBaseResponse.ResponseCode;
import com.xianjinxia.trade.shared.domain.TrdSaleOrderRequest;
import com.xianjinxia.trade.shared.domain.TrdSettleRecord;

/**
 * 
 * @author liuzhifang
 *
 *         2017年9月6日
 */
public interface IPetOrderService {

    /**
     * 分页获取出售单
     * @param querySaleOrdersReq
     * @return
     */
    PageInfo<TrdSaleOrderRequest> selectPageOrders(QuerySaleOrdersReq querySaleOrdersReq);

    /**
     * 分页获取当前用户的交割单
     * @param userSaleOrdersReq
     * @return
     */
    PageInfo<TrdSettleRecord> selectOrdersByUserId(UserSaleOrdersReq userSaleOrdersReq);

    /**
     * 出售宠物
     * @param salePetReq
     * @return
     */
    ResponseCode salePet(SalePetReq salePetReq);

    /**
     * 取消出售宠物
     * @param unSalePetReq
     * @return
     */
    ResponseCode unSalePet(UnSalePetReq unSalePetReq);

    /**
     * 购买宠物
     * @param buyPetReq
     * @return
     */
    ResponseCode createSettle(BuyPetReq buyPetReq);

    /**
     * 宠物订单详情
     * @param petOrderInfoReq
     * @return
     */
    TrdSaleOrderRequest getPetOrderInfo(PetOrderInfoReq petOrderInfoReq);

    /**
     * 定时任务宠物入链
     * @return
     */
    void handleSettle();

    /**
     * 定时任务宠物入链-回调
     * @return
     */
    ResponseCode settleCallback(AssetBaseResponse<BaseAssetRespDto> assetBaseResponse);
}
