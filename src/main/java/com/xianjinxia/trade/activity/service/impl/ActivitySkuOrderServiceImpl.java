package com.xianjinxia.trade.activity.service.impl;

import com.xianjinxia.trade.activity.dto.ActivitySkuOrderDto;
import com.xianjinxia.trade.activity.dto.ActivitySkuSaleCountDto;
import com.xianjinxia.trade.activity.dto.UserAddressDto;
import com.xianjinxia.trade.activity.request.*;
import com.xianjinxia.trade.activity.service.IActivitySkuOrderService;
import com.xianjinxia.trade.app.service.IMqMessageService;
import com.xianjinxia.trade.shared.constant.QueueConst;
import com.xianjinxia.trade.shared.domain.TrdActivitySkuOrder;
import com.xianjinxia.trade.shared.enums.ActivityOrderStatusEnum;
import com.xianjinxia.trade.shared.enums.IdempotentTypeEnum;
import com.xianjinxia.trade.shared.exception.ServiceException;
import com.xianjinxia.trade.shared.idempotent.IdempotentService;
import com.xianjinxia.trade.shared.mapper.TrdActivitySkuOrderMapper;
import com.xianjinxia.trade.shared.utils.JsonUtils;
import com.xianjinxia.trade.shared.utils.UniqueIdUtil;
import com.xjx.mqclient.pojo.MqMessage;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author ganminghui
 */
@Service
public class ActivitySkuOrderServiceImpl implements IActivitySkuOrderService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ActivitySkuOrderServiceImpl.class);
    private static final String DEFAULT_DELIMITER = ",";

    @Autowired
    private IdempotentService idempotentService;
    @Autowired(required = false)
    private TrdActivitySkuOrderMapper trdActivitySkuOrderMapper;
    @Autowired
    private IMqMessageService mqMessageService;

    @Override
    public Map<Integer, Integer> getSaleCount(String productIds) {
        if (StringUtils.isBlank(productIds)) {
            throw new ServiceException("待查询销量的商品编号为空!!!");
        }

        int[] idArray = Arrays.stream(productIds.split(DEFAULT_DELIMITER)).filter(StringUtils::isNoneBlank).mapToInt(Integer::parseInt).toArray();
        List<ActivitySkuSaleCountDto> saleCount = trdActivitySkuOrderMapper.getSaleCount(idArray);
        LOGGER.info("一共查询到{}(条)商品的销量纪录!!!", saleCount.size());

        return saleCount.stream().filter(Objects::nonNull).collect(Collectors.toMap(ActivitySkuSaleCountDto::getProductId, ActivitySkuSaleCountDto::getSaleCount));
    }

    @Override
    public UserAddressDto getUserAddress(Long userId) {
        if (Objects.isNull(userId)) {
            throw new ServiceException("待查询用户收货地址的用户编号为空!!!");
        }

        UserAddressDto userAddressDto = Optional.ofNullable(trdActivitySkuOrderMapper.getUserAddress(userId)).orElseGet(UserAddressDto::new);
        userAddressDto.setSeqBizNo(UniqueIdUtil.getActivityTradeNoUniqueId());
        LOGGER.info("查询用户编号:{}的收货信息为:{}", userId, userAddressDto);
        return userAddressDto;
    }

    @Override
    public List<ActivitySkuOrderDto> getAllActivitySkuOrder(Long userId) {
        if (Objects.isNull(userId)) {
            throw new ServiceException("待查询用户所有商品订单为空!!!");
        }

        List<ActivitySkuOrderDto> allActivitySkuOrder = trdActivitySkuOrderMapper.getAllActivitySkuOrder(userId);
        if (CollectionUtils.isNotEmpty(allActivitySkuOrder)) {
            allActivitySkuOrder = allActivitySkuOrder.stream().map(t -> {
                t.setStatus(ActivityOrderStatusEnum.getByCode(t.getStatus()).getValue());
                t.setUserId(userId);
                return t;
            }).collect(Collectors.toList());
        }
        LOGGER.info("查询用户编号:{}的商品订单一共{}(条)!!!", userId, allActivitySkuOrder.size());
        return allActivitySkuOrder;
    }

    @Override
    @Transactional
    public Long orderApply(BuyApplyRequest request) {
        //校验当前是否有待支付订单 幂等
        List<ActivitySkuOrderDto> list = trdActivitySkuOrderMapper.getOrderByUserIdAndStatus(request.getUserId(),request.getProductId(), new String[]{ActivityOrderStatusEnum.NEW.getCode()});
        if (list != null && list.size() > 0) {
            throw new ServiceException("当前有一笔未支付的订单");
        }
        //幂等 校验
        // 1、接口幂等验证
        idempotentService.idempotentCheck(IdempotentTypeEnum.ACTIVITY_ORDER_APPLY, request);

        TrdActivitySkuOrder order = new TrdActivitySkuOrder(request.getBizSeqNo(), request.getUserId(), request.getUserPhone(),
                request.getUserName(), request.getProductId(), request.getProductName(), request.getSpecification(), request.getProductPrice(), request.getReceiveUsername(),
                request.getReceivePhone(), request.getReceiveCity(), request.getReceiveAddress(), ActivityOrderStatusEnum.NEW.getCode());
        trdActivitySkuOrderMapper.insert(order);
        return order.getId();
    }

    @Override
    @Transactional
    public void orderPayApply(PayApplyRequest request) {
        //校验订单号有效性
        TrdActivitySkuOrder order = trdActivitySkuOrderMapper.selectByPrimaryKey(request.getOrderId());
        if (null == order || !order.getUserId().equals(request.getUserId())) {
            LOGGER.error("用户订单不存在，用户ID：{}，订单编号：{}", request.getUserId(), request.getOrderId());
            throw new ServiceException("用户订单不存在");
        }
        //更新订单状态
        int i = trdActivitySkuOrderMapper.updateOrderStatus(order.getId(), ActivityOrderStatusEnum.PAYING.getCode(), ActivityOrderStatusEnum.NEW.getCode());
        if (i > 0) {
            //更新成功以后发送mq
            SeckillPayRequest payRequest = new SeckillPayRequest(order.getId(), order.getBizSeqNo(), order.getUserId(), request.getBankCardId(), order.getProductPrice().longValue());
            mqMessageService.sendMessage(new MqMessage(JsonUtils.toJSONString(payRequest), QueueConst.ACTIVITY_SHOPPING_PAY));
        }
    }

    @Override
    public int updateActivityOrder(SeckillOrderRepayCallbackRequest request) {
        //校验订单号有效性
        TrdActivitySkuOrder order = trdActivitySkuOrderMapper.selectByPrimaryKey(Long.valueOf(request.getOrderDetailId()));
        if (null == order) {
            LOGGER.error("用户订单不存在，订单编号：{}", request.getOrderDetailId());
            throw new ServiceException("用户订单不存在");
        }

        //更新订单状态
        String status = (PaymentCenterCallbackRequestCode.SUCCESS.equals(request.getCode())) ? ActivityOrderStatusEnum.TO_DELIVER.getCode() : ActivityOrderStatusEnum.FAIL.getCode();
        int i = trdActivitySkuOrderMapper.updateOrderStatus(order.getId(), status, ActivityOrderStatusEnum.PAYING.getCode());
        return i;
    }
}
