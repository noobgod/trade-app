package com.xianjinxia.trade.shopping.controller;

import com.github.pagehelper.PageInfo;
import com.xianjinxia.trade.app.controller.BaseController;
import com.xianjinxia.trade.app.response.BaseResponse;
import com.xianjinxia.trade.shared.domain.TrdShoppingLoanOrder;
import com.xianjinxia.trade.shared.domain.TrdShoppingLogisticsOrder;
import com.xianjinxia.trade.shared.enums.ShoppingLoanOrderStatusEnum;
import com.xianjinxia.trade.shared.exception.ServiceException;
import com.xianjinxia.trade.shopping.dto.ShoppingProductOrderFinanceDto;
import com.xianjinxia.trade.shopping.dto.ShoppingProductOrderFinanceSummaryDto;
import com.xianjinxia.trade.shopping.dto.ShoppingVirtualProductOrderFinanceDto;
import com.xianjinxia.trade.shopping.dto.ShoppingVirtualProductOrderFinanceSummaryDto;
import com.xianjinxia.trade.shopping.request.bgd.FinanceShoppingOrderRequest;
import com.xianjinxia.trade.shopping.request.bgd.ShoppingLogisticsOrderEditRequest;
import com.xianjinxia.trade.shopping.response.bgd.RepaymentPlanDto;
import com.xianjinxia.trade.shopping.response.bgd.ShoppingLoanOrderDto;
import com.xianjinxia.trade.shopping.response.bgd.ShoppingLogisticsOrderDto;
import com.xianjinxia.trade.shopping.response.bgd.ShoppingProductOrderDto;
import com.xianjinxia.trade.shopping.service.*;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @author zhangyongjia
 */
@Api(tags = "trade_app shopping background controller")
@RequestMapping("/service/shopping/bgd")
@RestController
public class TradeShoppingBackgroundController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(TradeShoppingBackgroundController.class);

    @Autowired
    private IShoppingLoanService shoppingLoanService;

    @Autowired
    private ITrdShoppingLoanOrderService trdShoppingLoanOrderService;

    @Autowired
    private ITrdShoppingProductOrderService trdShoppingProductOrderService;

    @Autowired
    private ITrdShoppingLogisticsOrderService trdShoppingLogisticsOrderService;

    @Autowired
    private ITrdShoppingOrderFinanceService trdShoppingOrderFinanceService;


    @PostMapping("/loan-order-list")
    public BaseResponse<PageInfo<ShoppingLoanOrderDto>> getLoanOrderList(@RequestBody @Valid ShoppingLoanOrderDto params) {
        BaseResponse<PageInfo<ShoppingLoanOrderDto>> response = new BaseResponse<>();
        try {
            PageInfo<TrdShoppingLoanOrder> loanOrderPageList = trdShoppingLoanOrderService.getLoanOrderPageList(params);
            List<TrdShoppingLoanOrder> trdShoppingLoanOrderList = loanOrderPageList.getList();
            List<ShoppingLoanOrderDto> shoppingLoanOrderDtos = new ArrayList<>(trdShoppingLoanOrderList.size());
            for (Iterator<TrdShoppingLoanOrder> iterator = trdShoppingLoanOrderList.iterator(); iterator.hasNext(); ) {
                TrdShoppingLoanOrder trdShoppingLoanOrder = iterator.next();
                ShoppingLoanOrderDto shoppingLoanOrderDto = new ShoppingLoanOrderDto();
                BeanUtils.copyProperties(trdShoppingLoanOrder, shoppingLoanOrderDto);
                shoppingLoanOrderDto.setCreatedTime(trdShoppingLoanOrder.getCreatedAt().getTime());
                if (trdShoppingLoanOrder.getStatus().equals(ShoppingLoanOrderStatusEnum.SETTLED.getCode())){
                    shoppingLoanOrderDto.setUpdatedTime(trdShoppingLoanOrder.getUpdatedAt().getTime());
                }
                shoppingLoanOrderDto.setOrderAmount(shoppingLoanOrderDto.getOrderAmount()/100);
                shoppingLoanOrderDto.setStatus(ShoppingLoanOrderStatusEnum.getByCode(trdShoppingLoanOrder.getStatus()).getValue());
                shoppingLoanOrderDtos.add(shoppingLoanOrderDto);
            }

            PageInfo<ShoppingLoanOrderDto> pageInfo = new PageInfo<>();
            BeanUtils.copyProperties(loanOrderPageList, pageInfo);
            pageInfo.setList(shoppingLoanOrderDtos);


            response.setData(pageInfo);
            return response;
        } catch (ServiceException se) {
            response.setCode(se.getCode());
            response.setMsg(se.getMsg());
            return response;
        } catch (Exception e) {
            logger.error("service/shopping/sync-order-status  has  exception :{}", e);
            response.systemError();
            return response;
        }


    }

    @GetMapping("/repayment-plan-list")
    public BaseResponse<List<RepaymentPlanDto>> getRepaymentPlanByLoanOrderId(@RequestParam("loanOrderId") String loanOrderId) {
        BaseResponse<List<RepaymentPlanDto>> response = new BaseResponse<>();
        try {
            List<RepaymentPlanDto> repaymentPlanList = trdShoppingLoanOrderService.getRepaymentPlanList(loanOrderId);
            
            for (RepaymentPlanDto repaymentPlanDto : repaymentPlanList) {

			}
            response.setData(repaymentPlanList);
            return response;
        } catch (ServiceException se) {
            response.setCode(se.getCode());
            response.setMsg(se.getMsg());
            return response;
        } catch (Exception e) {
            logger.error("service/shopping/sync-order-status  has  exception :{}", e);
            response.systemError();
            return response;
        }
    }

    @PostMapping("/product-order-list")
    public BaseResponse<PageInfo<ShoppingProductOrderDto>> getProductOrderList(@RequestBody @Valid ShoppingProductOrderDto params) {
        BaseResponse<PageInfo<ShoppingProductOrderDto>> response = new BaseResponse<>();
        try {
            PageInfo<ShoppingProductOrderDto> productOrderPageList = trdShoppingProductOrderService.getProductOrderPageList(params);
            response.setData(productOrderPageList);
            return response;
        } catch (ServiceException se) {
            response.setCode(se.getCode());
            response.setMsg(se.getMsg());
            return response;
        } catch (Exception e) {
            logger.error("service/shopping/sync-order-status  has  exception :{}", e);
            response.systemError();
            return response;
        }
    }

    @GetMapping("/product-order")
    public BaseResponse<ShoppingProductOrderDto> getProductOrder(@RequestParam("productOrderId") String productOrderId) {
        BaseResponse<ShoppingProductOrderDto> response = new BaseResponse<>();
        try {
            ShoppingProductOrderDto shoppingProductOrderDto = trdShoppingProductOrderService.getById(Long.parseLong(productOrderId));
            response.setData(shoppingProductOrderDto);
            return response;
        } catch (ServiceException se) {
            response.setCode(se.getCode());
            response.setMsg(se.getMsg());
            return response;
        } catch (Exception e) {
            logger.error("service/shopping/sync-order-status  has  exception :{}", e);
            response.systemError();
            return response;
        }
    }

    @PostMapping("/logistics-order-list")
    public BaseResponse<PageInfo<ShoppingLogisticsOrderDto>> getLogisticsOrderList(@RequestBody @Valid ShoppingLogisticsOrderDto params) {
        BaseResponse<PageInfo<ShoppingLogisticsOrderDto>> response = new BaseResponse<>();
        try {
            PageInfo<ShoppingLogisticsOrderDto> trdShoppingLogisticsOrderPageInfo = trdShoppingLogisticsOrderService.getLogisticsOrderPageList(params);

            response.setData(trdShoppingLogisticsOrderPageInfo);
            return response;
        } catch (ServiceException se) {
            response.setCode(se.getCode());
            response.setMsg(se.getMsg());
            return response;
        } catch (Exception e) {
            logger.error("service/shopping/sync-order-status  has  exception :{}", e);
            response.systemError();
            return response;
        }
    }

    @GetMapping("/logistics-order")
    public BaseResponse<ShoppingLogisticsOrderDto> getLogisticsOrder(@RequestParam("logisticsOrderId") String logisticsOrderId) {
        BaseResponse<ShoppingLogisticsOrderDto> response = new BaseResponse<>();

        try {
            ShoppingLogisticsOrderDto shoppingLogisticsOrderDto = trdShoppingLogisticsOrderService.getShoppingLogisticsOrderDtoById(Long.parseLong(logisticsOrderId));
            shoppingLogisticsOrderDto.setProductPrice(shoppingLogisticsOrderDto.getProductPrice()/100);
            shoppingLogisticsOrderDto.setLogisticsPrice(shoppingLogisticsOrderDto.getLogisticsPrice() == null ? 0 : shoppingLogisticsOrderDto.getLogisticsPrice()/100);
            response.setData(shoppingLogisticsOrderDto);
            return response;
        } catch (ServiceException se) {
            response.setCode(se.getCode());
            response.setMsg(se.getMsg());
            return response;
        } catch (Exception e) {
            logger.error("service/shopping/logistics-order has  exception :{}", e);
            response.systemError();
            return response;
        }
    }

    @PostMapping("/edit-logistics-order")
    public BaseResponse<Void> editLogisticsOrder(@RequestBody ShoppingLogisticsOrderEditRequest req) {
        BaseResponse<Void> response = new BaseResponse();
        try {
            TrdShoppingLogisticsOrder trdShoppingLogisticsOrder = new TrdShoppingLogisticsOrder();
            BeanUtils.copyProperties(req, trdShoppingLogisticsOrder);
            trdShoppingLogisticsOrderService.updateLogisticsOrder(trdShoppingLogisticsOrder, false);
            return response;
        } catch (ServiceException se) {
            response.setCode(se.getCode());
            response.setMsg(se.getMsg());
            return response;
        } catch (Exception e) {
            logger.error("service/shopping/sync-order-status  has  exception :{}", e);
            response.systemError();
            return response;
        }

    }

    @PostMapping("/deliver-logistics-order")
    public BaseResponse<Void> deliver(@RequestBody ShoppingLogisticsOrderEditRequest req) {
        BaseResponse<Void> response = new BaseResponse();
        try {
            TrdShoppingLogisticsOrder trdShoppingLogisticsOrder = new TrdShoppingLogisticsOrder();
            BeanUtils.copyProperties(req, trdShoppingLogisticsOrder);
            trdShoppingLogisticsOrder.setSendTime(new Date(req.getSendTime()));
            trdShoppingLogisticsOrderService.updateLogisticsOrder(trdShoppingLogisticsOrder, true);
            return new BaseResponse();
        } catch (ServiceException se) {
            response.setCode(se.getCode());
            response.setMsg(se.getMsg());
            return response;
        } catch (Exception e) {
            logger.error("service/shopping/sync-order-status  has  exception :{}", e);
            response.systemError();
            return response;
        }

    }
    @RequestMapping("/finance/product-order-list-summary")
    public BaseResponse<ShoppingProductOrderFinanceSummaryDto> productOrderListSummary(@RequestBody FinanceShoppingOrderRequest req) {
        BaseResponse<ShoppingProductOrderFinanceSummaryDto> response = new BaseResponse();
        try {
            ShoppingProductOrderFinanceSummaryDto result = trdShoppingOrderFinanceService.selectSummaryByParameter(req);
            response.setData(result);
            return response;
        } catch (ServiceException se) {
            response.setCode(se.getCode());
            response.setMsg(se.getMsg());
            return response;
        } catch (Exception e) {
            logger.error("service/shopping/finance/product-order-list-summary  has  exception :{}", e);
            response.systemError();
            return response;
        }

    }
    @RequestMapping("/finance/product-order-list")
    public BaseResponse<PageInfo<ShoppingProductOrderFinanceDto>> productOrderList(@RequestBody FinanceShoppingOrderRequest req) {
        BaseResponse<PageInfo<ShoppingProductOrderFinanceDto>> response = new BaseResponse();
        try {
            PageInfo<ShoppingProductOrderFinanceDto> list = trdShoppingOrderFinanceService.selectByParameter(req);
            response.setData(list);
            return response;
        } catch (ServiceException se) {
            response.setCode(se.getCode());
            response.setMsg(se.getMsg());
            return response;
        } catch (Exception e) {
            logger.error("service/shopping/finance/product-order-list  has  exception :{}", e);
            response.systemError();
            return response;
        }

    }
    @RequestMapping("/finance/virtual-order-list-summary")
    public BaseResponse<ShoppingVirtualProductOrderFinanceSummaryDto> virtualOrderListSummary(@RequestBody FinanceShoppingOrderRequest req) {
        BaseResponse<ShoppingVirtualProductOrderFinanceSummaryDto> response = new BaseResponse();
        try {
            ShoppingVirtualProductOrderFinanceSummaryDto result = trdShoppingOrderFinanceService.selectVirtualOrderSummaryByParameter(req);
            response.setData(result);
            return response;
        } catch (ServiceException se) {
            response.setCode(se.getCode());
            response.setMsg(se.getMsg());
            return response;
        } catch (Exception e) {
            logger.error("service/shopping/finance/virtual-order-list-summary  has  exception :{}", e);
            response.systemError();
            return response;
        }

    }
    @RequestMapping("/finance/virtual-order-list")
    public BaseResponse<PageInfo<ShoppingVirtualProductOrderFinanceDto>> virtualOrderList(@RequestBody FinanceShoppingOrderRequest req) {
        BaseResponse<PageInfo<ShoppingVirtualProductOrderFinanceDto>> response = new BaseResponse();
        try {
            PageInfo<ShoppingVirtualProductOrderFinanceDto> list = trdShoppingOrderFinanceService.selectVirtualOrderByParameter(req);
            response.setData(list);
            return response;
        } catch (ServiceException se) {
            response.setCode(se.getCode());
            response.setMsg(se.getMsg());
            return response;
        } catch (Exception e) {
            logger.error("service/shopping/finance/virtual-order-list  has  exception :{}", e);
            response.systemError();
            return response;
        }

    }

}
