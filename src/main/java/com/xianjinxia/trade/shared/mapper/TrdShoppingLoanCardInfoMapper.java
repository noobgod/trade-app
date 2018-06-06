package com.xianjinxia.trade.shared.mapper;

import com.xianjinxia.trade.shared.domain.TrdShoppingLoanCardInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TrdShoppingLoanCardInfoMapper {


    int insert(TrdShoppingLoanCardInfo record);

    List<TrdShoppingLoanCardInfo> selectByOrderId(@Param("shoppingProductOrderId") Long shoppingProductOrderId);

    TrdShoppingLoanCardInfo selectByNoAndPassword(@Param("cardNo") String cardNo,@Param("cardPassword") String cardPassword);
}