package com.xianjinxia.trade.shared.mapper;

import com.xianjinxia.trade.activity.dto.ActivitySkuOrderDto;
import com.xianjinxia.trade.activity.dto.ActivitySkuSaleCountDto;
import com.xianjinxia.trade.activity.dto.UserAddressDto;
import com.xianjinxia.trade.shared.domain.TrdActivitySkuOrder;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ganminghui
 */
public interface TrdActivitySkuOrderMapper {

    /**
     * 查询10个商品编号的销量
     *
     * @param productIds 待查询的产品编号列表
     * @return 返回商品对应的销量 K 商品编号 V 商品销量
     */
    List<ActivitySkuSaleCountDto> getSaleCount(@Param("productIds") int[] productIds);

    /**
     * 查询用户的收货地址
     *
     * @param userId 待查询的用户编号
     * @return 用户的收货地址
     */
    UserAddressDto getUserAddress(@Param("userId") Long userId);

    /**
     * 查询用户的所有商品订单
     *
     * @param userId 待查询的用户编号
     * @return 返回订单列表
     */
    List<ActivitySkuOrderDto> getAllActivitySkuOrder(@Param("userId") Long userId);

    /**
     * 根据用户id，订单状态查询订单
     */
    List<ActivitySkuOrderDto> getOrderByUserIdAndStatus(@Param("userId") Long userId,@Param("productId") Integer productId, @Param("statusArray") String[] statusArray);

    int insert(TrdActivitySkuOrder record);

    TrdActivitySkuOrder selectByPrimaryKey(Long id);

    int updateOrderStatus(@Param("id") Long id,  @Param("newStatus") String newStatus, @Param("preStatus") String preStatus);
}