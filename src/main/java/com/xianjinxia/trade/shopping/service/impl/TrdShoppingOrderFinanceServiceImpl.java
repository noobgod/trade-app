package com.xianjinxia.trade.shopping.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xianjinxia.trade.shared.enums.ShoppingLoanOrderStatusEnum;
import com.xianjinxia.trade.shared.mapper.TrdShoppingLoanOrderFinanceMapper;
import com.xianjinxia.trade.shopping.dto.ShoppingProductOrderFinanceDto;
import com.xianjinxia.trade.shopping.dto.ShoppingProductOrderFinanceSummaryDto;
import com.xianjinxia.trade.shopping.dto.ShoppingVirtualProductOrderFinanceDto;
import com.xianjinxia.trade.shopping.dto.ShoppingVirtualProductOrderFinanceSummaryDto;
import com.xianjinxia.trade.shopping.request.bgd.FinanceShoppingOrderRequest;
import com.xianjinxia.trade.shopping.service.ITrdShoppingOrderFinanceService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class TrdShoppingOrderFinanceServiceImpl implements ITrdShoppingOrderFinanceService {

    @Autowired
    private TrdShoppingLoanOrderFinanceMapper trdShoppingLoanOrderFinanceMapper;

    @Override
    public PageInfo<ShoppingProductOrderFinanceDto> selectByParameter(FinanceShoppingOrderRequest request) throws ParseException {
        PageHelper.startPage(request.getPageNum(), request.getPageSize());
        List<ShoppingProductOrderFinanceDto> list = trdShoppingLoanOrderFinanceMapper.selectSelective(request);
        if (list != null && list.size() > 0) {
            for (ShoppingProductOrderFinanceDto dto : list) {
                dto.setOrderStatus(ShoppingLoanOrderStatusEnum.getByCode(dto.getOrderStatus()).getValue());
                dto.setProductUnitPrice(dto.getProductUnitPrice() == null ? dto.getProductPrice().divide(new BigDecimal(dto.getProductNum() * 100), 2, BigDecimal.ROUND_HALF_UP) : dto.getProductUnitPrice().divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP));
                dto.setProductPrice(dto.getProductPrice() == null ? null : dto.getProductPrice().divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP));
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                dto.setOrderTime(formatter.format(formatter.parse(dto.getOrderTime())));
                dto.setSendTime(dto.getSendTime() == null ? null : formatter.format(formatter.parse(dto.getSendTime())));
            }
        }

        Page<ShoppingProductOrderFinanceDto> page = (Page<ShoppingProductOrderFinanceDto>) list;
        return page.toPageInfo();
    }

    @Override
    public PageInfo<ShoppingVirtualProductOrderFinanceDto> selectVirtualOrderByParameter(FinanceShoppingOrderRequest request) throws ParseException {
        PageHelper.startPage(request.getPageNum(), request.getPageSize());
        List<ShoppingVirtualProductOrderFinanceDto> list = trdShoppingLoanOrderFinanceMapper.selectVirtualOrderByParameter(request);
        if (list != null && list.size() > 0) {
            for (ShoppingVirtualProductOrderFinanceDto dto : list) {
//                dto.setProductPrice(dto.getProductPrice().divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP));
                dto.setProductUnitPrice(dto.getProductUnitPrice().divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP));
                if (StringUtils.equals(dto.getOrderStatus(), ShoppingLoanOrderStatusEnum.RECEIVED.getCode())) {
                    dto.setRemark("自动发货");
                }
                dto.setOrderStatus(ShoppingLoanOrderStatusEnum.getByCode(dto.getOrderStatus()).getValue());
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                dto.setOrderTime(formatter.format(formatter.parse(dto.getOrderTime())));
            }
        }
        Page<ShoppingVirtualProductOrderFinanceDto> page = (Page<ShoppingVirtualProductOrderFinanceDto>) list;
        return page.toPageInfo();
    }

    @Override
    public ShoppingProductOrderFinanceSummaryDto selectSummaryByParameter(FinanceShoppingOrderRequest request) {
        return trdShoppingLoanOrderFinanceMapper.selectSummaryBySelective(request);
    }

    @Override
    public ShoppingVirtualProductOrderFinanceSummaryDto selectVirtualOrderSummaryByParameter(FinanceShoppingOrderRequest request) {
        return trdShoppingLoanOrderFinanceMapper.selectVirtualOrderSummaryByParameter(request);
    }
}
