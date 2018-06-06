package com.xianjinxia.trade.shared.mapper;

import com.xianjinxia.trade.shared.domain.TrdShoppingLogisticsOrder;
import com.xianjinxia.trade.shopping.response.bgd.ShoppingLogisticsOrderDto;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface TrdShoppingLogisticsOrderMapper {

    int insert(TrdShoppingLogisticsOrder record);

    TrdShoppingLogisticsOrder selectByPrimaryKey(Long id);

    int updateLogisticsOrderStatus(@Param("shoppingLoanOrderId") Long shoppingLoanOrderId,  @Param("newStatus") String newStatus, @Param("preStatus") String preStatus);

    int updateLogisticsOrderToDeliver(@Param("id") Long id, @Param("logisticsCompany") String logisticsCompany, @Param("logisticsNo") String logisticsNo, @Param("sendPostNo") String sendPostNo, @Param("logisticsPrice") Integer logisticsPrice, @Param("sendTime") Date sendTime, @Param("venderId") String venderId, @Param("venderName") String venderName, @Param("status") String status);

    TrdShoppingLogisticsOrder getByShoppingLoanOrderId(@Param("shoppingLoanOrderId") Long shoppingLoanOrderId);
    
    List<TrdShoppingLogisticsOrder> selectByEndTimeAndStatus(@Param("endTime") String endTime, @Param("status") String status);

    List<TrdShoppingLogisticsOrder> selectPage(ShoppingLogisticsOrderDto params);

    TrdShoppingLogisticsOrder getByShoppingLoanOrderIdAndNameMobile(@Param("shoppingLoanOrderId") Long shoppingLoanOrderId, @Param("receiveUsername") String receiveUsername, @Param("receiveMobile") String receiveMobile);
}