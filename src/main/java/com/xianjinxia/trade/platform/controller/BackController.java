package com.xianjinxia.trade.platform.controller;

import com.xianjinxia.trade.app.controller.BaseController;
import com.xianjinxia.trade.app.response.BaseResponse;
import com.xianjinxia.trade.platform.request.BackOrderRequest;
import com.xianjinxia.trade.platform.response.BackOrderResp;
import com.xianjinxia.trade.platform.service.PlatformLoanOrderService;
import com.xianjinxia.trade.shared.page.PageObj;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author meijunhui
 * <p>
 * 2017年11月25日
 */
@RestController
@RequestMapping("/back/service/platform")
public class BackController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(BackController.class);

    @Autowired
    private PlatformLoanOrderService platformLoanOrderService;

    /**
     * 后台订单查询接口，支持多条件查询
     * @param orderRequest 查询参数对象
     * @return
     */
    @PostMapping("/orders")
    public BaseResponse<PageObj<BackOrderResp>> getOrders(@RequestBody BackOrderRequest orderRequest){
        BaseResponse<PageObj<BackOrderResp>> response=new BaseResponse<>();
        try{
            if(!orderRequest.paramCheck(response)){
                return response;
            }
            if(checkBackOrderRequestIsNull(orderRequest)){
                response.paramCheckFail();
                response.setMsg("must be input one search param");
                return response;
            }
            response.setData(platformLoanOrderService.getOrders(orderRequest));
        }catch(Exception e){
            logger.error("/back/service/paltform/orders error",e);
            response.systemError();
        }
        return response;
    }

    public boolean checkBackOrderRequestIsNull(BackOrderRequest orderRequest){
        if(StringUtils.isBlank(orderRequest.getOrderNo()) && StringUtils.isBlank(orderRequest.getServiceCompany())
              &&  StringUtils.isBlank(orderRequest.getStatus()) && StringUtils.isBlank(orderRequest.getUserId())
              &&  StringUtils.isBlank(orderRequest.getUserPhone()) && StringUtils.isBlank(orderRequest.getProductCode())
              &&  orderRequest.getBeginCreatedTime()==null && orderRequest.getEndCreatedTime()==null){
            return true;
        }
        return false;
    }


}
