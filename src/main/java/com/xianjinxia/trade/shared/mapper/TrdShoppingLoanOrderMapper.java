package com.xianjinxia.trade.shared.mapper;

import com.xianjinxia.trade.app.dto.LoanOrderDto;
import com.xianjinxia.trade.shared.domain.TrdShoppingLoanOrder;
import com.xianjinxia.trade.shopping.response.bgd.ShoppingLoanOrderDto;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface TrdShoppingLoanOrderMapper {
	
    int deleteByPrimaryKey(Long id);

    int insert(TrdShoppingLoanOrder record);

    TrdShoppingLoanOrder selectByPrimaryKey(Long id);

    List<TrdShoppingLoanOrder> selectPage(ShoppingLoanOrderDto params);

    int updateLoanOrderStatusToPending(@Param("id") Long id,  @Param("newStatus") String newStatus, @Param("preStatus") String preStatus);

    int updateByUserIdAndStatus(@Param("userId") Long userId, @Param("newStatus") String newStatus, @Param("preStatus") String preStatus);

    Integer countByUserIdAndStatus(@Param("userId") Long userId, @Param("statusArray") String[] statusArray);

    List<TrdShoppingLoanOrder> selectByUserIdAndStatus(@Param("userId") Long userId, @Param("statusArray") String[] statusArray);

    TrdShoppingLoanOrder selectByUserIdAndStatusLimit(@Param("userId") Long userId, @Param("status") String[] status);

    Integer countByTraceNo(@Param("traceNo") String traceNo);

    int updateLoanOrderStatus(@Param("id") Long id,  @Param("newStatus") String newStatus, @Param("preStatus") String preStatus);

    List<LoanOrderDto> selectSelective(TrdShoppingLoanOrder trdShoppingLoanOrder);

    TrdShoppingLoanOrder getLastLoanOrderByUserIdAndProductCategory(@Param("userId") Long userId,
                                                         @Param("productCategory") Integer productCategory);

    List<TrdShoppingLoanOrder> selectListByStatusAndKind(@Param("status") String status, @Param("productKind") Integer productKind);

    int updateVirtualLoanOrderStatus(@Param("id") Long id, @Param("newStatus") String newStatus, @Param("preStatus") String preStatus, @Param("thirdOrderTime")Date thirdOrderTime);

    TrdShoppingLoanOrder selectByIdOrUserNamePhone(@Param("id") Long id, @Param("userName") String userName, @Param("userPhone") String userPhone);
}