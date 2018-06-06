package com.xianjinxia.trade.shared.mapper;

import com.xianjinxia.trade.shared.domain.Trace;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TraceMapperTest {

    @Autowired
    private TraceMapper traceMapper;

    private String traceNo = "123";
    private String orderEvent = "oe";
    private String eventText = "eventText";
    private String traceData = "traceData";

    private Trace getTrace() {
        Trace trace = new Trace();
        trace.setEventTime(new Date());
        trace.setTraceNo(traceNo);
        trace.setCreatedTime(new Date());
        trace.setOrderEvent(orderEvent);
        trace.setEventText(eventText);
        trace.setTraceData(traceData);
        trace.setDataValid(true);
        trace.setUpdatedTime(new Date());
        return trace;
    }

    @Test
    @Transactional
    public void insert() throws Exception {
        Trace trace = getTrace();
        int result = traceMapper.insert(trace);
        Trace aimTrace = traceMapper.selectByPrimaryKey(trace.getId());
        Assert.assertEquals(1, result);
        Assert.assertEquals(traceNo, aimTrace.getTraceNo());
        Assert.assertEquals(orderEvent, aimTrace.getOrderEvent());
        Assert.assertEquals(eventText, aimTrace.getEventText());
        Assert.assertEquals(traceData, aimTrace.getTraceData());
    }

    @Test
    @Transactional
    public void selectTraceNoCount() throws Exception {
        Trace trace = getTrace();
        int result = traceMapper.selectTraceNoCount(trace.getTraceNo());
        Assert.assertEquals(1, result);
    }

}