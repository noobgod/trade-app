package com.xianjinxia.trade.shared.mapper;

import com.xianjinxia.trade.shared.domain.TrdSettleRecord;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface TrdSettleRecordMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TrdSettleRecord record);

    int insertSelective(TrdSettleRecord record);

    TrdSettleRecord selectByPrimaryKey(Long id);

    /**
     * 我的订单
     * @param status 状态不是购买中-非该状态的数据
     * @param userId
     * @return
     */
    List<TrdSettleRecord> selectOrdersByUserId(@Param("userId") Long userId, @Param("status") String status);

    int updateStatusByIdPreStatus(@Param("id") Long id, @Param("curStatus") String curStatus,
                                  @Param("preStatus") String preStatus,
                                  @Param("actualServiceCharge") BigDecimal actualServiceCharge);

    int updateStatusByNoPreStatus(@Param("settleNo") String settleNo, @Param("curStatus") String curStatus,
                                  @Param("preStatus") String preStatus,
                                  @Param("actualServiceCharge") BigDecimal actualServiceCharge);

    int updateByPrimaryKeySelective(TrdSettleRecord record);

    int updateByPrimaryKey(TrdSettleRecord record);

    /**
     * 我的订单
     * @param status 状态
     * @return
     */
    List<TrdSettleRecord> selectOrdersByStatus(@Param("status") String status);


    /**
     * 交割单-根据交割单号查询
     * @param settleNo
     * @return
     */
    TrdSettleRecord selectOrdersBySettleNo(@Param("settleNo") String settleNo);
}