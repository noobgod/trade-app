package com.xianjinxia.trade.shared.mapper;

import com.xianjinxia.trade.shared.domain.TransferReqeustDetails;
import org.apache.ibatis.annotations.Param;

public interface TransferReqeustDetailsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TransferReqeustDetails record);

    int insertSelective(TransferReqeustDetails record);

    TransferReqeustDetails selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TransferReqeustDetails record);

    int updateByReqIdStatus(@Param("reqId") Long reqId, @Param("curStatus") String curStatus,
                            @Param("preStatus") String preStatus);

    int updateByPrimaryKey(TransferReqeustDetails record);
}