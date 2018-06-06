package com.xianjinxia.trade.app.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xianjinxia.trade.app.dto.TraceDto;
import com.xianjinxia.trade.app.service.ITraceService;
import com.xianjinxia.trade.shared.domain.Trace;
import com.xianjinxia.trade.shared.enums.LoanCodeMsgEnum;
import com.xianjinxia.trade.shared.enums.TraceOrderEventEnum;
import com.xianjinxia.trade.shared.mapper.TraceMapper;
import com.xianjinxia.trade.shared.utils.GsonUtil;
import com.xianjinxia.trade.shared.utils.UniqueIdUtil;
import com.xianjinxia.trade.shopping.dto.ShoppingTraceDto;
import com.xianjinxia.trade.shopping.request.app.ShoppingTraceReq;

/**
 * 
 * @author liuzhifang
 *
 *  2017年9月6日
 */
@Service
public class TraceService implements ITraceService {

    @Autowired
    private TraceMapper traceMapper;

    @Override
    public List<Trace> selectByUserIdAndOrderId(TraceDto traceDto) {
        return traceMapper.selectByUserIdAndOrderId(traceDto);
    }

	@Override
	public ShoppingTraceDto createTrace(
			ShoppingTraceReq shoppingTraceReq) {
		String traceNo = UniqueIdUtil.getTraceNoUniqueId();// 创建unique trace no
		ShoppingTraceDto shoppingTraceDto = new ShoppingTraceDto(
				LoanCodeMsgEnum.SUCCESS, traceNo);
		Trace trace = new Trace(traceNo,
				TraceOrderEventEnum.CREATE_TRACE.getCode(),
				TraceOrderEventEnum.CREATE_TRACE.getText(), new Date(),
				GsonUtil.toGson(shoppingTraceReq));
		traceMapper.insert(trace);
		return shoppingTraceDto;
	}

}
