package com.xianjinxia.trade.app.service;

import com.github.pagehelper.PageInfo;
import com.xianjinxia.trade.app.dto.OrderDetailDto;
import com.xianjinxia.trade.app.request.AuditOperationReq;
import com.xianjinxia.trade.app.request.OrderApplyReq;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: cff
 * Date: 2017-12-25
 * Time: 下午 5:05
 */
public interface BeService {

    PageInfo<OrderDetailDto> getOrderApplyList(OrderApplyReq req);

    PageInfo<OrderDetailDto> getLoanAuditList(OrderApplyReq req);

    Map<String, Integer> getStatisticalQuantity();

    void auditOperation(AuditOperationReq req);
}
