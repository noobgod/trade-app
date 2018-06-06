package com.xianjinxia.trade.shared.mapper;

import com.xianjinxia.trade.shared.domain.PlatformFeeDetail;

public interface PlatformFeeDetailMapper {

    int insert(PlatformFeeDetail record);

    PlatformFeeDetail selectByPrimaryKey(Long id);


    int updateByPrimaryKey(PlatformFeeDetail record);
}