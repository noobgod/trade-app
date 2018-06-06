package com.xianjinxia.trade.shopping.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xianjinxia.trade.app.dto.LoanOrderDto;
import com.xianjinxia.trade.app.request.LoanDetailReq;
import com.xianjinxia.trade.shared.domain.TrdShoppingLoanOrder;
import com.xianjinxia.trade.shared.domain.TrdShoppingProductOrder;
import com.xianjinxia.trade.shared.mapper.TrdShoppingProductOrderMapper;
import com.xianjinxia.trade.shopping.dto.ShoppingLoanOrderContractDto;
import com.xianjinxia.trade.shared.mapper.TrdShoppingLoanOrderMapper;
import com.xianjinxia.trade.shared.remote.CashmanAppRemoteService;
import com.xianjinxia.trade.shopping.dto.CashmanRepaymentPlanDto;
import com.xianjinxia.trade.shopping.dto.CashmanRepaymentPlanStatusEnum;
import com.xianjinxia.trade.shopping.response.bgd.RepaymentPlanDto;
import com.xianjinxia.trade.shopping.response.bgd.ShoppingLoanOrderDto;
import com.xianjinxia.trade.shopping.service.ITrdShoppingLoanOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @Description:实现类
 * @author chunliny
 * @version 1.0
 * @created
 */
@Service
public class TrdShoppingLoanOrderServiceImpl implements ITrdShoppingLoanOrderService {

	private static final Logger logger = LoggerFactory.getLogger(TrdShoppingLoanOrderServiceImpl.class);

	@Autowired
	private TrdShoppingLoanOrderMapper trdShoppingLoanOrderMapper;

	@Autowired
	private CashmanAppRemoteService cashmanAppRemoteService;
	@Autowired
	private TrdShoppingProductOrderMapper trdShoppingProductOrderMapper;




	@Override
	public void insert(TrdShoppingLoanOrder trdShoppingLoanOrder) {
		trdShoppingLoanOrderMapper.insert(trdShoppingLoanOrder);
	}

	@Override
	public Integer getUnFinishOrderCount(Long userId, String[] finalStatusArray) {
		return trdShoppingLoanOrderMapper.countByUserIdAndStatus(userId, finalStatusArray);
	}

	@Override
	public List<TrdShoppingLoanOrder> getRejectShoppingLoanOrders(Long userId, String[] finalStatusArray) {
		return trdShoppingLoanOrderMapper.selectByUserIdAndStatus(userId, finalStatusArray);
	}


	@Override
	public TrdShoppingLoanOrder getById(Long id) {
		return trdShoppingLoanOrderMapper.selectByPrimaryKey(id);
	}


	@Override
	public Integer countByTraceNo(String traceNo) {
		return trdShoppingLoanOrderMapper.countByTraceNo(traceNo);
	}

	@Override
	public int updateLoanOrderStatusToPending(Long id, String newStatus, String preStatus) {
		return trdShoppingLoanOrderMapper.updateLoanOrderStatusToPending(id, newStatus, preStatus);
	}

	@Override
	public int updateByUserIdAndStatus(Long userId, String newStatus, String preStatus) {
		return trdShoppingLoanOrderMapper.updateByUserIdAndStatus(userId, newStatus, preStatus);
	}

	@Override
	public int updateLoanOrderStatus(Long id, String newStatus, String preStatus) {
		return trdShoppingLoanOrderMapper.updateLoanOrderStatus(id, newStatus, preStatus);
	}

	@Override
	public int updateVirtualLoanOrderStatus(Long id, String newStatus, String preStatus,Date thirdOrderTime) {
		return trdShoppingLoanOrderMapper.updateVirtualLoanOrderStatus(id, newStatus, preStatus, thirdOrderTime);
	}

	@Override
	public PageInfo<TrdShoppingLoanOrder> getLoanOrderPageList(ShoppingLoanOrderDto params) {
		PageHelper.startPage(params.getPageNum(), params.getPageSize());
		List<TrdShoppingLoanOrder> trdShoppingLoanOrders = trdShoppingLoanOrderMapper.selectPage(params);
		Page<TrdShoppingLoanOrder> page = (Page<TrdShoppingLoanOrder>) trdShoppingLoanOrders;
		return page.toPageInfo();
	}

	@Override
	public List<RepaymentPlanDto> getRepaymentPlanList(String loanOrderId) {
		List<RepaymentPlanDto> repaymentPlanList = new ArrayList<>();
		try{
			List<CashmanRepaymentPlanDto> repaymentPlanListDto = cashmanAppRemoteService.getRepaymentPlanList(Long.parseLong(loanOrderId));
			for (Iterator<CashmanRepaymentPlanDto> iterator = repaymentPlanListDto.iterator(); iterator.hasNext(); ) {
				CashmanRepaymentPlanDto cashmanRepaymentPlanDto = iterator.next();
				RepaymentPlanDto repaymentPlanDto = new RepaymentPlanDto();
				BeanUtils.copyProperties(cashmanRepaymentPlanDto, repaymentPlanDto);

				repaymentPlanDto.setRepaymentIncomeAmount(new BigDecimal(cashmanRepaymentPlanDto.getRepaymentIncomeAmount()).divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP));
				repaymentPlanDto.setRepaymentTotalAmount(new BigDecimal(cashmanRepaymentPlanDto.getRepaymentTotalAmount()).divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP));

				repaymentPlanDto.setRepaymentWaitRepayAmount(new BigDecimal(cashmanRepaymentPlanDto.getRepaymentIncomeAmount() + cashmanRepaymentPlanDto.getRepaymentTotalAmount()));
				repaymentPlanDto.setRepaymentWaitRepayAmount(repaymentPlanDto.getRepaymentWaitRepayAmount().divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP));

				repaymentPlanDto.setRepaymentPlanTime(cashmanRepaymentPlanDto.getRepaymentPlanTime().getTime());
				repaymentPlanDto.setRepaymentRealTime(cashmanRepaymentPlanDto.getRepaymentRealTime() == null ? null : cashmanRepaymentPlanDto.getRepaymentRealTime().getTime());
				repaymentPlanDto.setUpdatedTime(cashmanRepaymentPlanDto.getUpdatedTime()== null ? null : cashmanRepaymentPlanDto.getUpdatedTime().getTime());
				repaymentPlanDto.setStatusText(CashmanRepaymentPlanStatusEnum.getText(cashmanRepaymentPlanDto.getStatus()));

				repaymentPlanList.add(repaymentPlanDto);
			}
		}catch (Exception e){
			logger.error("没有查找到还款计划，商城借款ID：" + loanOrderId, e);
		}
		return repaymentPlanList;
	}

	@Override
	public PageInfo<LoanOrderDto> conditionalPaging(LoanDetailReq req) {
		TrdShoppingLoanOrder trdShoppingLoanOrder = new TrdShoppingLoanOrder();
		trdShoppingLoanOrder.setUserId(req.getUserId());
		trdShoppingLoanOrder.setProductCategory(req.getProductCategory());
		logger.info("params:{}", trdShoppingLoanOrder);
		PageHelper.startPage(req.getPageNum(), req.getPageSize());
		List<LoanOrderDto> list = trdShoppingLoanOrderMapper.selectSelective(trdShoppingLoanOrder);
		Page<LoanOrderDto> page = (Page<LoanOrderDto>) list;
		logger.info("result:{}", page);
		return page.toPageInfo();
	}

	@Override
	public ShoppingLoanOrderContractDto getLoanContractDto(Long id) {
		TrdShoppingLoanOrder trdShoppingLoanOrder = trdShoppingLoanOrderMapper.selectByPrimaryKey(id);
		TrdShoppingProductOrder trdShoppingProductOrder = trdShoppingProductOrderMapper.getByShoppingLoanOrderId(id);
		ShoppingLoanOrderContractDto shoppingLoanOrderContractDto = new ShoppingLoanOrderContractDto(
				trdShoppingLoanOrder.getId(),
				trdShoppingLoanOrder.getUserId(),
				trdShoppingLoanOrder.getOrderAmount(),
				trdShoppingLoanOrder.getFeeAmount(),
				trdShoppingLoanOrder.getTerm(),
				trdShoppingLoanOrder.getProductId(),
				trdShoppingLoanOrder.getInterestAmount(),
				trdShoppingLoanOrder.getCreatedAt(),
				trdShoppingLoanOrder.getStatus(),
				"消费",
				trdShoppingLoanOrder.getSource(),
				trdShoppingProductOrder.getProductName(),
				"cjxjx"
		);
		return shoppingLoanOrderContractDto;
	}

	@Override
	public List<TrdShoppingLoanOrder> selectListByStatusAndKind(String status, Integer kindId) {
		return trdShoppingLoanOrderMapper.selectListByStatusAndKind(status, kindId);
	}


}
