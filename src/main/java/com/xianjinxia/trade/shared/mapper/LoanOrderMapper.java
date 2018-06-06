package com.xianjinxia.trade.shared.mapper;

import java.util.Date;
import java.util.List;

import com.xianjinxia.trade.app.dto.OrderDetailDto;
import com.xianjinxia.trade.app.request.OrderApplyReq;
import com.xianjinxia.trade.app.response.UnfreezeOrdersResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.xianjinxia.trade.app.dto.ManualReviewLoanOrderDto;
import com.xianjinxia.trade.shared.domain.LoanOrder;
import com.xianjinxia.trade.app.dto.LoanOrderDto;


@Mapper
public interface LoanOrderMapper {


    int insert(LoanOrder record);


    LoanOrder selectByPrimaryKey(Long id);



    /**
     * 修改订单状态
     * 
     * @param bizSeqNo 业务流水号
     * @param newStatus
     * @param oldStatus
     * @return
     */
    int updateOrderStatus(@Param("bizSeqNo") String bizSeqNo, @Param("newStatus") String newStatus,
            @Param("oldStatus") String oldStatus);

    LoanOrder selectByBizNo(@Param("bizSeqNo") String bizSeqNo);

    List<LoanOrderDto> selectSelective(LoanOrder record);

    LoanOrder getByUserIdAndOrderId(LoanOrder record);

    int updateStatByIdAndStat(@Param("newStatus") String newStatus, @Param("id") Long id,
            @Param("oldStatus") String oldStatus);

    /**
     * 根据ID查询 userId,orderAmount,bizSeqNo,traceNo
     * 
     * @param id 主键ID
     * @return
     */
    LoanOrder getUserMoneyById(Long id);

    LoanOrder getById(Long id);

    LoanOrder getLastLoanOrderByUserIdAndProductCategory(@Param("userId") Long userId,
            @Param("productCategory") Integer productCategory);

    LoanOrder getLastLoanOrderByUserIdAndMerchantNo(@Param("userId") Long userId, @Param("merchantNo") String merchantNo);

    /**
     * 根据ID 获取 tranceNo
     * 
     * @param id 主键ID
     * @return
     */
    String getTraceNoById(Long id);

    /**
     * 获取这个用户非终态的订单
     * 
     * @param userId 用户ID
     * @return
     */
    Integer selectNonUltimateOrder(@Param("userId") Long userId, @Param("status") String[] status);

    /**
     * 获取这个用户某些状态的订单
     *
     * @param userId 用户ID
     * @return
     */
    LoanOrder selectUserOrderByStatus(@Param("userId") Long userId, @Param("status") String[] status);

    /**
     * 跟新订单状态与 备注信息remark
     * 
     * @param bizSeqNo 借款业务流水号
     * @param newStatus 更新前的状态
     * @param oldStatus 更新后的状态
     * @param remark 风控失败原因/风控分数
     * @return count 影响行数
     */
    int updateOrderStatusAndRemark(@Param("bizSeqNo") String bizSeqNo,
            @Param("newStatus") String newStatus, @Param("oldStatus") String oldStatus,
            @Param("remark") String remark);

    int updateOrderReviewFailTime(@Param("bizSeqNo") String bizSeqNo,
                                   @Param("reviewFailTime") Date reviewFailTime);

    /**
     * 根据订单表ID查询订单数据
     * @param id
     * @return
     */
    LoanOrder selectLoanOrderById(@Param("id") Long id);

    LoanOrder selectLoanOrderByBizSeqNoAndStatus(@Param("bizSeqNo") String bizSeqNo ,@Param("status") String Status);

    List<ManualReviewLoanOrderDto> selectLoanOrderListByStatus(@Param("status") String status);

    List<LoanOrder> selectListByStatus(@Param("status") String status);

    int updateOrderLoanTime(@Param("id") Long id , @Param("loanTime")Date loanTime);

    Long getUserIdByOrderBizSeqNo(String bizSeqNo);

    List<OrderDetailDto> getOrderApplyList(OrderApplyReq req);

    List<OrderDetailDto> getLoanAuditList(OrderApplyReq req);

    Integer getCountByStatus(@Param("status") String code);

    Integer getSumMoneyByStatus(@Param("status") String code);

    /**
     * 获取风控审核失败或人工审核失败状态的订单
     *
     * @param productId 产品ID
     * @return
     */
    List<UnfreezeOrdersResponse> selectUnfreezeOrders(@Param("productId") Long productId, @Param("status") String[] status, @Param("reviewFailTime") Date reviewFailTime);

    /**
     *
     * @param id
     * @param newStatus
     */
    int updateOrderStatusById(@Param("id") Long id,@Param("newStatus") String newStatus,@Param("oldStatus") String oldStatus,@Param("reviewFailTime") Date reviewFailTime);
}
