package com.xianjinxia.trade.shared.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.xianjinxia.trade.shared.domain.Trace;
import com.xianjinxia.trade.app.dto.TraceDto;
import com.xianjinxia.trade.platform.request.TraceReq;

@Mapper
public interface TraceMapper {

    int insert(Trace trace);

    Trace selectByPrimaryKey(Long id);

    List<Trace> selectByUserIdAndOrderId(TraceDto trace);

    Integer selectTraceNoCount(@Param("traceNo") String traceNo);
    
    List<Trace> selectTraceListByOrderNo(TraceReq traceReq);

}
