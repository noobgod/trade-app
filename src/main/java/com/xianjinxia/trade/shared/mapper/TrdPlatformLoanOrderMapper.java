package com.xianjinxia.trade.shared.mapper;

import com.xianjinxia.trade.platform.dto.CheckLoanDto;
import com.xianjinxia.trade.platform.dto.TrdPlatformOrderAuditDto;
import com.xianjinxia.trade.platform.request.BackOrderRequest;
import com.xianjinxia.trade.platform.request.UserOrderRequest;
import com.xianjinxia.trade.shared.domain.TrdPlatformLoanOrder;

import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface TrdPlatformLoanOrderMapper {

    int insert(TrdPlatformLoanOrder record);

    TrdPlatformLoanOrder selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TrdPlatformLoanOrder record);


    List<TrdPlatformLoanOrder> getLoanOrderByCondition(BackOrderRequest orderRequest);

    List<TrdPlatformLoanOrder> getLoanOrderByUserId(UserOrderRequest userOrderRequest);

    List<TrdPlatformLoanOrder> getByStatusAndNextRetryTime(@Param("status")String status,@Param("nextRetryTime")Date nextRetryTime);

    int updateNextRetryTimeAndRetryTimesById(@Param("id") Long id,@Param("nextRetryTime") Date nextRetryTime,
                                             @Param("retryTimes") Integer retryTimes);

    /**
     * 更新trd_platform_loan_order表 状态
     * @param orderNo
     * @param targetStatus
     * @param originStatus
     * @return
     */
    int updateLoanOrderStatus(@Param("orderNo") String orderNo, @Param("targetStatus") String targetStatus, @Param("originStatus") String originStatus ,@Param("partnerOperateTime") Date partnerOperateTime,@Param("remark") String remark);

    int updateLoanOrderRetryTimes(@Param("orderNo") String orderNo, @Param("targetRetryTimes") Integer targetRetryTimes, @Param("originRetryTimes") Integer originRetryTimes);


    int updateStatusAndNextRetryTimeAndRetryTimes(@Param("orderNo") String orderNo, @Param("targetStatus") String targetStatus,
                                                  @Param("originStatus") String originStatus , @Param("nextRetryTime")Date nextRetryTime,
                                                  @Param("retryTimes") Integer retryTimes);
    /**
     * 在trd_platform_loan_order 表中通过订单号来查找 订单
     * @param orderNo
     * @return
     */
    TrdPlatformLoanOrder selectByOrderNo(@Param("orderNo") String orderNo);


    List<TrdPlatformLoanOrder> selectByStatusAndRetryTimes(@Param("status") String status, @Param("maxRetryTimes") Integer maxRetryTimes);


    /**
     * 根据订单号查询订单状态等数据
     * @param orderNo
     * @return
     */
    TrdPlatformOrderAuditDto selectStatusByOrderNo(@Param("orderNo") String orderNo);

    /**
     * 根据userId和companyService获取用户最后一笔订单
     * @param userId
     * @param serviceCompany
     * @return
     */
    CheckLoanDto getOrderNoByUserIdAndServiceCompanyLast(@Param("userId") Long userId, @Param("serviceCompany")String serviceCompany);

    /**
     * 根据userId ,orderNo 来获取 该笔订单
     * @param userId
     * @param orderNo 订单号
     * @return
     */
    TrdPlatformLoanOrder selectOrderByUserIdOrderNo(@Param("orderNo") String orderNo , @Param("userId") Long userId  );


    /**
     * 根据userId来查询订单
     * @param userId
     * @return
     */
    List<TrdPlatformLoanOrder> selectByUserId(@Param("userId") Long userId, @Param("productCode") String productCode);
}