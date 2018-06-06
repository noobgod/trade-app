package com.xianjinxia.trade.shared.mapper;

import com.xianjinxia.trade.shared.domain.TrdSaleOrderRequest;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface TrdSaleOrderRequestMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TrdSaleOrderRequest record);

    int insertSelective(TrdSaleOrderRequest record);

    TrdSaleOrderRequest selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TrdSaleOrderRequest record);

    int updateByPrimaryKey(TrdSaleOrderRequest record);

    /**
     * 根据状态获取出售列表
     * @param status
     * @return
     */
    List<TrdSaleOrderRequest> selectByStatus(@Param("status") String status, @Param("sortBy") String sortBy);

    List<TrdSaleOrderRequest> selectByStatusPrices(@Param("status") String status,
                                             @Param("lowPrice") BigDecimal lowPrice,
                                             @Param("highPrice") BigDecimal highPrice,
                                             @Param("sortBy") String sortBy);

    int selectCntByStatusPrices(@Param("status") String status,
                                                   @Param("lowPrice") BigDecimal lowPrice,
                                                   @Param("highPrice") BigDecimal highPrice,
                                                   @Param("sortBy") String sortBy);

    /**
     * 取消出售
     * @param userId
     * @param petId
     * @return
     */
    TrdSaleOrderRequest selectUserSalePet(@Param("userId") Long userId,
                                          @Param("petId") Long petId);

    /**
     * 查询出售单信息
     * @param userId
     * @param saleNo
     * @return
     */
    TrdSaleOrderRequest selectSaleBySaleNoUserId(@Param("userId") Long userId,
                                                 @Param("saleNo") String saleNo);

    /**
     * 查询出售单详情
     * @param saleNo
     * @return
     */
    TrdSaleOrderRequest selectSaleBySaleNo(@Param("saleNo") String saleNo);

    /**
     * 取消出售
     * @param userId
     * @param saleNo
     * @return
     */
    int updateByUserSaleNo(@Param("userId") Long userId,
                                          @Param("saleNo") String saleNo,@Param("curStatus") String curStatus,
                                          @Param("preStatus") String preStatus);
}