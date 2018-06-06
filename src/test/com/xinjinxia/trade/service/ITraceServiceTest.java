package com.xinjinxia.trade.service;

import com.xianjinxia.trade.shared.domain.Trace;
import com.xianjinxia.trade.app.dto.TraceDto;
import com.xianjinxia.trade.app.service.ITraceService;
import com.xinjinxia.trade.BaseSpringTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author zhangjiayuan
 * @create 2017-11-01 14:02
 **/
public class ITraceServiceTest extends BaseSpringTest {
    @Autowired
    ITraceService traceService;

    /**
     * 查询 订单轨迹
     */
    @Test
    public void  selectByUserIdAndOrderIdTest(){
        TraceDto dto =new TraceDto();
        dto.setLoanOrderId(67L);
        dto.setUserId(427L);
        List<Trace> traces = traceService.selectByUserIdAndOrderId(dto);
        log.info("查询到的数据:{}",traces);
    }



}
