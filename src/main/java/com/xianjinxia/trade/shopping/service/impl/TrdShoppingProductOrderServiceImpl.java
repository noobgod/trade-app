package com.xianjinxia.trade.shopping.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xianjinxia.trade.app.dto.LoanOrderDto;
import com.xianjinxia.trade.app.response.BaseResponse;
import com.xianjinxia.trade.shared.domain.TrdShoppingLoanOrder;

import com.xianjinxia.trade.shared.domain.TrdShoppingLogisticsOrder;
import com.xianjinxia.trade.shared.enums.ShoppingLoanOrderStatusEnum;
import com.xianjinxia.trade.shared.mapper.TrdShoppingLoanOrderMapper;
import com.xianjinxia.trade.shared.mapper.TrdShoppingLogisticsOrderMapper;
import com.xianjinxia.trade.shopping.response.bgd.ShoppingLoanOrderDto;
import com.xianjinxia.trade.shopping.response.bgd.ShoppingProductOrderDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xianjinxia.trade.shared.domain.TrdShoppingProductOrder;
import com.xianjinxia.trade.shared.mapper.TrdShoppingProductOrderMapper;
import com.xianjinxia.trade.shopping.service.ITrdShoppingProductOrderService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
public class TrdShoppingProductOrderServiceImpl implements ITrdShoppingProductOrderService {
	
	@Autowired
	private TrdShoppingProductOrderMapper trdShoppingProductOrderMapper;

	@Autowired
	private TrdShoppingLoanOrderMapper trdShoppingLoanOrderMapper;

	@Autowired
	private TrdShoppingLogisticsOrderMapper trdShoppingLogisticsOrderMapper;

	@Override
	public void insert(TrdShoppingProductOrder trdShoppingProductOrder) {
		trdShoppingProductOrderMapper.insert(trdShoppingProductOrder);
	}
	
	@Override
	public int updateProductOrderStatus(Long shoppingLoanOrderId, String newStatus, String preStatus) {
		return trdShoppingProductOrderMapper.updateProductOrderStatus(shoppingLoanOrderId, newStatus, preStatus);
	}

	@Override
	public PageInfo<ShoppingProductOrderDto> getProductOrderPageList(ShoppingProductOrderDto params) {
		PageHelper.startPage(params.getPageNum(), params.getPageSize());
		List<ShoppingProductOrderDto> shoppingProductOrderDtoList = trdShoppingProductOrderMapper.selectByParams(params);
		for (Iterator<ShoppingProductOrderDto> iterator = shoppingProductOrderDtoList.iterator(); iterator.hasNext(); ) {
			ShoppingProductOrderDto shoppingProductOrderDto = iterator.next();
			shoppingProductOrderDto.setStatus(ShoppingLoanOrderStatusEnum.getByCode(shoppingProductOrderDto.getStatus()).getValue());
			shoppingProductOrderDto.setProductPrice(shoppingProductOrderDto.getProductPrice()/100);
			shoppingProductOrderDto.setCreatedTime(shoppingProductOrderDto.getCreatedAt().getTime());
			shoppingProductOrderDto.setUpdatedTime(shoppingProductOrderDto.getUpdatedAt().getTime());
		}
		Page<ShoppingProductOrderDto> page = (Page<ShoppingProductOrderDto>) shoppingProductOrderDtoList;
		PageInfo<ShoppingProductOrderDto> r = page.toPageInfo();
		return r;
	}

	@Override
	public ShoppingProductOrderDto getById(Long id) {
		TrdShoppingProductOrder trdShoppingProductOrder = trdShoppingProductOrderMapper.selectByPrimaryKey(id);
		trdShoppingProductOrder.setProductPrice(trdShoppingProductOrder.getProductPrice()/100);
		ShoppingProductOrderDto shoppingProductOrderDto = new ShoppingProductOrderDto();
		BeanUtils.copyProperties(trdShoppingProductOrder, shoppingProductOrderDto);
		shoppingProductOrderDto.setCreatedTime(trdShoppingProductOrder.getCreatedAt().getTime());
		shoppingProductOrderDto.setUpdatedTime(trdShoppingProductOrder.getUpdatedAt().getTime());

		TrdShoppingLoanOrder trdShoppingLoanOrder = trdShoppingLoanOrderMapper.selectByPrimaryKey(shoppingProductOrderDto.getShoppingLoanOrderId());
		BeanUtils.copyProperties(trdShoppingLoanOrder, shoppingProductOrderDto);
		shoppingProductOrderDto.setStatus(ShoppingLoanOrderStatusEnum.getByCode(trdShoppingLoanOrder.getStatus()).getValue());

		TrdShoppingLogisticsOrder trdShoppingLogisticsOrder = trdShoppingLogisticsOrderMapper.getByShoppingLoanOrderId(shoppingProductOrderDto.getShoppingLoanOrderId());
		shoppingProductOrderDto.setReceiveUsername(trdShoppingLogisticsOrder.getReceiveUsername());
		shoppingProductOrderDto.setReceiveMobile(trdShoppingLogisticsOrder.getReceiveMobile());
		shoppingProductOrderDto.setReceiveAddr(trdShoppingLogisticsOrder.getReceiveAddr());
		shoppingProductOrderDto.setId(trdShoppingProductOrder.getId());
		return shoppingProductOrderDto;
	}

	@Override
	public TrdShoppingProductOrder getByShoppingLoanOrderId(Long shoppingLoanOrderId) {
		return trdShoppingProductOrderMapper.getByShoppingLoanOrderId(shoppingLoanOrderId);
	}

	@Override
	public int updateVirtualProductOrderStatus(Long shoppingLoanOrderId, String newStatus, String preStatusString, String purchasePrice) {
		return trdShoppingProductOrderMapper.updateProductStatusAndPurchasePrice(shoppingLoanOrderId, newStatus, preStatusString, purchasePrice);
	}

}

