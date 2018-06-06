package com.xianjinxia.trade.app.service;

import java.util.List;

import com.xianjinxia.trade.app.dto.TraceDto;
import com.xianjinxia.trade.shared.domain.Trace;
import com.xianjinxia.trade.shopping.dto.ShoppingTraceDto;
import com.xianjinxia.trade.shopping.request.app.ShoppingTraceReq;

/**
 * 
 * @author liuzhifang
 *
 *         2017年9月6日
 */
public interface ITraceService {


    List<Trace> selectByUserIdAndOrderId(TraceDto traceDto);
    
    /**
     * 生成traceNo
     * @param shoppingTraceReq
     * @return
     */
    ShoppingTraceDto createTrace(ShoppingTraceReq shoppingTraceReq);
}
