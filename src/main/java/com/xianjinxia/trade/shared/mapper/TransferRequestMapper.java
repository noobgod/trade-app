package com.xianjinxia.trade.shared.mapper;

import com.xianjinxia.trade.shared.domain.TransferRequest;
import org.apache.ibatis.annotations.Param;

public interface TransferRequestMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TransferRequest record);

    int insertSelective(TransferRequest record);

    TransferRequest selectByPrimaryKey(Long id);

    TransferRequest selectByReqNo(String id);

    int updateByPrimaryKeySelective(TransferRequest record);

    int updateByReqNoStatus(@Param("reqNo") String reqNo, @Param("curStatus") String curStatus,
                            @Param("preStatus") String preStatus);

    int updateByPrimaryKey(TransferRequest record);
}