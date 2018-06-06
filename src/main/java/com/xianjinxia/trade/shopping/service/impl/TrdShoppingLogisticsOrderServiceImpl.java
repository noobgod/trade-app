package com.xianjinxia.trade.shopping.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xianjinxia.trade.shared.domain.TrdShoppingLoanCardInfo;
import com.xianjinxia.trade.shared.domain.TrdShoppingLoanOrder;
import com.xianjinxia.trade.shared.domain.TrdShoppingLogisticsOrder;
import com.xianjinxia.trade.shared.domain.TrdShoppingProductOrder;
import com.xianjinxia.trade.shared.enums.ShoppingLoanOrderStatusEnum;
import com.xianjinxia.trade.shared.exception.ServiceException;
import com.xianjinxia.trade.shared.mapper.TrdShoppingLoanCardInfoMapper;
import com.xianjinxia.trade.shared.mapper.TrdShoppingLoanOrderMapper;
import com.xianjinxia.trade.shared.mapper.TrdShoppingLogisticsOrderMapper;
import com.xianjinxia.trade.shared.mapper.TrdShoppingProductOrderMapper;
import com.xianjinxia.trade.shared.remote.CashmanAppRemoteService;
import com.xianjinxia.trade.shopping.request.SyncRepaymentPlanReq;
import com.xianjinxia.trade.shopping.response.bgd.ShoppingLogisticsOrderDto;
import com.xianjinxia.trade.shopping.service.ITrdShoppingLogisticsOrderService;
import com.xianjinxia.trade.shopping.service.ITrdShoppingProductOrderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @Description:实现类
 * @author chunliny
 * @version 1.0
 * @created 
 */
@Service
public class TrdShoppingLogisticsOrderServiceImpl implements ITrdShoppingLogisticsOrderService {
	@Autowired
	private TrdShoppingProductOrderMapper trdShoppingProductOrderMapper;

	@Autowired
	private TrdShoppingLoanOrderMapper trdShoppingLoanOrderMapper;

	@Autowired
	private TrdShoppingLogisticsOrderMapper trdShoppingLogisticsOrderMapper;

	@Autowired
	private ITrdShoppingProductOrderService trdShoppingProductOrderService;

	@Autowired
	private CashmanAppRemoteService cashmanAppRemoteService;

	@Autowired
	private TrdShoppingLoanCardInfoMapper trdShoppingLoanCardInfoMapper;


	@Override
	@Transactional
	public void insert(TrdShoppingLogisticsOrder trdShoppingLogisticsOrder) {
		trdShoppingLogisticsOrderMapper.insert(trdShoppingLogisticsOrder);
	}

	@Override
	@Transactional
	public int updateLogisticsOrderStatus(Long shoppingLoanOrderId, String newStatus, String preStatus) {
		return trdShoppingLogisticsOrderMapper.updateLogisticsOrderStatus(shoppingLoanOrderId, newStatus, preStatus);
	}

	@Override
	@Transactional
	public void updateLogisticsOrder(TrdShoppingLogisticsOrder trdShoppingLogisticsOrder, boolean isUpdateStatus) {
		int i = 0;
		trdShoppingLogisticsOrder.setLogisticsPrice(trdShoppingLogisticsOrder.getLogisticsPrice() * 100);
		if (isUpdateStatus){
			TrdShoppingLogisticsOrder selectByPrimaryKey = trdShoppingLogisticsOrderMapper.selectByPrimaryKey(trdShoppingLogisticsOrder.getId());
			TrdShoppingLoanOrder trdShoppingLoanOrder = trdShoppingLoanOrderMapper.selectByPrimaryKey(selectByPrimaryKey.getShoppingLoanOrderId());

			if (trdShoppingLoanOrder.getStatus().equals(ShoppingLoanOrderStatusEnum.SENDING.getCode())){
				throw new ServiceException("该订单已发货，请选择编辑此订单");
			}


			i = trdShoppingLogisticsOrderMapper.updateLogisticsOrderToDeliver(trdShoppingLogisticsOrder.getId(), trdShoppingLogisticsOrder.getLogisticsCompany(), trdShoppingLogisticsOrder.getLogisticsNo(), trdShoppingLogisticsOrder.getSendPostNo(), trdShoppingLogisticsOrder.getLogisticsPrice(), trdShoppingLogisticsOrder.getSendTime(), trdShoppingLogisticsOrder.getVenderId(), trdShoppingLogisticsOrder.getVenderName(), ShoppingLoanOrderStatusEnum.SENDING.getCode());
			if (i != 1){
				throw new ServiceException("修改物流发货信息异常");
			}


			i = trdShoppingLoanOrderMapper.updateLoanOrderStatus(trdShoppingLoanOrder.getId(), ShoppingLoanOrderStatusEnum.SENDING.getCode(), trdShoppingLoanOrder.getStatus());
			if (i != 1){
				throw new ServiceException("修改物流发货信息异常");
			}

			i = trdShoppingProductOrderService.updateProductOrderStatus(selectByPrimaryKey.getShoppingLoanOrderId(), ShoppingLoanOrderStatusEnum.SENDING.getCode(), trdShoppingLoanOrder.getStatus());
			if (i != 1){
				throw new ServiceException("修改商品订单状态异常");
			}
			cashmanAppRemoteService.syncRepaymentPlanTime(trdShoppingLoanOrder.getId(), trdShoppingLogisticsOrder.getSendTime());
		}else{
			i = trdShoppingLogisticsOrderMapper.updateLogisticsOrderToDeliver(trdShoppingLogisticsOrder.getId(), trdShoppingLogisticsOrder.getLogisticsCompany(), trdShoppingLogisticsOrder.getLogisticsNo(), trdShoppingLogisticsOrder.getSendPostNo(), trdShoppingLogisticsOrder.getLogisticsPrice(), null, trdShoppingLogisticsOrder.getVenderId(), trdShoppingLogisticsOrder.getVenderName(), null);
		}

		if (i != 1){
			throw new ServiceException("修改物流发货信息异常");
		}
	}

//
//	private void deliver(){
//		map.put("id",loanOrder.getId());
//		map.put("prestatus", TrdLoanOrderStatusEnum.LOANING.getCode());
//		if (payCenterCallbackDto.getBankPayTime() != null) {
//			map.put("loanTime", payCenterCallbackDto.getBankPayTime());
//		}
//		if (Constant.CUSTODYLOAN_SUCCESS.equals(payCenterCallbackDto.getCode())) {
//			//放款成功
//			map.put("status", TrdLoanOrderStatusEnum.LOAN_SUCCESS.getCode());
//			//添加数据--用户的合同数据
//			List<ContractDto> contracts=contractMapper.selectByProductId(loanOrder.getProductId());
//			for(ContractDto contract:contracts){
//				LoanContract loanContract=new LoanContract(contract.getContractName(),
//						contract.getContractType(),loanOrder.getTrdLoanOrderId(),
//						loanOrder.getUserId(), LoanContractStatusEnum.NOT_HANDLE.getCode());
//
//				loanContractMapper.insert(loanContract);
//			}
//		} else {
//			//放款失败
//			map.put("status", TrdLoanOrderStatusEnum.LOAN_FAIL.getCode());
//
//			//放款失败把还款计划订单状态设置成已取消
//			int repaymentNum = repaymentPlanMapper.updateStatus(loanOrder.getTrdLoanOrderId(), RepaymentPlanStatusEnum.Canceled.getCode());
//			if(repaymentNum == 0){
//				logger.error("repaymentNum=0,loanOrderId={}",loanOrder.getTrdLoanOrderId());
//			}
//		}
//		loanOrderMapper.updateselective(map);
//	}


	@Override
	public TrdShoppingLogisticsOrder getByShoppingLoanOrderId(Long shoppingLoanOrderId) {
		return trdShoppingLogisticsOrderMapper.getByShoppingLoanOrderId(shoppingLoanOrderId);
	}

	@Override
	public List<TrdShoppingLogisticsOrder> selectBySendTimeAndStatus(String endTime, String status) {
		return trdShoppingLogisticsOrderMapper.selectByEndTimeAndStatus(endTime, status);
	}

	@Override
	public PageInfo<ShoppingLogisticsOrderDto> getLogisticsOrderPageList(ShoppingLogisticsOrderDto params) {
		PageHelper.startPage(params.getPageNum(), params.getPageSize());
		List<TrdShoppingLogisticsOrder> trdShoppingLogisticsOrders = trdShoppingLogisticsOrderMapper.selectPage(params);
		Page<TrdShoppingLogisticsOrder> page = (Page<TrdShoppingLogisticsOrder>) trdShoppingLogisticsOrders;
		PageInfo<TrdShoppingLogisticsOrder> logisticsOrderPageInfo = page.toPageInfo();
		List<TrdShoppingLogisticsOrder> list = logisticsOrderPageInfo.getList();


		PageInfo<ShoppingLogisticsOrderDto> r = new PageInfo<ShoppingLogisticsOrderDto>();
		BeanUtils.copyProperties(logisticsOrderPageInfo, r);
		List<ShoppingLogisticsOrderDto> shoppingProductOrderDtos = new ArrayList<>(list.size());

		for (Iterator<TrdShoppingLogisticsOrder> iterator = list.iterator(); iterator.hasNext(); ) {
			TrdShoppingLogisticsOrder trdShoppingLogisticsOrder = iterator.next();
			TrdShoppingLoanOrder trdShoppingLoanOrder = trdShoppingLoanOrderMapper.selectByPrimaryKey(trdShoppingLogisticsOrder.getShoppingLoanOrderId());
			//2n+1的查询性能不好,如果有脏数据报错了,修改bug
			if(null == trdShoppingLoanOrder){
				continue;
			}
			TrdShoppingProductOrder trdShoppingProductOrder = trdShoppingProductOrderMapper.getByShoppingLoanOrderId(trdShoppingLogisticsOrder.getShoppingLoanOrderId());
			
			//2n+1的查询性能不好,如果有脏数据报错了,修改bug
			if(null == trdShoppingProductOrder){
				continue;
			}

			ShoppingLogisticsOrderDto shoppingLogisticsOrderDto = new ShoppingLogisticsOrderDto(
					trdShoppingLogisticsOrder.getId(),
					trdShoppingLogisticsOrder.getShoppingLoanOrderId(),
					trdShoppingLoanOrder.getStatus(),
					ShoppingLoanOrderStatusEnum.getByCode(trdShoppingLoanOrder.getStatus()).getValue(),
					trdShoppingProductOrder.getProductName(),
					trdShoppingProductOrder.getProductPrice()/100,
					1,
					trdShoppingLoanOrder.getSpecificationDesc(),
					trdShoppingLoanOrder.getUserName(),
					trdShoppingLogisticsOrder.getReceiveUsername(),
					trdShoppingLogisticsOrder.getReceiveMobile(),
					trdShoppingLogisticsOrder.getReceiveAddr(),
					trdShoppingLogisticsOrder.getCreatedAt().getTime(),
					null,
					trdShoppingLogisticsOrder.getVenderId(),
					trdShoppingLogisticsOrder.getVenderName(),
					trdShoppingLogisticsOrder.getLogisticsNo(),
					trdShoppingLogisticsOrder.getSendPostNo(),
					trdShoppingLogisticsOrder.getLogisticsCompany(),
					trdShoppingLogisticsOrder.getLogisticsPrice(),
					trdShoppingLogisticsOrder.getSendTime() ==  null ? null : trdShoppingLogisticsOrder.getSendTime().getTime(),
					trdShoppingLoanOrder.getProductKind()

			);

			if (trdShoppingLogisticsOrder.getStatus().equals(ShoppingLoanOrderStatusEnum.RECEIVED.getCode())){
				shoppingLogisticsOrderDto.setFinishTime(trdShoppingLogisticsOrder.getUpdatedAt().getTime());
			}

			shoppingProductOrderDtos.add(shoppingLogisticsOrderDto);
		}

		r.setList(shoppingProductOrderDtos);
		return r;
	}

	@Override
	public ShoppingLogisticsOrderDto getShoppingLogisticsOrderDtoById(Long id) {
		TrdShoppingLogisticsOrder trdShoppingLogisticsOrder = trdShoppingLogisticsOrderMapper.selectByPrimaryKey(id);
		TrdShoppingLoanOrder trdShoppingLoanOrder = trdShoppingLoanOrderMapper.selectByPrimaryKey(trdShoppingLogisticsOrder.getShoppingLoanOrderId());
		TrdShoppingProductOrder trdShoppingProductOrder = trdShoppingProductOrderMapper.getByShoppingLoanOrderId(trdShoppingLogisticsOrder.getShoppingLoanOrderId());

		ShoppingLogisticsOrderDto shoppingLogisticsOrderDto = new ShoppingLogisticsOrderDto(
				trdShoppingLogisticsOrder.getId(),
				trdShoppingLogisticsOrder.getShoppingLoanOrderId(),
				trdShoppingLoanOrder.getStatus(),
				ShoppingLoanOrderStatusEnum.getByCode(trdShoppingLoanOrder.getStatus()).getValue(),
				trdShoppingProductOrder.getProductName(),
				trdShoppingProductOrder.getProductPrice(),
				1,
				trdShoppingLoanOrder.getSpecificationDesc(),
				trdShoppingLoanOrder.getUserName(),
				trdShoppingLogisticsOrder.getReceiveUsername(),
				trdShoppingLogisticsOrder.getReceiveMobile(),
				trdShoppingLogisticsOrder.getReceiveProvince() + trdShoppingLogisticsOrder.getReceiveCity() + trdShoppingLogisticsOrder.getReceiveAddr(),
				trdShoppingLogisticsOrder.getCreatedAt().getTime(),
				null,
				trdShoppingLogisticsOrder.getVenderId(),
				trdShoppingLogisticsOrder.getVenderName(),
				trdShoppingLogisticsOrder.getLogisticsNo(),
				trdShoppingLogisticsOrder.getSendPostNo(),
				trdShoppingLogisticsOrder.getLogisticsCompany(),
				trdShoppingLogisticsOrder.getLogisticsPrice(),
				null,
				trdShoppingLoanOrder.getProductKind()

		);

		if (trdShoppingLogisticsOrder.getStatus().equals(ShoppingLoanOrderStatusEnum.SENDING.getCode())){
			shoppingLogisticsOrderDto.setSendTime(trdShoppingLogisticsOrder.getSendTime().getTime());
		}
		if (trdShoppingLoanOrder.getProductKind() == 2) {
			List<TrdShoppingLoanCardInfo> cards = trdShoppingLoanCardInfoMapper.selectByOrderId(trdShoppingProductOrder.getId());
			if (cards != null && cards.size() > 0) {
				shoppingLogisticsOrderDto.setSendTime(cards.get(0).getCreatedAt().getTime());
			}
		}

		if (trdShoppingLogisticsOrder.getStatus().equals(ShoppingLoanOrderStatusEnum.RECEIVED.getCode())){
			shoppingLogisticsOrderDto.setFinishTime(trdShoppingLogisticsOrder.getUpdatedAt().getTime());
		}
		return shoppingLogisticsOrderDto;
	}

	@Override
	public TrdShoppingLogisticsOrder getById(Long id) {
		TrdShoppingLogisticsOrder trdShoppingLogisticsOrder = trdShoppingLogisticsOrderMapper.selectByPrimaryKey(id);
		return trdShoppingLogisticsOrder;
	}
}

