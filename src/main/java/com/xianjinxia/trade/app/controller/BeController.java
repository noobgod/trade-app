package com.xianjinxia.trade.app.controller;

import com.dianping.cat.common.EventInfo;
import com.dianping.cat.utils.CatUtils;
import com.github.pagehelper.PageInfo;
import com.xianjinxia.trade.app.dto.OrderDetailDto;
import com.xianjinxia.trade.app.request.AuditOperationReq;
import com.xianjinxia.trade.app.request.OrderApplyReq;
import com.xianjinxia.trade.app.response.BaseResponse;
import com.xianjinxia.trade.app.response.BaseResponseExt;
import com.xianjinxia.trade.app.response.LoanBaseResponse;
import com.xianjinxia.trade.app.service.BeService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by liquan on 2017/12/25.
 */
@RestController
@RequestMapping("/be")
public class BeController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(BeController.class);

    @Autowired
    private BeService beService;

    /**
     * 订单申请列表
     * @param req
     * @return
     */
    @PostMapping("/businessManagement/getOrderApplyList")
    @ApiOperation(value = "loan-orders",httpMethod = "POST",notes = "获取订单申请列表")
    public BaseResponse<PageInfo<OrderDetailDto>> getOrderApplyList(@RequestBody OrderApplyReq req) {

        BaseResponse<PageInfo<OrderDetailDto>> response = new BaseResponse<>();
        //校验入参是否非空
        if(!req.paramCheck(response)){
            return response;
        }
        try {
            // cat 日志收集
            EventInfo eventInfo = new EventInfo();
            eventInfo.put("loanId", req.getLoanId());
            eventInfo.setEventType("users-loan-orders");
            CatUtils.info(eventInfo);
            PageInfo<OrderDetailDto> pageInfo = beService.getOrderApplyList(req);
            response.setData(pageInfo);
            eventInfo.setMessage("order apply is {"+req.toString()+"}");
            // cat日志收集结束
            eventInfo.endSend();
        }catch (Exception e){
            logger.error("查询订单申请列表失败",e);
            response.systemError();
        }
        return  response;
    }


    /**
     * 放款人审列表
     * @param req
     * @return
     */
    @PostMapping("/businessManagement/getLoanAuditList")
    @ApiOperation(value = "loan-orders",httpMethod = "POST",notes = "获取放款人审列表")
    public BaseResponseExt<PageInfo<OrderDetailDto>> getLoanAuditList(@RequestBody OrderApplyReq req) {

        BaseResponseExt<PageInfo<OrderDetailDto>> response = new BaseResponseExt<>();
        try {
            // cat 日志收集
            EventInfo eventInfo = new EventInfo();
            eventInfo.put("loanId", req.getLoanId());
            eventInfo.setEventType("users-loan-orders");
            CatUtils.info(eventInfo);
            PageInfo<OrderDetailDto> pageInfo = beService.getLoanAuditList(req);
            response.setData(pageInfo);
            Map<String,Integer> map = beService.getStatisticalQuantity();
            response.setLoanMoneyEnd(map.get("loanMoneyEnd"));
            response.setLoanNumberEnd(map.get("loanNumberEnd"));
            response.setWaitAuditNum(map.get("waitAuditNum"));
            eventInfo.setMessage("order apply is {"+req.toString()+"}");
            // cat日志收集结束
            eventInfo.endSend();
        }catch (Exception e){
            logger.error("查询放款人审列表失败",e);
            response.systemError();
        }
        return  response;
    }

    /**
     * 放款人审列表>>审批操作
     * @param req
     * @return
     */
    @PostMapping("/businessManagement/auditOperation")
    @ApiOperation(value = "loan-orders",httpMethod = "POST",notes = "审批操作")
    public BaseResponse<LoanBaseResponse> auditOperation(@RequestBody AuditOperationReq req) {

        BaseResponse<LoanBaseResponse> response = new BaseResponse<>();
        //校验入参是否非空
        if(!req.paramCheck(response)){
            return response;
        }
        try {
            // cat 日志收集
            EventInfo eventInfo = new EventInfo();
            eventInfo.put("ids", req.getIds());
            eventInfo.setEventType("users-loan-orders");
            CatUtils.info(eventInfo);
            beService.auditOperation(req);
            eventInfo.setMessage("auditOperation is {"+req.toString()+"}");
            // cat日志收集结束
            eventInfo.endSend();
        }catch (Exception e){
            logger.error("查询放款人审列表失败",e);
            response.systemError();
        }
        return  response;
    }


}
