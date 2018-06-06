package com.xianjinxia.trade.app.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xianjinxia.trade.app.dto.OrderDetailDto;
import com.xianjinxia.trade.app.request.AuditOperationReq;
import com.xianjinxia.trade.app.request.ManualReviewStatusReq;
import com.xianjinxia.trade.app.request.OrderApplyReq;
import com.xianjinxia.trade.app.service.BeService;
import com.xianjinxia.trade.app.service.ILoanService;
import com.xianjinxia.trade.shared.enums.LoanOrderStatusEnum;
import com.xianjinxia.trade.shared.mapper.LoanOrderMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: cff
 * Date: 2017-12-25
 * Time: 下午 5:06
 */
@Service
public class BeServiceImpl implements BeService {
    private static final Logger logger = LoggerFactory.getLogger(BeServiceImpl.class);
    @Autowired
    private LoanOrderMapper loanOrderMapper;
    @Autowired
    private ILoanService loanService;

    @Override
    public PageInfo<OrderDetailDto> getOrderApplyList(OrderApplyReq req) {

        PageHelper.startPage(req.getPageNum(), req.getPageSize());
        Page<OrderDetailDto> page = (Page<OrderDetailDto>) loanOrderMapper.getOrderApplyList(req);

        return page.toPageInfo();
    }

    @Override
    public PageInfo<OrderDetailDto> getLoanAuditList(OrderApplyReq req) {
        PageHelper.startPage(req.getPageNum(), req.getPageSize());
        Page<OrderDetailDto> page = (Page<OrderDetailDto>) loanOrderMapper.getLoanAuditList(req);

        return page.toPageInfo();
    }

    @Override
    public Map<String, Integer> getStatisticalQuantity() {
        Map<String,Integer> map = new HashMap<String,Integer>();

        //修改于2018-01-05 张永嘉
        //Integer waitAuditNum = loanOrderMapper.getCountByStatus(LoanOrderStatusEnum.MANUALREVIEWING.getCode());//
        Integer waitAuditNum = loanOrderMapper.getCountByStatus(LoanOrderStatusEnum.NEW_PUSH_SUCCESS.getCode());

        Integer loanNumberEnd = loanOrderMapper.getCountByStatus(LoanOrderStatusEnum.LOAN_SUCCESS.getCode());
        Integer loanMoneyEnd = loanOrderMapper.getSumMoneyByStatus(LoanOrderStatusEnum.LOAN_SUCCESS.getCode());
        logger.debug("waitAuditNum=" + waitAuditNum + ",loanNumberEnd=" + loanNumberEnd + ",loanMoneyEnd=" + loanMoneyEnd);
        map.put("waitAuditNum",waitAuditNum);
        map.put("loanNumberEnd",loanNumberEnd);
        map.put("loanMoneyEnd",loanMoneyEnd);
        return map;
    }

    @Override
    public void auditOperation(AuditOperationReq req) {
        logger.info("放款人审列表>>审批操作：" + JSON.toJSONString(req));
        ManualReviewStatusReq manualReviewStatusReq = new ManualReviewStatusReq();
        String[] bizSeqNo = req.getIds().split(",");
        for (int i=0 ; i<bizSeqNo.length ; i++) {
            manualReviewStatusReq.setBizSeqNo(bizSeqNo[i]);
            manualReviewStatusReq.setManualReviewIsPass(req.getOperationType());
            loanService.updateManualReviewStatus(manualReviewStatusReq);
        }

    }
}
