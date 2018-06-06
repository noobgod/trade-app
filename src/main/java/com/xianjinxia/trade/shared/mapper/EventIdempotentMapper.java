package com.xianjinxia.trade.shared.mapper;

import com.xianjinxia.trade.shared.domain.EventIdempotent;

public interface EventIdempotentMapper {
    int deleteByPrimaryKey(Long id);

    int insert(EventIdempotent record);

    int insertSelective(EventIdempotent record);

    EventIdempotent selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(EventIdempotent record);

    int updateByPrimaryKey(EventIdempotent record);

    int saveEventIdempotent(EventIdempotent eventIdempotent);
}