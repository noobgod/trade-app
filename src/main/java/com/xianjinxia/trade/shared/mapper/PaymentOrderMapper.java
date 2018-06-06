package com.xianjinxia.trade.shared.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.xianjinxia.trade.shared.domain.PaymentOrder;

@Mapper
public interface PaymentOrderMapper {

    int insert(PaymentOrder record);

    PaymentOrder selectByPrimaryKey(Long id);

    PaymentOrder selectByTradeOrderId(@Param("trdOrderId") Long trdOrderId,
            @Param("paymentOrderType") Integer paymentOrderType);

    int updateByPaymentSeqNoAndStat(@Param("newStatus") String newStatus,
            @Param("record") PaymentOrder record);



    /**
     * 根据paymentSeqNo查询loanOrder表的ID和状态
     * 
     * @param paymentSeqNo
     * @return
     */
    PaymentOrder getLoanOrderIdAndStatusByPaymentSeqNo(String paymentSeqNo);


}
