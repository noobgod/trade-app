package com.xianjinxia.trade.shopping.service;

import com.github.pagehelper.PageInfo;
import com.xianjinxia.trade.app.dto.LoanOrderDto;
import com.xianjinxia.trade.app.request.LoanDetailReq;
import com.xianjinxia.trade.shared.domain.TrdShoppingLoanOrder;
import com.xianjinxia.trade.shopping.dto.ShoppingLoanOrderContractDto;
import com.xianjinxia.trade.shopping.response.bgd.RepaymentPlanDto;
import com.xianjinxia.trade.shopping.response.bgd.ShoppingLoanOrderDto;

import java.util.Date;
import java.util.List;

/**
 * @author chunliny
 */
public interface ITrdShoppingLoanOrderService {
    void insert(TrdShoppingLoanOrder trdShoppingLoanOrder);

    Integer getUnFinishOrderCount(Long userId, String[] finalStatusArray);

    List<TrdShoppingLoanOrder> getRejectShoppingLoanOrders(Long userId, String[] finalStatusArray);

    Integer countByTraceNo(String traceNo);

    int updateLoanOrderStatusToPending(Long id, String newStatus, String preStatus);

    int updateByUserIdAndStatus(Long userId, String newStatus, String preStatus);

    TrdShoppingLoanOrder getById(Long id);

    int updateLoanOrderStatus(Long id, String newStatus, String preStatus);

    int updateVirtualLoanOrderStatus(Long id, String newStatus, String preStatus, Date thirdOrderTime);

    //分页查询订单列表
    PageInfo<TrdShoppingLoanOrder> getLoanOrderPageList(ShoppingLoanOrderDto params);

    List<RepaymentPlanDto> getRepaymentPlanList(String loanOrderId);

    PageInfo<LoanOrderDto> conditionalPaging(LoanDetailReq req);

    ShoppingLoanOrderContractDto getLoanContractDto(Long id);

    List<TrdShoppingLoanOrder> selectListByStatusAndKind(String status, Integer kindId);


}